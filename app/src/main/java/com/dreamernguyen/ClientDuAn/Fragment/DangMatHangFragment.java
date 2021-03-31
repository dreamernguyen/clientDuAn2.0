package com.dreamernguyen.ClientDuAn.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.Adapter.MatHangHinhAnhAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangMatHangFragment extends Fragment {

    MatHangHinhAnhAdapter matHangHinhAnhAdapter;
    TextView tvTieuDe, tvGiaBan, tvNoiDung, tvHangMuc, tvDiaChi, tvBack;
    RecyclerView rv;
    AppCompatButton btn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang, container, false);

        tvBack = view.findViewById(R.id.tvBack);

        tvTieuDe = view.findViewById(R.id.tvTieuDe);
        tvGiaBan = view.findViewById(R.id.tvGiaBan);
        tvNoiDung = view.findViewById(R.id.tvNoiDung);
        tvHangMuc = view.findViewById(R.id.tvHangMuc);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        rv = view.findViewById(R.id.rv);
        btn = view.findViewById(R.id.btn);


        matHangHinhAnhAdapter = new MatHangHinhAnhAdapter(getActivity());
        matHangHinhAnhAdapter.setListAnh(DangMatHangActivity.listAnh);


        tvBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDangMatHangToKhac);
            DangMatHangActivity.viTri = 3;
        });

        Log.d("idMatHangChiTiet", "onCreateView: "+DangMatHangActivity.idMatHangChiTiet);

        btn.setOnClickListener(v -> {

            Log.d("TAGa", "onClick: " + "1");
            List<Uri> listURI = DangMatHangActivity.listURI;
            List<String> listURL = new ArrayList<>();

            if (DangMatHangActivity.listAnh != null && !DangMatHangActivity.listAnh.isEmpty()) {

                if (!DangMatHangActivity.listURI.isEmpty()){
                    Log.d("TAGa", "onClick: " + "2");
                    for (int i = 0; i < listURI.size(); i++) {
                        MediaManager.get().upload(listURI.get(i)).unsigned("gybczcnv").callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                listURL.add(resultData.get("url").toString());
                                Log.d("TAGa", "onSuccess: " + DangMatHangActivity.listAnh);
                                if (listURI.size() == listURL.size()) {
                                    if (!DangMatHangActivity.listString.isEmpty()) {
                                        listURL.addAll(DangMatHangActivity.listString);
                                    }
                                    DangMatHangActivity.listAnh = new ArrayList<>();
                                    DangMatHangActivity.listAnh = listURL;
                                    Log.d("TAGa", "onSuccess: " + DangMatHangActivity.listAnh);

                                    if (DangMatHangActivity.idMatHangChiTiet != null && !DangMatHangActivity.idMatHangChiTiet.isEmpty()) {
                                        capNhapMatHang(DangMatHangActivity.listAnh, DangMatHangActivity.idMatHangChiTiet);

                                    } else {
                                        dangMatHang(DangMatHangActivity.listAnh);

                                    }

                                }
                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {
                            }

                        }).dispatch();
                    }
                }
                else {
                    capNhapMatHang(DangMatHangActivity.listAnh, DangMatHangActivity.idMatHangChiTiet);

                }

            }
        });

        rv.setAdapter(matHangHinhAnhAdapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

    @Override
    public void onResume() {

        Log.d("TAGa", "onResume: " + DangMatHangActivity.listAnh);

        tvTieuDe.setText(DangMatHangActivity.tieuDe);
        tvDiaChi.setText(DangMatHangActivity.ThanhPho + " - " + DangMatHangActivity.QuanHuyen + " - " + DangMatHangActivity.PhuongXa);
        tvGiaBan.setText(DangMatHangActivity.giaBan + "");
        tvHangMuc.setText(DangMatHangActivity.DanhMuc + " - " + DangMatHangActivity.DanhMucCon);
        tvNoiDung.setText(DangMatHangActivity.noiDung);
        super.onResume();
    }

    public void dangMatHang(List<String> list) {
        String mTieuDe = DangMatHangActivity.tieuDe;
        String mNoiDung = DangMatHangActivity.noiDung;
        String mHangMuc = DangMatHangActivity.DanhMuc + " - " + DangMatHangActivity.DanhMucCon;
        int mGiaBan = DangMatHangActivity.giaBan;
        String mDiaChi = DangMatHangActivity.ThanhPho + " - " + DangMatHangActivity.QuanHuyen + " - " + DangMatHangActivity.PhuongXa;

        MatHang matHang = new MatHang(mTieuDe, mNoiDung, mHangMuc, mGiaBan, list, mDiaChi);
        Call<DuLieuTraVe> call = ApiService.apiService.dangMatHang(LocalDataManager.getIdNguoiDung(), matHang);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {

                Log.d("TAG", "onResponse: " + response.body().getThongBao());
                DangMatHangActivity.clearAll();
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }

    public void capNhapMatHang(List<String> list, String id) {
        String mTieuDe = DangMatHangActivity.tieuDe;
        String mNoiDung = DangMatHangActivity.noiDung;
        String mHangMuc = DangMatHangActivity.DanhMuc + " - " + DangMatHangActivity.DanhMucCon;
        int mGiaBan = DangMatHangActivity.giaBan;
        String mDiaChi = DangMatHangActivity.ThanhPho + " - " + DangMatHangActivity.QuanHuyen + " - " + DangMatHangActivity.PhuongXa;
        MatHang matHang = new MatHang(mTieuDe, mNoiDung, mHangMuc, mGiaBan, list, mDiaChi);
        Call<DuLieuTraVe> call = ApiService.apiService.capNhapMatHang(id, matHang);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                Log.d("TAG", "onResponse: " + response.body().getThongBao());
                DangMatHangActivity.clearAll();
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });


    }
}
