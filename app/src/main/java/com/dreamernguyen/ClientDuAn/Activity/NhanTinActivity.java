package com.dreamernguyen.ClientDuAn.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.dreamernguyen.ClientDuAn.Adapter.TinNhanAdapter;
import com.dreamernguyen.ClientDuAn.ApiService;
import com.dreamernguyen.ClientDuAn.LocalDataManager;
import com.dreamernguyen.ClientDuAn.MainActivity;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.TinNhan;
import com.dreamernguyen.ClientDuAn.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanTinActivity extends AppCompatActivity {
    private EditText edTinNhan;
    private TextView tvTenNguoiDung;
    private Button btnGui;
    private ImageView btnUpload,imgBack;
    private static RecyclerView rvChat;
    private static TinNhanAdapter tinNhanAdapter;
    public static List<TinNhan> listTinNhan = new ArrayList<>();
    public String idLienHe,idNguoiDung,tenNguoiDung;
    public String intentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_tin);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.SkyBlue));
        }
        Intent i = getIntent();
        if(i.getExtras() != null){
            intentActivity = i.getStringExtra("activity");
            idNguoiDung = i.getStringExtra("idNguoiDung");
            tenNguoiDung = i.getStringExtra("tenNguoiDung");
        }


        tvTenNguoiDung = findViewById(R.id.tvTenNguoiDung);
        tvTenNguoiDung.setText(tenNguoiDung);
        edTinNhan = findViewById(R.id.edTinNhan);
        btnGui = findViewById(R.id.btnGui);
        imgBack = findViewById(R.id.imgBack);
        btnUpload = findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guiAnh();
            }
        });
        rvChat = findViewById(R.id.rvChat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvChat.setLayoutManager(linearLayoutManager);

        tinNhanAdapter = new TinNhanAdapter(getApplicationContext());
        rvChat.setAdapter(tinNhanAdapter);
        loadTinNhan(idNguoiDung);
        edTinNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkKeyboard();
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edTinNhan.getText().toString().trim().length() > 0){
                    guiTinNhan(idNguoiDung,edTinNhan.getText().toString());
                }else {
                    Toast.makeText(NhanTinActivity.this, "Không để trống tin nhắn !", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void checkKeyboard(){
        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                activityRootView.getWindowVisibleDisplayFrame(rect);

                int heightDiff = activityRootView.getRootView().getHeight() - rect.height();
                if ((heightDiff > 0.25 *activityRootView.getRootView().getHeight())){
                    if(listTinNhan.size() > 0){
                        rvChat.scrollToPosition(listTinNhan.size() - 1);
                        activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                }
            }
        });
    }

    public static void loadTinNhan(String idLienHe){
        Call<List<TinNhan>> call = ApiService.apiService.danhSachTinNhan(idLienHe, LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<List<TinNhan>>() {
            @Override
            public void onResponse(Call<List<TinNhan>> call, Response<List<TinNhan>> response) {
                listTinNhan = response.body();
                tinNhanAdapter.setData(listTinNhan);
                if(listTinNhan.size() > 0){
                    rvChat.scrollToPosition(listTinNhan.size() - 1);
                }
                Log.d("loadTinNhan", "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<List<TinNhan>> call, Throwable t) {
                Log.d("oo", "onFailure: "+t.getMessage());
            }
        });
    }

    private void guiTinNhan(String idLienHe,String noiDung){
        edTinNhan.setText("");
        String idNguoiDung = LocalDataManager.getIdNguoiDung();
        Call<DuLieuTraVe> call = ApiService.apiService.chat(idNguoiDung,idLienHe,noiDung,"");
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                loadTinNhan(idLienHe);
                Log.d("ggg", "onResponse: "+response.body().getThongBao());
            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("ddg", "Loi: "+t.getMessage());
            }
        });
    }

    private void guiAnh(){
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Xác nhận")
                .setEmptySelectionText("Không ảnh nào được chọn")
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        MediaManager.get().upload(uri)
                                .unsigned("gybczcnv").callback(new UploadCallback() {
                            @Override
                            public void onStart(String requestId) {

                            }

                            @Override
                            public void onProgress(String requestId, long bytes, long totalBytes) {

                            }

                            @Override
                            public void onSuccess(String requestId, Map resultData) {
                                Log.d("trave", "onSuccess: " + resultData.get("url"));
                                String linkAnh = resultData.get("url").toString();
                                Call<DuLieuTraVe> call = ApiService.apiService.chat(LocalDataManager.getIdNguoiDung(), idLienHe, "", linkAnh);
                                call.enqueue(new Callback<DuLieuTraVe>() {
                                    @Override
                                    public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
//                                        TinNhan tinNhan1 = response.body();
//                                        Log.d("abc", "onResponse: " + tinNhan1.getNoiDung());
//                                        listTinNhan.add(tinNhan1);
//                                        messageAdapter.notifyDataSetChanged();
//                                        rvChat.scrollToPosition(listTinNhan.size()-1);
//                                        edMessage.setText("");
                                        loadTinNhan(idLienHe);
                                    }

                                    @Override
                                    public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                                        Log.d("loi", "onFailure: " + t.getMessage());
                                    }
                                });
//                                listTinNhan.add(new TinNhan("","","",uri.toString()));
//                                messageAdapter.notifyDataSetChanged();
//                                rvChat.scrollToPosition(listTinNhan.size()-1);

                            }

                            @Override
                            public void onError(String requestId, ErrorInfo error) {

                            }

                            @Override
                            public void onReschedule(String requestId, ErrorInfo error) {

                            }
                        })
                                .dispatch();
                    }

                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(intentActivity.equals("ThongBao")){
            Intent intent = new Intent(NhanTinActivity.this, MainActivity.class);
            intent.putExtra("chucNang","TinNhanBack");
            startActivity(intent);
            finish();
        }
        else {
            finish();
        }

    }
}
