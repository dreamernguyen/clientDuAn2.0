package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Adapter.AnhBaiVietAdapter;
import com.dreamernguyen.ClientDuAn.Adapter.BinhLuanAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.BinhLuan;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.R;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietChiTietActivity extends AppCompatActivity {
    TextView tvNoiDung,tvTenNguoiDung;
    List<String> listAnh = new ArrayList<>();
    AnhBaiVietAdapter anhBaiVietAdapter;
    BinhLuanAdapter binhLuanAdapter;
    ViewPager vpgAnh;
    String idBaiViet;
    RecyclerView rvBinhLuan;
    ImageView btnGui;
    TextView tvTim,tvTrangThai,tvThoiGian;
    LikeButton btnLike;
    EditText edBinhLuan;
    CircleImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_viet_chi_tiet);

        tvNoiDung = findViewById(R.id.tvNoiDung);
        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvTrangThai = findViewById(R.id.tvTrangThai);
        tvThoiGian = findViewById(R.id.tvThoiGian);
        vpgAnh = findViewById(R.id.vpgImage);
        tvTim = findViewById(R.id.tvTim);
        btnLike = findViewById(R.id.btnLike);
        imgAvatar = findViewById(R.id.imgAvatar);

        binhLuanAdapter = new BinhLuanAdapter(this);
        rvBinhLuan = findViewById(R.id.rvBinhLuan);
        btnGui = findViewById(R.id.btnGui);
        edBinhLuan = findViewById(R.id.edBinhLuan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rvBinhLuan.setLayoutManager(linearLayoutManager);
        Intent i = getIntent();
        if(i.getStringExtra("chucNang").equals("Xem")){
            idBaiViet = i.getStringExtra("idBaiViet");
            loadChiTiet(idBaiViet);
            loadBinhLuan(idBaiViet);
            btnGui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binhLuan(idBaiViet);
                }
            });
        }else {
            Toast.makeText(this, "Tạo bài mới", Toast.LENGTH_SHORT).show();
        }
        btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Call<DuLieuTraVe> call = ApiService.apiService.thichBaiViet(idBaiViet,LocalDataManager.getIdNguoiDung());
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        Log.d("Like", "onResponse: Đã like bài viết");
                        int soTim = Integer.parseInt(tvTim.getText().toString() )+ 1;
                        tvTim.setText(soTim+"");

                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Lỗi like", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Call<DuLieuTraVe> call = ApiService.apiService.boThichBaiViet(idBaiViet,LocalDataManager.getIdNguoiDung());
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        Log.d("Like", "onResponse: Đã like bài viết");
                        int soTim = Integer.parseInt(tvTim.getText().toString() ) - 1;
                        tvTim.setText(soTim+" Yêu thích");

                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Lỗi dislike", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });




    }
    private void loadChiTiet(String idBaiViet){
        Call<DuLieuTraVe> call = ApiService.apiService.xemChiTiet(idBaiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                BaiViet baiViet = response.body().getBaiViet();
                tvTenNguoiDung.setText(baiViet.getIdNguoiDung().getHoTen());
                Glide.with(getApplicationContext()).load(baiViet.getIdNguoiDung().getAvatar()).into(imgAvatar);
                tvTim.setText(baiViet.getLuotThich().size()+"");
                tvNoiDung.setText(baiViet.getNoiDung());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                format.setTimeZone(TimeZone.getTimeZone("UTC+7"));

                try {
                    Date date = format.parse(baiViet.getThoiGianTao());
                    long diff = now.getTime() - date.getTime();
                    long giay = (diff / 1000);
                    long phut = (diff / (1000 * 60));
                    long gio = (diff / (1000 * 60 * 60));
                    long ngay = (diff / (1000 * 60 * 60 * 24));

                    if (ngay > 3) {
                        tvThoiGian.setText(format2.format(date));
                    }
                    if (ngay <= 3) {
                        tvThoiGian.setText(ngay + " ngày trước");
                    }
                    if (gio < 24 && gio > 0) {
                        tvThoiGian.setText(gio + " giờ trước");
                    }
                    if (phut < 60 && phut > 0) {
                        tvThoiGian.setText(phut + " phút trước");
                    }
                    if (giay < 60 && giay > 0) {
                        tvThoiGian.setText("Vừa xong");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (baiViet.getDaAn()) {
                    tvTrangThai.setText("Chỉ mình tôi");
                } else {
                    tvTrangThai.setText("Công khai");
                }
                if(baiViet.getLinkAnh().size() > 0){
                    listAnh = baiViet.getLinkAnh();
                    anhBaiVietAdapter = new AnhBaiVietAdapter(listAnh,true);
                    vpgAnh.setVisibility(View.VISIBLE);
                    vpgAnh.setAdapter(anhBaiVietAdapter);
                }else {
                    vpgAnh.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(BaiVietChiTietActivity.this, "Lỗi" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadBinhLuan(String idBaiViet){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachBinhLuan(idBaiViet);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<BinhLuan> listBinhLuan = response.body().getDanhSachBinhLuan();
                Log.d("ssss", "onResponse: "+listBinhLuan);
                if(listBinhLuan.size() > 0){
                    binhLuanAdapter.setData(listBinhLuan);
                    rvBinhLuan.setAdapter(binhLuanAdapter);
                }

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Toast.makeText(BaiVietChiTietActivity.this, "Lỗi" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void binhLuan(String idBaiViet){
        if(edBinhLuan.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập bình luận !", Toast.LENGTH_SHORT).show();
        }else {
            //ẩn bàn phím
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(edBinhLuan.getApplicationWindowToken(), 0);
            BinhLuan binhLuan = new BinhLuan(idBaiViet, edBinhLuan.getText().toString());
            Call<DuLieuTraVe> call = ApiService.apiService.binhLuan(LocalDataManager.getIdNguoiDung(), binhLuan);
            call.enqueue(new Callback<DuLieuTraVe>() {
                @Override
                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                    Toast.makeText(BaiVietChiTietActivity.this, "" + response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                    loadBinhLuan(idBaiViet);
                    edBinhLuan.setText("");
                }

                @Override
                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                    Toast.makeText(BaiVietChiTietActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}