package com.dreamernguyen.ClientDuAn.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dreamernguyen.ClientDuAn.Fragment.CaNhanFragment;
import com.dreamernguyen.ClientDuAn.Fragment.GianHangFragment;
import com.dreamernguyen.ClientDuAn.Fragment.ThongBaoFragment;
import com.dreamernguyen.ClientDuAn.Fragment.TinNhanFragment;
import com.dreamernguyen.ClientDuAn.Fragment.TrangChuFragment;

public class BottomNavAdapter extends FragmentStatePagerAdapter {

    public BottomNavAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new TrangChuFragment();
            case 1 : return new GianHangFragment();
            case 2 : return new TinNhanFragment();
            case 3 : return new ThongBaoFragment();
            case 4 : return new CaNhanFragment();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
