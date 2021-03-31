package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Adapter.BaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangCaNhanActivity extends AppCompatActivity {
    TextView tvTenNguoiDung, tvDangTheoDoi, tvTheoDoi, btnDangBai,btnTheoDoi,btnChat,tv;
    RecyclerView rcvBaiViet;
    BaiVietAdapter baiVietAdapter;
    CircleImageView imgAvatar;
    ImageView imQR;
    String idNguoiDung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);



        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvDangTheoDoi = findViewById(R.id.tvDangTheoDoi);
        tvTheoDoi = findViewById(R.id.tvTheoDoi);
        tv = findViewById(R.id.tv);
        rcvBaiViet = findViewById(R.id.rcvBaiViet);
        btnDangBai = findViewById(R.id.btnDangBai);
        imgAvatar = findViewById(R.id.imgAvatar);
        imQR = findViewById(R.id.imQR);
        btnChat = findViewById(R.id.btnChat);
        btnTheoDoi = findViewById(R.id.btnTheoDoi);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        rcvBaiViet.setLayoutManager(linearLayoutManager);
        baiVietAdapter = new BaiVietAdapter(getApplicationContext());
        rcvBaiViet.setAdapter(baiVietAdapter);
        Intent i = getIntent();
        if(i.getExtras() != null){
            idNguoiDung = i.getStringExtra("idNguoiDung");
            loadTrangCaNhan(idNguoiDung);
        }else {
            Toast.makeText(this, "Không tìm thấy người dùng !", Toast.LENGTH_SHORT).show();
        }


    }
    private void loadTrangCaNhan(String idNguoiDung){
        Call<DuLieuTraVe> call = ApiService.apiService.xemTrangCaNhan(idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                NguoiDung nguoiDung = response.body().getNguoiDung();
                NguoiDung nguoiDung1 = LocalDataManager.getNguoiDung();
                if(nguoiDung.getDuocTheoDoi().size() > 0){
                    for(int i = 0; i < nguoiDung.getDuocTheoDoi().size();i++){
                        if(nguoiDung.getDuocTheoDoi().get(i).equals(LocalDataManager.getIdNguoiDung())){
                            btnTheoDoi.setText("Đã theo dõi");
                            btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(TrangCaNhanActivity.this, "Đã hủy theo dõi", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }else {
                    btnTheoDoi.setText("Theo dõi");
                    btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TrangCaNhanActivity.this, "Đã theo dõi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                tvTenNguoiDung.setText(nguoiDung.getHoTen());
                tvDangTheoDoi.setText(nguoiDung.getDangTheoDoi().size()+"");
                tvTheoDoi.setText(nguoiDung.getDuocTheoDoi().size()+"");
                btnChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), NhanTinActivity.class);
                        intent.putExtra("activity","TrangCaNhan");
                        intent.putExtra("idNguoiDung", nguoiDung.getId());
                        intent.putExtra("tenNguoiDung",nguoiDung.getHoTen() );
                        startActivity(intent);
                    }
                });
                if(nguoiDung.getAvatar() != null && !nguoiDung.getAvatar().isEmpty() ){
                    Glide.with(getApplicationContext()).load(nguoiDung.getAvatar()).into(imgAvatar);
                }
                List<BaiViet> listBaiViet = response.body().getDanhSachBaiViet();
                if(listBaiViet.size() > 0){
                    tv.setVisibility(View.GONE);
                    rcvBaiViet.setVisibility(View.VISIBLE);
                    baiVietAdapter.setData(listBaiViet);
                }else {
                    tv.setVisibility(View.VISIBLE);
                    rcvBaiViet.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("Lỗi", "onFailure: "+t.getMessage());
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}