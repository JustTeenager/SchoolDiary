package com.example.schooldiary.View;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;

public abstract class MainActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    @LayoutRes
    public abstract int getLayoutRes();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());

        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment==null)
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,getFragment()).commit();
        else getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,getFragment()).commit();
    }
}
