package com.example.schooldiary.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.view.fragment.AboutCompanyFragment;
import com.example.schooldiary.view.fragment.DiaryFragment;
import com.example.schooldiary.view.fragment.SubjectsFragment;
import com.example.schooldiary.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SubjectsFragment.Callback {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupNavigationMenu();
    }

    private void setupNavigationMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_subjects:{
                       replaceFragment(SubjectsFragment.newInstance());
                    }
                    break;
                    case R.id.page_about_company:{
                        replaceFragment(AboutCompanyFragment.newInstance());
                    }
                    break;
                    case R.id.page_diary:{
                        replaceFragment(DiaryFragment.newInstance());
                    }
                    break;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    @Override
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack(null).commit();
    }
}
