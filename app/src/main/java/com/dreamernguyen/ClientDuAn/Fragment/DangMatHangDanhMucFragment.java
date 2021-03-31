package com.dreamernguyen.ClientDuAn.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.R;

public class DangMatHangDanhMucFragment extends Fragment {

    TextView tvDanhMuc, tvDanhMucCon, tvBack;
    AppCompatButton btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_danh_muc, container, false);

        ((DangMatHangActivity) getActivity()).setFragmentRefreshListener(new DangMatHangActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                layGiaTri();
                hienThiBtn();
                nhanDanhMucCon();
            }
        });

        tvBack = view.findViewById(R.id.tvBack);

        tvDanhMuc = view.findViewById(R.id.tvDanhMuc);
        tvDanhMucCon = view.findViewById(R.id.tvDanhMucCon);
        btn = view.findViewById(R.id.btn);

        layGiaTri();
        hienThiBtn();
        nhanDanhMucCon();

        btn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToHinhAnh);
            DangMatHangActivity.viTri = 1;
            btn.setVisibility(View.GONE);
        });

        tvDanhMuc.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToThongTin);
            denThongTin();
            DangMatHangActivity.DanhMuc = "";
            DangMatHangActivity.DanhMucCon = "";

        });

        tvDanhMucCon.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDanhMucToThongTin);
            denThongTin();
            DangMatHangActivity.DanhMucCon = "";

        });
        return view;
    }

    @Override
    public void onResume() {
        layGiaTri();
        hienThiBtn();
        nhanDanhMucCon();
        super.onResume();
    }

    public void layGiaTri() {
        tvDanhMuc.setText(DangMatHangActivity.DanhMuc);
        tvDanhMucCon.setText(DangMatHangActivity.DanhMucCon);
    }

    public void hienThiBtn() {

        if (DangMatHangActivity.DanhMuc != null && !DangMatHangActivity.DanhMuc.isEmpty()) {
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }
    }

    public void denThongTin() {
        btn.setVisibility(View.GONE);
        DangMatHangActivity.viTri = 0;
    }

    public void nhanDanhMucCon() {
        if (DangMatHangActivity.DanhMuc != null && !DangMatHangActivity.DanhMuc.isEmpty()) {
            tvDanhMucCon.setEnabled(true);
        } else {
            tvDanhMucCon.setEnabled(false);
        }
    }
}
