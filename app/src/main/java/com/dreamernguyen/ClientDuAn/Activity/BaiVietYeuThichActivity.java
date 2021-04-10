package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dreamernguyen.ClientDuAn.Adapter.BaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietYeuThichActivity extends AppCompatActivity {
    RecyclerView rvBaiViet;
    BaiVietAdapter baiVietAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tvThongBao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_yeu_thich);
        refreshLayout = findViewById(R.id.refreshLayout);
        rvBaiViet =  findViewById(R.id.rvBaiVietYeuThich);
        baiVietAdapter = new BaiVietAdapter(BaiVietYeuThichActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BaiVietYeuThichActivity.this, RecyclerView.VERTICAL,false);
        rvBaiViet.setLayoutManager(linearLayoutManager);
        rvBaiViet.setAdapter(baiVietAdapter);
        tvThongBao = findViewById(R.id.tvThongBao);
        loadBaiVietYeuThich();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBaiVietYeuThich();

            }
        });
    }
    public void loadBaiVietYeuThich(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachYeuThich(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                refreshLayout.setRefreshing(false);
                Log.d("Data", "onResponse: "+response.body().getDanhSachBaiViet());
                if(response.body().getDanhSachBaiViet() == null || response.body().getDanhSachBaiViet().isEmpty()){
                    tvThongBao.setVisibility(View.VISIBLE);
                    tvThongBao.setText(response.body().getThongBao());
                    rvBaiViet.setVisibility(View.GONE);
                }else {
                    tvThongBao.setVisibility(View.GONE);
                    rvBaiViet.setVisibility(View.VISIBLE);
                    baiVietAdapter.setData(response.body().getDanhSachBaiViet());
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }
}