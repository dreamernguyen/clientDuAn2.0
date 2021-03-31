package com.dreamernguyen.ClientDuAn.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Adapter.DanhMucGianHangAdapter;
import com.dreamernguyen.ClientDuAn.Adapter.MatHangAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GianHangFragment extends Fragment {
    RecyclerView rvDanhSach,rvDanhMuc;
    MatHangAdapter matHangAdapter;
    SwipeRefreshLayout refreshLayout;
    CircleImageView imgAvatar;
    AppCompatButton ACButtonDangSanPham;

    List<MatHang> listHienTai;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gian_hang, container, false);
        rvDanhSach = view.findViewById(R.id.rvDanhSach);
        rvDanhMuc = view.findViewById(R.id.rvDanhMuc);
        ACButtonDangSanPham=view.findViewById(R.id.ACButtonDangSanPham);
        imgAvatar=view.findViewById(R.id.imgAvatar);
        refreshLayout=view.findViewById(R.id.refreshLayout);

        List<String> list = new ArrayList<>(Arrays.asList("Bất Động Sản", "Xe Cộ", "Sách", "Đồ Điện Tử", "Thời Trang"));

        DanhMucGianHangAdapter danhMucGianHangAdapter= new DanhMucGianHangAdapter(getActivity(),list);
        matHangAdapter = new MatHangAdapter(getActivity());


        ACButtonDangSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DangMatHangActivity.class);
                startActivity(intent);
            }
        });

        loadMatHang();
//        Glide.with(this).load(LocalDataManager.getNguoiDung().getAvatar()).circleCrop().into(imgAvatar);

        rvDanhMuc.setAdapter(danhMucGianHangAdapter);
        rvDanhMuc.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        rvDanhSach.setAdapter(matHangAdapter);
        rvDanhSach.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return view;
    }

    public void loadMatHang() {
        listHienTai = new ArrayList<>();
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachMatHang();
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                listHienTai.addAll(response.body().getDanhSachMatHang());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        matHangAdapter.setData(listHienTai);
        super.onResume();
    }
}