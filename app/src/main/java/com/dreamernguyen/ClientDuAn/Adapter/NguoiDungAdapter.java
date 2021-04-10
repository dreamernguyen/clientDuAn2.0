package com.dreamernguyen.ClientDuAn.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.Activity.NhanTinActivity;
import com.dreamernguyen.ClientDuAn.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NguoiDungAdapter extends  RecyclerView.Adapter<NguoiDungAdapter.NguoiDungViewHolder> {
    private Context context;
    private List<NguoiDung> listNguoiDung;

    public NguoiDungAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<NguoiDung> list) {
        this.listNguoiDung = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NguoiDungViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lien_he, parent, false);
        return new NguoiDungViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NguoiDungViewHolder holder, int position) {
        NguoiDung nguoiDung = listNguoiDung.get(position);

        if(nguoiDung.getAvatar().length() > 0){
            Glide.with(context).load(nguoiDung.getAvatar()).into(holder.imgAvatar);
        }
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrangCaNhanActivity.class);
                intent.putExtra("idNguoiDung", nguoiDung.getId());
                context.startActivity(intent);
            }
        });
        holder.tvTenNguoiDung.setText(nguoiDung.getHoTen());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NhanTinActivity.class);
                intent.putExtra("activity","MainActivity");
                intent.putExtra("idNguoiDung", nguoiDung.getId());
                intent.putExtra("tenNguoiDung", nguoiDung.getHoTen());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        if (listNguoiDung != null) {
            return listNguoiDung.size();
        }
        return 0;
    }

    public class NguoiDungViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenNguoiDung, tvNoiDung;
        CircleImageView imgAvatar;
        MaterialCardView cardView;


        public NguoiDungViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvTenNguoiDung = itemView.findViewById(R.id.tvTenNguoiDung);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            cardView = itemView.findViewById(R.id.cvLienHe);
        }
    }
}
