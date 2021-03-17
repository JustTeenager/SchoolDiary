package com.example.schooldiary.view;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.utils.DBSingleton;
import com.example.schooldiary.utils.DateManager;
import com.example.schooldiary.view.fragment.AboutCompanyFragment;
import com.example.schooldiary.view.fragment.BottomNavigationFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements Callback{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        Observable.range(15,28).subscribeOn(Schedulers.single()).subscribe(new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                DBSingleton.getInstance(MainActivity.this).getDiaryDao().getDiary();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (fragment==null)
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,BottomNavigationFragment.newInstance()).commit();
                else getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,BottomNavigationFragment.newInstance()).commit();
            }
        });
    }
    @Override
    public void replaceFragmentWithBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                //.setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left, R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                .replace(R.id.fragment_container,fragment)
                .addToBackStack(null)
                .commit();
    }


    /*private void setupNavigationMenu() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_subjects:{
                        setTitle(R.string.subjects_page_title);
                       replaceFragment(SubjectsFragment.newInstance());
                    }
                    break;
                    case R.id.page_about_company:{
                        setTitle(R.string.about_company_page_title);
                        replaceFragment(AboutCompanyFragment.newInstance());
                    }
                    break;
                    case R.id.page_diary:{
                        setTitle(R.string.diary_page_title);
                        replaceFragment(DiaryFragment.newInstance());
                    }
                    break;
                }
                return true;
            }
        });
        binding.bottomNavigation.setSelectedItemId(R.id.page_diary);
        replaceFragment(DiaryFragment.newInstance());
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
    }

    @Override
    public void replaceFragmentWithBackStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left, R.anim.enter_left_to_right,R.anim.exit_left_to_right).replace(R.id.container,fragment).addToBackStack(null).commit();
    }*/
}

