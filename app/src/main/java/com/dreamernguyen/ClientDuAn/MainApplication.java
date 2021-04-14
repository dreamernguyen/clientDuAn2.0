package com.dreamernguyen.ClientDuAn;

import android.Manifest;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.cloudinary.android.MediaManager;
import com.dreamernguyen.ClientDuAn.Activity.NhanTinActivity;
import com.dreamernguyen.ClientDuAn.Models.TinNhan;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainApplication extends Application {
    public static final String HOST = "http://server-du-an.herokuapp.com/";

    private String TAG = "Main Application";
    public static final String CHANNEL_ID = "Chạy ngầm";
    public static final String CHANNEL_ID2 = "Tin nhắn";
    public static final String CHANNEL_ID3 = "Thông báo";


    @Override
    public void onCreate() {
        super.onCreate();
        MediaManager.init(this);
        LocalDataManager.init(getApplicationContext());
        createNotificationChannel();
        capQuyen();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(new Intent(this, MyService.class));
        } else {
            startService(new Intent(this, MyService.class));
        }


    }


    private void capQuyen() {
        TedPermission.with(this).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(MainApplication.this, "Ok", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainApplication.this, "Not Ok", Toast.LENGTH_SHORT).show();
            }
        }).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .setDeniedTitle("Cài đặt cho phép truy cập").setDeniedMessage("Vui lòng bật quyền truy cập tại [Cài đặt] > [Quyền]").check();
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Chạy ngầm
            CharSequence name = "Thông báo chạy ngầm";
            String description = "Ứng dụng đang chạy ngầm";
            int importance = NotificationManager.IMPORTANCE_NONE;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            //Tin nhắn
            CharSequence name2 = "Thông báo tin nhắn mới";
            String description2 = "Thông báo tin nhắn đến";
            int importance2 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_ID2, name2, importance2);
            channel2.setDescription(description2);
            //Thông báo
            CharSequence name3 = "Thông báo các hoạt động";
            String description3 = "Thông báo các hoạt động mới";
            int importance3 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel3 = new NotificationChannel(CHANNEL_ID3, name3, importance3);
            channel3.setDescription(description3);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                notificationManager.createNotificationChannel(channel2);
                notificationManager.createNotificationChannel(channel3);
            }

        }
    }

}
