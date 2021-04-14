package com.dreamernguyen.ClientDuAn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.MatHangChiTietActivity;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatHangAdapter extends RecyclerView.Adapter<MatHangAdapter.MatHangViewHolder> {
    private Context context;
    private List<MatHang> listMatHang;

    public MatHangAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<MatHang> list) {
        this.listMatHang = list;
        notifyDataSetChanged();
    }
    public void random(){
        Collections.shuffle(listMatHang);
    }

    @NonNull
    @Override
    public MatHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mat_hang, parent, false);
        return new MatHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatHangViewHolder holder, int position) {
        MatHang matHang = listMatHang.get(position);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        Date now = cal.getTime();
        Log.d("now", "onBindViewHolder: " + now);
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        format.setTimeZone(TimeZone.getTimeZone("UTC+7"));


        try {
            Date date = format.parse(matHang.getThoiGianTao());
            long diff = now.getTime() - date.getTime();
            long giay = (diff / 1000);
            long phut = (diff / (1000 * 60));
            long gio = (diff / (1000 * 60 * 60));
            long ngay = (diff / (1000 * 60 * 60 * 24));
            if (ngay > 3) {
                holder.tvThoiGian.setText(format2.format(date));
            }
            if (ngay < 3) {
                holder.tvThoiGian.setText(ngay + "ngày trước");
            }
            if (gio < 24 && gio > 0) {
                holder.tvThoiGian.setText(gio + " giờ trước");
            }
            if (phut < 60 && phut > 0) {
                holder.tvThoiGian.setText(phut + " phút trước");
            }
            if (giay < 60 && giay > 0) {
                holder.tvThoiGian.setText("vừa xong");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvTieuDe.setText(matHang.getTieuDe());
        holder.tvGiaBan.setText( matHang.getGiaBan()+" VND");
        String thanhPho=matHang.getDiaChi().replaceAll("(?=\\-)(.*?)$", "");
        holder.tvDiaChi.setText(thanhPho);
        Glide.with(context).load(matHang.getLinkAnh().get(0)).into(holder.imgHinhAnh);
        holder.lnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context,MatHangChiTietActivity.class);
                intent.putExtra("idMatHangChiTiet",matHang.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listMatHang != null) {
            return listMatHang.size();
        }
        return 0;
    }

    public class MatHangViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe, tvGiaBan, tvDiaChi, tvThoiGian, tvSoAnh;

        LinearLayout lnChiTiet;
        ImageView imgTuyChinh, imgHinhAnh;


        public MatHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTieuDe = itemView.findViewById(R.id.tvTieuDe);
            tvGiaBan = itemView.findViewById(R.id.tvGiaBan);
            tvDiaChi=itemView.findViewById(R.id.tvDiaChi);
            tvThoiGian=itemView.findViewById(R.id.tvThoiGian);
            imgHinhAnh=itemView.findViewById(R.id.imgHinhAnh);
            lnChiTiet=itemView.findViewById(R.id.lnChiTiet);
        }
    }
}

