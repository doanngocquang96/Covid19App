package com.example.Quang_HomePage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.Quang_HomePage.SucKhoe.CaNhan_Fragment;
import com.example.Quang_HomePage.SucKhoe.NguoiThan_Fragment;
import com.google.gson.Gson;


public class ViewPagerAdapter2 extends FragmentStateAdapter {

    public ViewPagerAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new CaNhan_Fragment();

            case 1: return new NguoiThan_Fragment();

            default: return new CaNhan_Fragment();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
