package com.dreamernguyen.ClientDuAn.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.api.exceptions.ApiException;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.MainActivity;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.dreamernguyen.ClientDuAn.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1;
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    GoogleApiClient mApiClient;
    SignInButton signInButton;
    TextView btnDangKy, tvQuenMatKhau;
    TextInputLayout layoutSDT, layoutMatKhau;
    TextInputEditText edSDT, edMatKhau;
    MaterialButton btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        layoutSDT = findViewById(R.id.layoutSDT);
        layoutMatKhau = findViewById(R.id.layoutMatKhau);
        edSDT = findViewById(R.id.edSDT);
        edMatKhau = findViewById(R.id.edMatKhau);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.tvDangKy);
        tvQuenMatKhau = findViewById(R.id.tvQuenmatkhau);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        tvQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(DangNhapActivity.this, XacThucActivity.class);
//                startActivity(i);
                mApiClient.clearDefaultAccountAndReconnect();
//                Toast.makeText(DangNhapActivity.this, "" + edSDT.getText().toString() + "\n" + edMatKhau.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(i);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DangNhap();

            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        edSDT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutSDT.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                layoutMatKhau.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void DangNhap() {
        validateForm();
        if (layoutSDT.getError() == null || layoutMatKhau.getError() == null) {
            String mMatKhau = edMatKhau.getText().toString();
            String mSdt = edSDT.getText().toString();
            NguoiDung nguoiDung = new NguoiDung(mSdt, mMatKhau);
            Call<DuLieuTraVe> call = ApiService.apiService.dangNhap(nguoiDung);
            call.enqueue(new Callback<DuLieuTraVe>() {
                @Override
                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                    String thongBao = response.body().getThongBao();
                    NguoiDung nguoiDung = response.body().getNguoiDung();
                    if (thongBao.equals("Tài khoản không tồn tại !")) {
                        layoutSDT.setError(thongBao);
                    }
                    if (thongBao.equals("Sai mật khẩu !")) {
                        layoutMatKhau.setError(thongBao);
                    } else {
                        Toast.makeText(DangNhapActivity.this, thongBao, Toast.LENGTH_SHORT).show();
                    }
                    if(nguoiDung != null){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        LocalDataManager.setIdNguoiDung(nguoiDung.getId());
                        LocalDataManager.setNguoiDung(nguoiDung);
                        startActivity(intent);
                    }

                }

                @Override
                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                    Toast.makeText(DangNhapActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("dangNhap", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Vui lòng kiểm tra lại thông tin đăng nhập !", Toast.LENGTH_SHORT).show();
        }

    }


    public void validateForm() {
        if (edSDT.getText().toString().trim().isEmpty()) {
            layoutSDT.setError("Số điện thoại không được để trống !");
        }
        if (edMatKhau.getText().toString().trim().isEmpty()) {
            layoutMatKhau.setError("Mật khẩu không được để trống !");
        }
        if (edSDT.getText().toString().length() != 10) {
            layoutSDT.setError("Vui lòng nhập số điện thoại có 10 số !");
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("DangNhapGG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d("DangNhapGG", "Google sign in failed " + e.getMessage().toString());
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("DangNhapGG", "signInWithCredential:success");
                            Log.d("DangNhapGG", "onComplete: " + mAuth.getCurrentUser().getEmail());
                            Call<DuLieuTraVe> call = ApiService.apiService.dangNhapGG(mAuth.getCurrentUser().getEmail(),mAuth.getCurrentUser().getPhotoUrl().toString(),mAuth.getCurrentUser().getDisplayName());
                            call.enqueue(new Callback<DuLieuTraVe>() {
                                @Override
                                public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                                    if (response.body().getThongBao().equals("Email này chưa được liên kết !")) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        LocalDataManager.setIdNguoiDung(response.body().getNguoiDung().getId());
                                        LocalDataManager.setNguoiDung(response.body().getNguoiDung());
                                        startActivity(intent);
                                        Toast.makeText(DangNhapActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        LocalDataManager.setIdNguoiDung(response.body().getNguoiDung().getId());
                                        LocalDataManager.setNguoiDung(response.body().getNguoiDung());
                                        startActivity(intent);
                                        Toast.makeText(DangNhapActivity.this, response.body().getThongBao(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                    Log.d("dangNhapGG", "onFailure: " + t.getMessage());
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("DangNhapGG", "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}