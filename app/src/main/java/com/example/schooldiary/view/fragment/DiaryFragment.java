package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schooldiary.utils.DateManager;
import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.utils.RecViewAdapter;
import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentDiaryBinding;

public class DiaryFragment extends Fragment {

    public static DiaryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private FragmentDiaryBinding diaryBinding;
    private RecViewAdapter<DayItem> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        diaryBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_diary,container,false);
        setupRecyclerView();

        return diaryBinding.getRoot();
    }

    private void setupRecyclerView(){
        diaryBinding.diaryRecView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter=new RecViewAdapter<>(new DateManager().setupTwoWeeksFromToday(),RecViewAdapter.ViewType.DayHolder);
        diaryBinding.diaryRecView.setAdapter(adapter);
    }
}
