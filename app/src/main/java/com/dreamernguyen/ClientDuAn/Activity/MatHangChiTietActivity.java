package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangChiTietActivity extends AppCompatActivity {

    ImageView imgHinhAnh;
    TextView tvTieuDe, tvGiaBan, tvDiaChi, tvNoiDung,tvSuaTin,tvXoaTin;
    String idMatHangChiTiet;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_chi_tiet);

        intent = getIntent();
        idMatHangChiTiet = intent.getStringExtra("idMatHangChiTiet");
        imgHinhAnh = findViewById(R.id.imgHinhAnh);
        tvTieuDe = findViewById(R.id.tvTieuDeChiTiet);
        tvGiaBan = findViewById(R.id.tvGiaChiTiet);
        tvDiaChi = findViewById(R.id.tvDiaChiChiTiet);
        tvNoiDung = findViewById(R.id.tvNoiDungChiTiet);
        tvSuaTin=findViewById(R.id.tvSuaTin);
        tvXoaTin=findViewById(R.id.tvXoaTin);

        tvSuaTin.setOnClickListener(v -> {
            intent= new Intent(this,DangMatHangActivity.class);
            intent.putExtra("idMatHangChiTiet",idMatHangChiTiet);
            startActivity(intent);
        });

        tvXoaTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<DuLieuTraVe> call= ApiService.apiService.xoaMatHang(idMatHangChiTiet);
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        finish();
                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                    }
                });
            }
        });

        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTietMatHang(idMatHangChiTiet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                MatHang matHang = response.body().getMatHang();
                Glide.with(getApplicationContext()).load(matHang.getLinkAnh().get(0)).into(imgHinhAnh);
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText(matHang.getDiaChi());
                tvGiaBan.setText(matHang.getGiaBan() + "");
                tvNoiDung.setText(matHang.getMoTa());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTietMatHang(idMatHangChiTiet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                MatHang matHang = response.body().getMatHang();
                Glide.with(getApplicationContext()).load(matHang.getLinkAnh().get(0)).into(imgHinhAnh);
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText(matHang.getDiaChi());
                tvGiaBan.setText(matHang.getGiaBan() + "");
                tvNoiDung.setText(matHang.getMoTa());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });

        super.onResume();
    }
}