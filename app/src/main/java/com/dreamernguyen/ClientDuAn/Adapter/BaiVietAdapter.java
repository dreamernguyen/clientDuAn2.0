package com.dreamernguyen.ClientDuAn.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Activity.BaiVietChiTietActivity;
import com.dreamernguyen.ClientDuAn.Activity.DangBaiActivity;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.MainActivity;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.like.LikeButton;
import com.like.OnLikeListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiVietAdapter extends RecyclerView.Adapter<BaiVietAdapter.BaiVietViewHolder> {
    private AnhBaiVietAdapter anhBaiVietAdapter;
    private Context context;
    private List<BaiViet> listBaiViet;


    public BaiVietAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BaiViet> list) {
        this.listBaiViet = list;
        notifyDataSetChanged();
    }
    public void random(){
        Collections.shuffle(listBaiViet);
    }

    @NonNull
    @Override
    public BaiVietViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bai_viet, parent, false);
        return new BaiVietViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiVietViewHolder holder, int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.layoutLoading.stopShimmer();
                holder.layoutLoading.hideShimmer();
                holder.layoutLoading.setVisibility(View.GONE);
                holder.layoutMain.setVisibility(View.VISIBLE);
            }
        },2000);
        BaiViet baiViet = listBaiViet.get(position);

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
                holder.tvThoiGian.setText(format2.format(date));
            }
            if (ngay <= 3) {
                holder.tvThoiGian.setText(ngay + " ngày trước");
            }
            if (gio < 24 && gio > 0) {
                holder.tvThoiGian.setText(gio + " giờ trước");
            }
            if (phut < 60 && phut > 0) {
                holder.tvThoiGian.setText(phut + " phút trước");
            }
            if (giay < 60 && giay > 0) {
                holder.tvThoiGian.setText("Vừa xong");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (baiViet.getDaAn()) {
            holder.tvTrangThai.setText("Chỉ mình tôi");
        } else {
            holder.tvTrangThai.setText("Công khai");
        }

        if(baiViet.getLinkAnh().size() >0){
            anhBaiVietAdapter  = new AnhBaiVietAdapter(baiViet.getLinkAnh(),false);
            holder.vpgAnh.setVisibility(View.VISIBLE);
            holder.vpgAnh.setAdapter(anhBaiVietAdapter);
        }else {
            holder.vpgAnh.setVisibility(View.GONE);
        }
        if(baiViet.getIdNguoiDung().getAvatar() != null && !baiViet.getIdNguoiDung().getAvatar().isEmpty() ){
            Glide.with(context).load(baiViet.getIdNguoiDung().getAvatar()).into(holder.imgAvatar);
        }else {
            holder.imgAvatar.setImageResource(R.drawable.logo_main);
        }

        holder.tvTenNguoiDung.setText(baiViet.getIdNguoiDung().getHoTen());
        holder.tvTenNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, TrangCaNhanActivity.class);
                i.putExtra("idNguoiDung",baiViet.getIdNguoiDung().getId());
                context.startActivity(i);
            }
        });
        holder.tvNoiDung.setText(baiViet.getNoiDung());
        holder.tvLuotTim.setText(baiViet.getLuotThich().size()+"");

        holder.imTuyChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View viewDailog = ((FragmentActivity) context).getLayoutInflater().inflate(R.layout.layout_bottom_sheet,null);
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.BottomSheetThemeCustom);
                bottomSheetDialog.setContentView(viewDailog);
                bottomSheetDialog.show();

                MaterialButton btnXoa,btnChinhSua,btnAn,btnAnKhoiToi,btnBaoCao,btnTheoDoi,btnBoTheoDoi;


                btnXoa = viewDailog.findViewById(R.id.btnXoa);
                btnChinhSua = viewDailog.findViewById(R.id.btnChinhSua);
                btnAn = viewDailog.findViewById(R.id.btnAn);
                btnAnKhoiToi = viewDailog.findViewById(R.id.btnAnKhoiToi);
                btnBaoCao = viewDailog.findViewById(R.id.btnBaoCao);
                btnTheoDoi = viewDailog.findViewById(R.id.btnTheoDoi);
                btnBoTheoDoi = viewDailog.findViewById(R.id.btnBoTheoDoi);
                if(baiViet.getIdNguoiDung().getDuocTheoDoi().size() > 0){
                    for(int i = 0; i < baiViet.getIdNguoiDung().getDuocTheoDoi().size();i++){
                        if(baiViet.getIdNguoiDung().getDuocTheoDoi().get(i).equals(LocalDataManager.getIdNguoiDung())){
                            btnBoTheoDoi.setVisibility(View.VISIBLE);
                            btnTheoDoi.setVisibility(View.GONE);

                        }else {
                            btnBoTheoDoi.setVisibility(View.GONE);
                            btnTheoDoi.setVisibility(View.VISIBLE);
                        }
                    }
                }
                if (baiViet.getIdNguoiDung().getId().equals(LocalDataManager.getIdNguoiDung())) {
                    btnXoa.setVisibility(View.VISIBLE);
                    btnChinhSua.setVisibility(View.VISIBLE);
                    btnAn.setVisibility(View.VISIBLE);
                    btnBaoCao.setVisibility(View.GONE);
                    btnAnKhoiToi.setVisibility(View.GONE);
                } else {
                    btnXoa.setVisibility(View.GONE);
                    btnChinhSua.setVisibility(View.GONE);
                    btnAn.setVisibility(View.GONE);
                    btnBaoCao.setVisibility(View.VISIBLE);
                    btnAnKhoiToi.setVisibility(View.VISIBLE);
                }
                btnXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
                        Call<DuLieuTraVe> call = ApiService.apiService.xoaBaiViet(baiViet.getId());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                Toast.makeText(context, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                listBaiViet.remove(position);
                                notifyItemChanged(position);
                                notifyItemRangeRemoved(position, listBaiViet.size());

                            }

                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                btnChinhSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        Intent intent = new Intent(context, DangBaiActivity.class);
                        intent.putExtra("chucNang","Cập nhật");
                        intent.putExtra("idBaiViet", baiViet.getId());
                        context.startActivity(intent);
                    }
                });
                btnAn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        Call<DuLieuTraVe> call = ApiService.apiService.anBaiViet(baiViet.getId());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                Toast.makeText(context, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                btnTheoDoi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                        Call<DuLieuTraVe> call = ApiService.apiService.theoDoi(LocalDataManager.getIdNguoiDung(),baiViet.getIdNguoiDung().getId());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                Toast.makeText(context, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                btnAnKhoiToi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<DuLieuTraVe> call = ApiService.apiService.anBaiVietKhoiToi(baiViet.getId(),baiViet.getIdNguoiDung().getId());
                        call.enqueue(new Callback<DuLieuTraVe>() {
                            @Override
                            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {

                            }

                            @Override
                            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

                            }
                        });
                    }
                });

            }

        });

        holder.tvBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                intent.putExtra("chucNang","Xem");
                intent.putExtra("idBaiViet", baiViet.getId());
                context.startActivity(intent);
            }
        });
        holder.tvNoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                intent.putExtra("chucNang","Xem");
                intent.putExtra("idBaiViet", baiViet.getId());
                context.startActivity(intent);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                intent.putExtra("chucNang","Xem");
                intent.putExtra("idBaiViet", baiViet.getId());
                context.startActivity(intent);
            }
        });

        if(baiViet.getLuotThich().size() == 0){
            holder.btnLike.setLiked(false);
        }else {
            for(int i = 0; i < baiViet.getLuotThich().size();i++){
                if(baiViet.getLuotThich().get(i).getId().equals(LocalDataManager.getIdNguoiDung())){
                    holder.btnLike.setLiked(true);
                }else {
                    holder.btnLike.setLiked(false);
                }
            }
        }

        holder.btnLike.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Call<DuLieuTraVe> call = ApiService.apiService.thichBaiViet(baiViet.getId(),LocalDataManager.getIdNguoiDung());
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        Log.d("Like", "onResponse: Đã like bài viết");
                        int soTim = baiViet.getLuotThich().size() + 1;
                        holder.tvLuotTim.setText(soTim+"");

                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                        Log.d("Like", "onFailure: "+t.getMessage());
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                Call<DuLieuTraVe> call = ApiService.apiService.boThichBaiViet(baiViet.getId(),LocalDataManager.getIdNguoiDung());
                call.enqueue(new Callback<DuLieuTraVe>() {
                    @Override
                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                        Log.d("Like", "onResponse: Đã dislike bài viết");
                        int soTim = baiViet.getLuotThich().size();
                        holder.tvLuotTim.setText(soTim+"");

                    }

                    @Override
                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                        Log.d("Like", "onFailure: "+t.getMessage());
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        if (listBaiViet != null) {
            return listBaiViet.size();
        }
        return 0;
    }

    public class BaiVietViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung, tvThoiGian, tvNoiDung, tvTrangThai, tvLuotTim,tvBinhLuan;
        ViewPager vpgAnh;
        ImageView imTuyChinh;
        CircleImageView imgAvatar;
        LikeButton btnLike;
        LinearLayout layout,layoutMain;
        ShimmerFrameLayout layoutLoading;

        public BaiVietViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian2);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            tvLuotTim = itemView.findViewById(R.id.tvTim);
            tvBinhLuan = itemView.findViewById(R.id.tvBinhLuan);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            vpgAnh = itemView.findViewById(R.id.vpgImage);
            imTuyChinh = itemView.findViewById(R.id.imTuyChinh);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            btnLike = itemView.findViewById(R.id.btnLike);
            layoutLoading = itemView.findViewById(R.id.layoutLoading);
            layoutMain = itemView.findViewById(R.id.layoutMain);


        }
    }


}