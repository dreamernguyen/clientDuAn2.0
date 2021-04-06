package com.dreamernguyen.ClientDuAn.Fragment;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamernguyen.ClientDuAn.Adapter.BaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.Anh;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheoDoiFragment extends Fragment {
    RecyclerView rvBaiViet;
    BaiVietAdapter baiVietAdapter;
    SwipeRefreshLayout refreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_theo_doi, container, false);
        rvBaiViet =  view.findViewById(R.id.rvBaiViet);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        baiVietAdapter = new BaiVietAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        rvBaiViet.setLayoutManager(linearLayoutManager);
        rvBaiViet.setAdapter(baiVietAdapter);
        loadBaiVietTheoDoi();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBaiVietTheoDoi();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        return view;
    }
    public void loadBaiVietTheoDoi(){
        Call<DuLieuTraVe> call = ApiService.apiService.danhSachTheoDoi(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                List<BaiViet> listBaiViet = response.body().getDanhSachBaiViet();
                if(listBaiViet.size() > 0){
                    baiVietAdapter.setData(listBaiViet);
                }else {
                    Log.d("loadBaiVietTheoDoi", "onFailure: danh sách bài viết trống");
                }
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("loadBaiVietTheoDoi", "onFailure: "+t.getMessage());
                Toast.makeText(getActivity(), "Lỗi load bài viết theo dõi !\n"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}