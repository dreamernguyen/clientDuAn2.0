package com.dreamernguyen.ClientDuAn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cloudinary.android.LogLevel;
import com.cloudinary.android.MediaManager;
import com.dreamernguyen.ClientDuAn.Activity.QuetQR;
import com.dreamernguyen.ClientDuAn.Activity.TrangCaNhanActivity;
import com.dreamernguyen.ClientDuAn.Adapter.BottomNavAdapter;
import com.dreamernguyen.ClientDuAn.Fragment.CaNhanFragment;
import com.dreamernguyen.ClientDuAn.Fragment.GianHangFragment;
import com.dreamernguyen.ClientDuAn.Fragment.ThongBaoFragment;
import com.dreamernguyen.ClientDuAn.Fragment.TinNhanFragment;
import com.dreamernguyen.ClientDuAn.Fragment.TrangChuFragment;
import com.dreamernguyen.ClientDuAn.Models.BaiViet;
import com.dreamernguyen.ClientDuAn.Models.DuLieuTraVe;
import com.dreamernguyen.ClientDuAn.Models.MatHang;
import com.dreamernguyen.ClientDuAn.Models.NguoiDung;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private BottomNavAdapter viewPagerAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);


        loadNguoiDung();

        viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(4);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.trangChu :
                        viewPager.setCurrentItem(0,false);
                        TrangChuFragment trangChuFragment = (TrangChuFragment) viewPager.getAdapter().instantiateItem(viewPager,0);
//                        trangChuFragment.reloadData();
                        break;
                    case R.id.gianHang :
                        viewPager.setCurrentItem(1,false);
                        GianHangFragment gianHangFragment = (GianHangFragment) viewPager.getAdapter().instantiateItem(viewPager,1);
//                        trangChuFragment.reloadData();
                        break;
                    case R.id.tinNhan :
                        viewPager.setCurrentItem(2,false);
                        TinNhanFragment tinNhanFragment = (TinNhanFragment) viewPager.getAdapter().instantiateItem(viewPager,2);
//                        tinNhanFragment.reloadData();
                        break;
                    case R.id.thongBao :
                        viewPager.setCurrentItem(3,false);
                        ThongBaoFragment thongBaoFragment = (ThongBaoFragment) viewPager.getAdapter().instantiateItem(viewPager,3);
//                        thongBaoFragment.reloadData();
                        break;
                    case R.id.caNhan :
                        viewPager.setCurrentItem(4,false);
                        CaNhanFragment caNhanFragment = (CaNhanFragment) viewPager.getAdapter().instantiateItem(viewPager,4);
                        break;
                }
                return true;
            }
        });
        viewPagerAdapter = new BottomNavAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.trangChu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.gianHang).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.tinNhan).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.thongBao).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.caNhan).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Intent i = getIntent();
        if(i.getExtras() != null){
            if(i.getStringExtra("chucNang").equals("TinNhanBack")){
                viewPager.setCurrentItem(2);
                bottomNavigationView.getMenu().findItem(R.id.tinNhan).setChecked(true);
            }
        }

    }
    private void loadNguoiDung(){
        Call<DuLieuTraVe> call = ApiService.apiService.xemTrangCaNhan(LocalDataManager.getIdNguoiDung());
        call.enqueue(new Callback<DuLieuTraVe>() {
            @Override
            public void onResponse(Call<DuLieuTraVe> call, Response<DuLieuTraVe> response) {
                NguoiDung nguoiDung = response.body().getNguoiDung();
                LocalDataManager.setNguoiDung(nguoiDung);

            }

            @Override
            public void onFailure(Call<DuLieuTraVe> call, Throwable t) {
                Log.d("LoadNguoiDung", "onFailure: "+t.getMessage());
                Toast.makeText(getApplicationContext(), "Load người dùng lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }


}