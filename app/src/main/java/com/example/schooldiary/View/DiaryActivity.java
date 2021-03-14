package com.example.schooldiary.View;

import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;

public class DiaryActivity extends MainActivity {
    @Override
    public Fragment getFragment() {
        return new DiaryFragment();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }
}
