package com.dreamernguyen.ClientDuAn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Adapter.BaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Activity.DangBaiActivity;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KhamPhaFragment extends Fragment {
    RecyclerView rvBaiViet;
    BaiVietAdapter baiVietAdapter;
    SwipeRefreshLayout refreshLayout;
    TextView tv;
    ImageView imAvatar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kham_pha, container, false);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        tv = view.findViewById(R.id.tv1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),DangBaiActivity.class);
                i.putExtra("chucNang","Đăng bài");
                startActivity(i);
            }
        });
        imAvatar = view.findViewById(R.id.avatar);
        rvBaiViet =  view.findViewById(R.id.rvBaiViet);
        baiVietAdapter = new BaiVietAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvBaiViet.setLayoutManager(linearLayoutManager);
        rvBaiViet.setAdapter(baiVietAdapter);
        loadBaiVietKhamPha();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBaiVietKhamPha();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        Glide.with(this).load(LocalDataManager.getNguoiDung().getAvatar()).circleCrop().into(imAvatar);
        return view;

    }
    public void loadBaiVietKhamPha(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachBaiViet();
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<BaiViet> listBaiViet = response.body().getDanhSachBaiViet();
                if(listBaiViet.size() > 0){
                    baiVietAdapter.setData(listBaiViet);
                    baiVietAdapter.random();
                }else {
                    Log.d("loadBaiVietKhamPha", "onFailure: danh sách bài viết trống");
                }
            }
            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadBaiVietKhamPha", "onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "Lỗi load bài viết khám phá !\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}