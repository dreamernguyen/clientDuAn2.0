package com.dreamernguyen.ClientDuAn.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dreamernguyen.ClientDuAn.Activity.BaiVietYeuThichActivity;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.Adapter.BaiVietAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        return view;
    }

}