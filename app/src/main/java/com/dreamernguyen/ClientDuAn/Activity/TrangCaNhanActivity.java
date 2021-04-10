package com.dreamernguyen.ClientDuAn.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrangCaNhanActivity extends AppCompatActivity {
    TextView tvTenNguoiDung, tvDangTheoDoi, tvNguoiTheoDoi, tvTrong;
    RecyclerView rcvBaiViet;
    BaiVietAdapter baiVietAdapter;
    CircleImageView imgAvatar;
    ImageView imQR, imgMenu;
    String idNguoiDung;
    LinearLayout layoutBottom;
    MaterialButton btnTheoDoi, btnChat, btnDangBai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_ca_nhan);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        Intent i = getIntent();
        if (i.getExtras() != null) {
            idNguoiDung = i.getStringExtra("idNguoiDung");
            loadTrangCaNhan(idNguoiDung);
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng !", Toast.LENGTH_SHORT).show();
        }

        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        imgMenu = findViewById(R.id.imgMenu);
        tvTenNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrangCaNhanActivity.this, tvTenNguoiDung.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        tvDangTheoDoi = findViewById(R.id.tvDangTheoDoi);
        tvNguoiTheoDoi = findViewById(R.id.tvNguoiTheoDoi);
        tvTrong = findViewById(R.id.tvTrong);
        rcvBaiViet = findViewById(R.id.rcvBaiViet);
        btnDangBai = findViewById(R.id.btnDangBai);
        imgAvatar = findViewById(R.id.imgAvatar);
        imQR = findViewById(R.id.imQR);
        layoutBottom = findViewById(R.id.layoutBottom);
        btnChat = findViewById(R.id.btnChat);
        btnTheoDoi = findViewById(R.id.btnTheoDoi);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TrangCaNhanActivity.this, RecyclerView.VERTICAL, false);
        rcvBaiViet.setLayoutManager(linearLayoutManager);
        baiVietAdapter = new BaiVietAdapter(TrangCaNhanActivity.this);
        rcvBaiViet.setAdapter(baiVietAdapter);
        loadTrangCaNhan(idNguoiDung);
        taoQR();

    }

    private void loadTrangCaNhan(String idNguoiDung) {
        Call<DuLieuTraVe> call = ApiService.apiService.xemTrangCaNhan(idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                NguoiDung nguoiDung = response.body().getNguoiDung();
                if (nguoiDung.getId().equals(LocalDataManager.getIdNguoiDung())) {
                    layoutBottom.setVisibility(View.GONE);
                    imgMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TrangCaNhanActivity.this, "Bản thân", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    btnDangBai.setVisibility(View.GONE);
                    imgMenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TrangCaNhanActivity.this, "Của người khác", Toast.LENGTH_SHORT).show();
                        }
                    });
                    btnChat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), NhanTinActivity.class);
                            intent.putExtra("activity", "TrangCaNhan");
                            intent.putExtra("idNguoiDung", nguoiDung.getId());
                            intent.putExtra("tenNguoiDung", nguoiDung.getHoTen());
                            startActivity(intent);
                        }
                    });

                    if (nguoiDung.getDuocTheoDoi().size() > 0) {
                        for (int i = 0; i < nguoiDung.getDuocTheoDoi().size(); i++) {
                            if (nguoiDung.getDuocTheoDoi().get(i).equals(LocalDataManager.getIdNguoiDung())) {
                                btnTheoDoi.setText("Đang theo dõi");
                                btnTheoDoi.setIcon(getDrawable(R.drawable.ic_user_check));
                                btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        boTheoDoi();
                                        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                theoDoi();
                                            }
                                        });
                                    }
                                });
                            } else {
                                btnTheoDoi.setText("Theo dõi");
                                btnTheoDoi.setIcon(getDrawable(R.drawable.ic_user_add));
                                btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        theoDoi();
                                        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                boTheoDoi();
                                            }
                                        });
                                    }
                                });

                            }
                        }
                    } else {
                        btnDangBai.setVisibility(View.GONE);
                        btnTheoDoi.setText("Theo dõi");
                        btnTheoDoi.setIcon(getDrawable(R.drawable.ic_user_add));
                        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                theoDoi();
                                btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        boTheoDoi();
                                    }
                                });
                            }
                        });
                    }

                }
                tvTenNguoiDung.setText(nguoiDung.getHoTen());
                tvDangTheoDoi.setText(nguoiDung.getDangTheoDoi().size() + "");
                tvNguoiTheoDoi.setText(nguoiDung.getDuocTheoDoi().size() + "");
                if (nguoiDung.getAvatar() != null && !nguoiDung.getAvatar().isEmpty()) {
                    Glide.with(TrangCaNhanActivity.this).load(nguoiDung.getAvatar()).into(imgAvatar);
                }
                List<BaiViet> listBaiViet = response.body().getDanhSachBaiViet();
                if (listBaiViet.size() > 0) {
                    baiVietAdapter.setData(listBaiViet);
                    rcvBaiViet.setVisibility(View.VISIBLE);
                    tvTrong.setVisibility(View.GONE);

                } else {
                    tvTrong.setVisibility(View.VISIBLE);
                    rcvBaiViet.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadTrangCaNhan", "onFailure: " + t.getMessage());
                Toast.makeText(TrangCaNhanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void taoQR() {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = writer.encode(LocalDataManager.getIdNguoiDung(), BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(bitMatrix);
            imQR.setImageBitmap(bitmap);


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void theoDoi() {
        Call<DuLieuTraVe> call = ApiService.apiService.theoDoi(LocalDataManager.getIdNguoiDung(), idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                tvNguoiTheoDoi.setText((Integer.parseInt(tvNguoiTheoDoi.getText().toString()) + 1)+"");
                btnTheoDoi.setText("Đang theo dõi");
                btnTheoDoi.setIcon(getDrawable(R.drawable.ic_user_check));
                Toast.makeText(TrangCaNhanActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("TheoDoi", "onFailure: " + t.getMessage());
            }
        });
    }

    public void boTheoDoi() {
        Call<DuLieuTraVe> call = ApiService.apiService.huyTheoDoi(LocalDataManager.getIdNguoiDung(), idNguoiDung);
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                tvNguoiTheoDoi.setText((Integer.parseInt(tvNguoiTheoDoi.getText().toString()) - 1)+"");
                btnTheoDoi.setText("Theo dõi");
                btnTheoDoi.setIcon(getDrawable(R.drawable.ic_user_add));
                Toast.makeText(TrangCaNhanActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("HuyTheoDoi", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}