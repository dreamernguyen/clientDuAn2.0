package com.dreamernguyen.ClientDuAn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.dreamernguyen.ClientDuAn.Activity.QuetQR;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.Adapter.NguoiDungAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.MainActivity;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TinNhanFragment extends Fragment {
    RecyclerView rvLienHe;
    NguoiDungAdapter nguoiDungAdapter;
    ImageView btnQuetMa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_nhan, container, false);
        rvLienHe = view.findViewById(R.id.rvLienHe);
        nguoiDungAdapter = new NguoiDungAdapter(getContext());
        btnQuetMa = view.findViewById(R.id.btnQuetMa);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvLienHe.setLayoutManager(linearLayoutManager);
        rvLienHe.setAdapter(nguoiDungAdapter);
        loadLienHe();
        btnQuetMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quetQR();
            }
        });


        return view;
    }
    public void loadLienHe(){
        Call<List<NguoiDung>> call = ApiService.apiService.danhSachLienHe(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<NguoiDung>>() {
            @Override
            public void onResponse(Call<List<NguoiDung>> call, Response<List<NguoiDung>> response) {
                nguoiDungAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<NguoiDung>> call, Throwable t) {
                Log.d("loadLienHe", "onFailure : "+t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void quetQR(){
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Quét mã QR");
//        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(true);
//        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(QuetQR.class);
        integrator.initiateScan();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data );
        if(intentResult.getContents() != null){
            Intent i = new Intent(getContext(), TrangCaNhanActivity.class);
            i.putExtra("idNguoiDung",intentResult.getContents());
            startActivity(i);
        }else {
            Toast.makeText(getContext(), "Quét thất bại ! Vui lòng thử lại ! ", Toast.LENGTH_SHORT).show();
        }
    }


}