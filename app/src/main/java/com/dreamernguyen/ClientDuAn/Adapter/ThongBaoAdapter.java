package com.dreamernguyen.ClientDuAn.Adapter;

import android.app.Activity;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dreamernguyen.ClientDuAn.Activity.BaiVietChiTietActivity;
import com.dreamernguyen.ClientDuAn.Activity.NhanTinActivity;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.Models.ThongBao;
import com.dreamernguyen.ClientDuAn.R;

import java.util.List;

public class ThongBaoAdapter extends  RecyclerView.Adapter<ThongBaoAdapter.ThongBaoViewHolder> {
    private Context context;
    private List<ThongBao> listThongBao;

    public ThongBaoAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ThongBao> list) {
        this.listThongBao = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ThongBaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thong_bao, parent, false);
        return new ThongBaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongBaoViewHolder holder, int position) {
        ThongBao thongBao = listThongBao.get(position);
        if(thongBao != null){
            holder.tvNoiDung.setText(thongBao.getNoiDung());
            if(thongBao.getLoaiThongBao().equals("BaiViet")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                        intent.putExtra("chucNang","Xem");
                        intent.putExtra("idBaiViet", thongBao.getIdTruyXuat());
                        context.startActivity(intent);
                    }
                });
            }
            if(thongBao.getLoaiThongBao().equals("NguoiDung")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TrangCaNhanActivity.class);
                        intent.putExtra("chucNang","Xem");
                        intent.putExtra("idNguoiDung", thongBao.getIdTruyXuat());
                        context.startActivity(intent);
                    }
                });
            }
            if(thongBao.getLoaiThongBao().equals("MatHang")){
                holder.layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BaiVietChiTietActivity.class);
                        intent.putExtra("chucNang","Xem");
                        intent.putExtra("idBaiViet", thongBao.getIdTruyXuat());
                        context.startActivity(intent);
                    }
                });
            }
        }




    }

    @Override
    public int getItemCount() {
        if (listThongBao != null) {
            return listThongBao.size();
        }
        return 0;
    }

    public class ThongBaoViewHolder extends RecyclerView.ViewHolder {
        TextView  tvNoiDung;
        LinearLayout layout;

        public ThongBaoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoiDung = itemView.findViewById(R.id.tvNoiDung);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
