package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Adapter.AnhAdapter;
import com.dreamernguyen.ClientDuAn.Adapter.AnhBaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangChiTietActivity extends AppCompatActivity {

    ViewPager vpgImage;
    TextView tvTieuDe, tvGiaBan, tvDiaChi, tvMota,tvSuaTin,tvXoaTin, tvThoiGianChiTiet, tvHoTen, tvSdt;
    String idMatHangChiTiet;
    Intent intent;
    CircleImageView imgAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mat_hang_chi_tiet);

        intent = getIntent();
        idMatHangChiTiet = intent.getStringExtra("idMatHangChiTiet");
        vpgImage = findViewById(R.id.vpgImage);
        tvTieuDe = findViewById(R.id.tvTieuDeChiTiet);
        tvGiaBan = findViewById(R.id.tvGiaChiTiet);
        tvDiaChi = findViewById(R.id.tvDiaChiChiTiet);
        tvMota = findViewById(R.id.tvNoiDungChiTiet);
        tvThoiGianChiTiet = findViewById(R.id.tvThoiGianChiTiet);
        tvSdt = findViewById(R.id.tvSdt);
        imgAvatar = findViewById(R.id.imgAvatar);
        tvSuaTin=findViewById(R.id.tvSuaTin);
        tvXoaTin=findViewById(R.id.tvXoaTin);
        tvHoTen = findViewById(R.id.tvHoTen);

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
                // khởi tạo
                MatHang matHang = response.body().getMatHang();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                format.setTimeZone(TimeZone.getTimeZone("UTC+7"));
                Calendar cal = Calendar.getInstance();
                Date now = cal.getTime();
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MatHangChiTietActivity.this, LinearLayoutManager.HORIZONTAL, false);
//                vpgImage.setLayoutManager(linearLayoutManager);
                // Xử lí dữ liệu
                String khong = "0";
                String mLinkAnh = response.body().getMatHang().getIdNguoiDung().getAvatar();
                List<String> listAnh = response.body().getMatHang().getLinkAnh();
                if (listAnh.size() > 0){
                    AnhBaiVietAdapter anhBaiVietAdapter = new AnhBaiVietAdapter(listAnh, false);
                    vpgImage.setAdapter(anhBaiVietAdapter);
                }
                try {
                    Date date = format.parse(response.body().getMatHang().getThoiGianTao());
                    long diff = now.getTime() - date.getTime();
                    long giay =  (diff / 1000);
                    long phut =  (diff / (1000 * 60));
                    long gio =  (diff / (1000 * 60 * 60));
                    long ngay =  (diff / (1000 * 60 * 60 * 24));
                    if(ngay > 3){
                        tvThoiGianChiTiet.setText(format2.format(date));
                    }
                    if (ngay < 3){
                        tvThoiGianChiTiet.setText(ngay + "ngày trước");
                    }
                    if( gio < 24 && gio > 0){
                        tvThoiGianChiTiet.setText(gio + " giờ trước");
                    }if(phut < 60 && phut > 0){
                        tvThoiGianChiTiet.setText(phut+ " phút trước");
                    }if(giay < 60 && giay > 0){
                        tvThoiGianChiTiet.setText("vừa xong");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // gán dữ liệu
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText("/"+matHang.getDiaChi());
                tvGiaBan.setText("Giá: "+matHang.getGiaBan() + " Đồng");
                tvMota.setText(matHang.getMoTa());
                tvHoTen.setText(matHang.getIdNguoiDung().getHoTen());
                tvSdt.setText("Số điện thoại: "+khong+matHang.getIdNguoiDung().getSoDienThoai()+"");
                Glide.with(getApplicationContext()).load(mLinkAnh).into(imgAvatar);
                Log.d("linkAnhAvatar", "onResponse: "+ mLinkAnh);
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
                tvTieuDe.setText(matHang.getTieuDe());
                tvDiaChi.setText(matHang.getDiaChi());
                tvGiaBan.setText(matHang.getGiaBan() + "");
                tvMota.setText(matHang.getMoTa());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });

        super.onResume();
    }
}