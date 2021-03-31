package com.dreamernguyen.ClientDuAn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.TimKiemMatHangActivity;
import com.dreamernguyen.ClientDuAn.R;

import java.util.List;

public class DanhMucGianHangAdapter extends RecyclerView.Adapter<DanhMucGianHangAdapter.DanhMucViewHolder> {

    Context context;
    List<String> mlist;

    public DanhMucGianHangAdapter(Context context, List<String> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_gian_hang, parent, false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        switch (position) {

            case 0:
                holder.imgDanhMuc.setImageResource(R.drawable.bat_dong_san);
                holder.tvTenDanhMuc.setText("Bất Động Sản");

                holder.imgDanhMuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context,TimKiemMatHangActivity.class);
                        intent.putExtra("hangMuc","Bất Động Sản");

                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                holder.imgDanhMuc.setImageResource(R.drawable.xe_co);
                holder.tvTenDanhMuc.setText("Xe Cộ");

                holder.imgDanhMuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context,TimKiemMatHangActivity.class);
                        intent.putExtra("hangMuc","Xe Cộ");

                        context.startActivity(intent);
                    }
                });
                break;

            case 2:
                holder.imgDanhMuc.setImageResource(R.drawable.sach);
                holder.tvTenDanhMuc.setText("Sách");

                break;

            case 3:
                holder.imgDanhMuc.setImageResource(R.drawable.do_dien_tu);
                holder.tvTenDanhMuc.setText("Đồ Điện Tử");

                break;

            case 4:
                holder.imgDanhMuc.setImageResource(R.drawable.thoi_trang);
                holder.tvTenDanhMuc.setText("Thời Trang");

                break;
        }

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class DanhMucViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenDanhMuc;
        ImageView imgDanhMuc;

        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDanhMuc = itemView.findViewById(R.id.tvTenDanhMuc);
            imgDanhMuc = itemView.findViewById(R.id.imgDanhMuc);
        }
    }

}
