package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {

   public EditText edtHoten, edtSdt, edtMatKhau, edtNhapLaimatKhau;
    TextView btnDangKy, btnDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        // ánh xạ
        edtHoten = findViewById(R.id.edtTen);
        edtSdt = findViewById(R.id.edtSdt);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtNhapLaimatKhau = findViewById(R.id.edtNhapLaiMatKhau);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangNhap = findViewById(R.id.tvDangNhap);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckForm() == true){
//                    DangKy();
                    String mHoTen, mMatKhau;
                    mHoTen = edtHoten.getText().toString().trim();
                    mMatKhau = edtMatKhau.getText().toString().trim();
                    Intent i = new Intent(getApplicationContext(),XacThucActivity.class);
                    i.putExtra("SDT",edtSdt.getText().toString());
                    i.putExtra("hoTen",mHoTen);
                    i.putExtra("matKhau",mMatKhau);
                    startActivity(i);
                }
            }
        });
    }

    public Boolean CheckForm(){
        String mHoten = edtHoten.getText().toString().trim();
        String mSdt = edtSdt.getText().toString().trim();
        String mMatKhau = edtMatKhau.getText().toString().trim();
        String mNhapLaiMatKhau = edtNhapLaimatKhau.getText().toString().trim();
        //check họ tên
        if (mHoten.length() == 0){
            edtHoten.setError("!");
            edtHoten.setHint("Yêu cầu nhập họ và tên !");
            edtHoten.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtHoten.setPadding(25,0,25,0);
            return false;
        } if (mHoten.length() < 4){
            edtHoten.setError("Yêu cầu nhập đúng họ tên !");
            edtHoten.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtHoten.setPadding(25,0,25,0);
            return false;
        }
        //check sdt
        if (mSdt.length() == 0){
            edtSdt.setError("!");
            edtSdt.setHint("Yêu cầu nhập số điện thoại !");
            edtSdt.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtSdt.setPadding(25,0,25,0);
            return false;
        }if (mSdt.length() < 10){
            edtSdt.setError("Yêu cầu nhập lại số điện thoại !");
            edtSdt.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtSdt.setPadding(25,0,25,0);
            return false;
        }
        // check mật khẩu
        if (mMatKhau.length() < 0){
            edtMatKhau.setHint("Yêu cầu nhập mật khẩu !");
            edtMatKhau.setError("!");
            edtMatKhau.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtMatKhau.setPadding(25,0,25,0);
            return false;
        } if (mMatKhau.length() <= 8){
            edtMatKhau.setError("Mật khẩu phải nhiều hơn 8 ký tự !");
            edtMatKhau.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtMatKhau.setPadding(25,0,25,0);
            return false;
        } if (mMatKhau.equals(mNhapLaiMatKhau) == false){
            edtNhapLaimatKhau.setError("Xát nhận mật khẩu sai !");
            edtNhapLaimatKhau.setHint("Xát nhận mật khẩu sai !");
            edtNhapLaimatKhau.setBackground(getDrawable(R.drawable.edit_text_fail));
            edtNhapLaimatKhau.setPadding(25,0,25,0);
            return false;
        } else {
            return true;
        }
    }

}