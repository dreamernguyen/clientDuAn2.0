package com.dreamernguyen.ClientDuAn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dreamernguyen.ClientDuAn.Adapter.AnhBaiVietAdapter;

public class AnhChiTietActivity extends AppCompatActivity {
    ViewPager vp;
    AnhBaiVietAdapter anhBaiVietAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anh_chi_tiet);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
        vp = findViewById(R.id.vpgImage);
        Intent i = getIntent();
        anhBaiVietAdapter = new AnhBaiVietAdapter(i.getStringArrayListExtra("listAnh"),true);

        vp.setAdapter(anhBaiVietAdapter);
        vp.setCurrentItem(i.getIntExtra("pos",0));
    }
}