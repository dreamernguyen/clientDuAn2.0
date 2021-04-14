package com.dreamernguyen.ClientDuAn.Activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MatHangToiRaoActivity extends AppCompatActivity {
    RecyclerView rvMatHang;
    MatHangAdapter matHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_toi_rao);
        rvMatHang = findViewById(R.id.rvMatHang);
        matHangAdapter = new MatHangAdapter(MatHangToiRaoActivity.this);
        rvMatHang.setAdapter(matHangAdapter);
        rvMatHang.setLayoutManager(new LinearLayoutManager(MatHangToiRaoActivity.this, RecyclerView.VERTICAL,false));
        loadDanhSachToiRao();
    }
    public void loadDanhSachToiRao(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachToiBan(LocalDataManager.getIdNguoiDung());
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
