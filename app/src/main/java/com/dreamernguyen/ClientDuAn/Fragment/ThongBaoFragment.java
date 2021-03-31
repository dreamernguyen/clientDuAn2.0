package com.dreamernguyen.ClientDuAn.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dreamernguyen.ClientDuAn.Adapter.NguoiDungAdapter;
import com.dreamernguyen.ClientDuAn.Adapter.ThongBaoAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.Models.ThongBao;
import com.dreamernguyen.ClientDuAn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThongBaoFragment extends Fragment {
    RecyclerView rvThongBao;
    ThongBaoAdapter thongBaoAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_bao, container, false);
        rvThongBao= view.findViewById(R.id.rvThongBao);
        thongBaoAdapter = new ThongBaoAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rvThongBao.setLayoutManager(linearLayoutManager);
        rvThongBao.setAdapter(thongBaoAdapter);
        loadThongBao();
        return view;
    }
    public void loadThongBao(){
        Call<List<ThongBao>> call = ApiService.apiService.danhSachThongBao(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<ThongBao>>() {
            @Override
            public void onResponse(Call<List<ThongBao>> call, Response<List<ThongBao>> response) {
                if(response.body().size() >0){
                    thongBaoAdapter.setData(response.body());
                }


            }

            @Override
            public void onFailure(Call<List<ThongBao>> call, Throwable t) {
                Log.d("loadThongBao", "onFailure: "+t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}