package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.dreamernguyen.ClientDuAn.Adapter.MatHangAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangQuanTamActivity extends AppCompatActivity {
    RecyclerView rvMatHang;
    MatHangAdapter matHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_quan_tam);
        rvMatHang = findViewById(R.id.rvMatHang);
        matHangAdapter = new MatHangAdapter(MatHangQuanTamActivity.this);
        rvMatHang.setAdapter(matHangAdapter);
        rvMatHang.setLayoutManager(new LinearLayoutManager(MatHangQuanTamActivity.this, RecyclerView.VERTICAL,false));
        loadDanhSachQuanTam();
    }
    public void loadDanhSachQuanTam(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachQuanTam(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<MatHang> list = response.body().getDanhSachMatHang();
                if(list.size() > 0){
                    matHangAdapter.setData(list);
                }
            }
            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("LoadDanhSachToiRao", "onFailure: "+t.getMessage());
            }
        });
    }
}