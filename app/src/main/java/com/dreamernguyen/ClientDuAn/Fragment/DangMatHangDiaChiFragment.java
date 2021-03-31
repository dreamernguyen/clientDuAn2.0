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

public class DangMatHangDiaChiFragment extends Fragment {
    TextView tvThanhPho, tvQuan, tvPhuongXa, tvBack;
    AppCompatButton btnToiKhac;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_dia_chi, container, false);

        tvBack = view.findViewById(R.id.tvBack);
        tvThanhPho = view.findViewById(R.id.tvThanhPho);
        tvQuan = view.findViewById(R.id.tvQuan);
        tvPhuongXa = view.findViewById(R.id.tvPhuongXa);
        btnToiKhac = view.findViewById(R.id.btnToiKhac);

        layGiaTri();
        hienThiBtn();

        tvBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDiaChiToHinhAnh);
            DangMatHangActivity.viTri = 1;
        });


        tvThanhPho.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionDiaChiToThongTin);

            denThongTin();
            DangMatHangActivity.ThanhPho = "";
            DangMatHangActivity.QuanHuyen = "";
            DangMatHangActivity.PhuongXa = "";
        });
        tvQuan.setOnClickListener(v -> {

        });
        btnToiKhac.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.actionDiaChiToKhac);
            DangMatHangActivity.viTri = 3;

        });
        return view;
    }

    @Override
    public void onResume() {

        layGiaTri();
        hienThiBtn();
        super.onResume();
    }

    public void denThongTin() {
        DangMatHangActivity.viTri = 2;
    }


    public void layGiaTri() {
        tvThanhPho.setText(DangMatHangActivity.ThanhPho);
        tvQuan.setText(DangMatHangActivity.QuanHuyen);
        tvPhuongXa.setText(DangMatHangActivity.PhuongXa);

    }

    public void hienThiBtn() {

        if (DangMatHangActivity.ThanhPho != null && DangMatHangActivity.QuanHuyen != null && !DangMatHangActivity.ThanhPho.isEmpty() && !DangMatHangActivity.QuanHuyen.isEmpty()) {
            btnToiKhac.setVisibility(View.VISIBLE);

        } else {
            btnToiKhac.setVisibility(View.GONE);

        }

    }

}
