package com.dreamernguyen.ClientDuAn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Models.BinhLuan;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.BinhLuanViewHolder> {
    private Context context;
    private List<BinhLuan> listBinhLuan;

    public BinhLuanAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BinhLuan> list) {
        this.listBinhLuan = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BinhLuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binh_luan, parent, false);
        return new BinhLuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BinhLuanViewHolder holder, int position) {
        BinhLuan binhLuan = listBinhLuan.get(position);
        if(binhLuan.getIdNguoiDung().getAvatar().length() > 0){
            Glide.with(context).load(binhLuan.getIdNguoiDung().getAvatar()).into(holder.imgAvatar);
        }
        holder.tvTenNguoiDung.setText(binhLuan.getIdNguoiDung().getHoTen());
        holder.tvNoiDung.setText(binhLuan.getNoiDung());

    }

    @Override
    public int getItemCount() {
        if (listBinhLuan != null) {
            return listBinhLuan.size();
        }
        return 0;
    }

    public class BinhLuanViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung, tvNoiDung;
        CircleImageView imgAvatar;


        public BinhLuanViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
        }
    }
}


