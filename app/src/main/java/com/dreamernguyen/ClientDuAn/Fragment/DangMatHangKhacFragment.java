package com.dreamernguyen.ClientDuAn.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dreamernguyen.ClientDuAn.Activity.DangMatHangActivity;
import com.dreamernguyen.ClientDuAn.R;

public class DangMatHangKhacFragment extends Fragment {

    TextView tvBack;
    EditText edGia, edTieuDe, edNoiDung;
    Button btn, btn1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dang_mat_hang_khac, container, false);

        tvBack = view.findViewById(R.id.tvBack);

        edGia = view.findViewById(R.id.edGia);
        edTieuDe = view.findViewById(R.id.edTieuDe);
        edNoiDung = view.findViewById(R.id.edNoiDung);

        btn1 = view.findViewById(R.id.btn1);

        layThongTin();
        hienThiBtn();

        edGia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                hienThiBtn();
                DangMatHangActivity.giaBan = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edTieuDe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DangMatHangActivity.tieuDe = edTieuDe.getText().toString();

                hienThiBtn();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edNoiDung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hienThiBtn();
                DangMatHangActivity.noiDung = edNoiDung.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tvBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.actionKhacToDiaChi);
            DangMatHangActivity.viTri = 2;
        });


        btn1.setOnClickListener(v -> {

            Navigation.findNavController(v).navigate(R.id.actionKhacToDangMatHangFragment);
            DangMatHangActivity.viTri = 4;
        });
        return view;
    }

    @Override
    public void onResume() {
        layThongTin();
        hienThiBtn();
        super.onResume();
    }

    public void hienThiBtn() {
        if (DangMatHangActivity.giaBan != 0 && DangMatHangActivity.tieuDe != null && !DangMatHangActivity.tieuDe.isEmpty()) {
            btn1.setVisibility(View.VISIBLE);
        } else {
            btn1.setVisibility(View.GONE);
        }
    }

    public void layThongTin() {
        edTieuDe.setText(DangMatHangActivity.tieuDe);
        edNoiDung.setText(DangMatHangActivity.noiDung);
        edGia.setText(DangMatHangActivity.giaBan + "");
    }
}
