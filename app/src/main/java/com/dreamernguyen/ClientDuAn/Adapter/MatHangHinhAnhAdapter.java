package com.dreamernguyen.ClientDuAn.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.R;

import java.util.ArrayList;
import java.util.List;

public class MatHangHinhAnhAdapter extends RecyclerView.Adapter<MatHangHinhAnhAdapter.HinhAnhViewHolder> {

    Context context;
    List<String>  listAnh;

    public MatHangHinhAnhAdapter(Context context) {
        this.context = context;
    }

    public void setListAnh(List<String> listAnh) {
        this.listAnh = listAnh;
    }
    @NonNull
    @Override
    public HinhAnhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_anh, null);
        return new HinhAnhViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HinhAnhViewHolder holder, int position) {

        Glide.with(context).load(listAnh.get(position)).into(holder.imgBaiViet);

        Log.d("a", "listURI: " + DangMatHangActivity.listURI.size() + " position :" + position + " listString: " + DangMatHangActivity.listString.size());
        holder.xoa.setOnClickListener(v -> {
            DangMatHangActivity.listAnh.remove(position);
            if (position + 1 <= DangMatHangActivity.listURI.size()) {
                DangMatHangActivity.listURI.remove(position);

            } else {
                DangMatHangActivity.listString.remove(position - DangMatHangActivity.listURI.size());

            }
            notifyItemRemoved(position);
            notifyDataSetChanged();
        });
    }


    @Override
    public int getItemCount() {
        if (!listAnh.isEmpty()) {
            return listAnh.size();
        }
        return 0;
    }

    public class HinhAnhViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBaiViet, xoa;

        public HinhAnhViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBaiViet = itemView.findViewById(R.id.imBaiViet);
            xoa = itemView.findViewById(R.id.xoa);
        }
    }
}
