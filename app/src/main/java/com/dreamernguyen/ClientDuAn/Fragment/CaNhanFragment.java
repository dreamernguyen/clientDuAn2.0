package com.dreamernguyen.ClientDuAn.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamernguyen.ClientDuAn.Activity.BaiVietYeuThichActivity;
import com.dreamernguyen.ClientDuAn.Activity.ChinhSuaThongTinActivity;
import com.dreamernguyen.ClientDuAn.Activity.MatHangQuanTamActivity;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class CaNhanFragment extends Fragment {
    MaterialCardView cvXemTrangCaNhan;
    MaterialButton btnBaiVietYeuThich,btnMatHangToiRao,btnMatHangDaLuu,btnBaiVietDaAn,btnChinhSuaThongTin,btnDoiMatKhau,btnDangXuat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);

        cvXemTrangCaNhan = view.findViewById(R.id.cvXemTrangCaNhan);
        btnBaiVietYeuThich = view.findViewById(R.id.btnBaiVietYeuThich);
        btnMatHangToiRao = view.findViewById(R.id.btnMatHangToiRao);
        btnMatHangDaLuu = view.findViewById(R.id.btnMatHangDaLuu);
        btnBaiVietDaAn = view.findViewById(R.id.btnBaiVietDaAn);
        btnChinhSuaThongTin = view.findViewById(R.id.btnChinhSuaThongTin);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);

        cvXemTrangCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrangCaNhanActivity.class);
                i.putExtra("idNguoiDung",LocalDataManager.getIdNguoiDung());
                startActivity(i);
            }
        });
        btnBaiVietYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), BaiVietYeuThichActivity.class);
                startActivity(i);
            }
        });
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChinhSuaThongTinActivity.class);
                i.putExtra("ChucNang","SuaThongTin");
                startActivity(i);
            }
        });
        btnMatHangToiRao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), com.dreamernguyen.ClientDuAn.Activity.MatHangToiRaoActivity.class);
                startActivity(i);
            }
        });
        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ChinhSuaThongTinActivity.class);
                i.putExtra("ChucNang","DoiMatKhau");
                startActivity(i);
            }
        });
        btnMatHangDaLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MatHangQuanTamActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

}