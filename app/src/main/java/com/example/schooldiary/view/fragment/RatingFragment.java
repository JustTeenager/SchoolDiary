package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentRatingBinding;

public class RatingFragment extends Fragment {
    private static final String SUBJECT_NAME_KEY="subject_name_key";
    private static final String DATE_KEY="date_key";

    private FragmentRatingBinding binding;
    private String nameSubject;
    private String date;

    public static RatingFragment newInstance(String nameSubject, String date) {
        Bundle args = new Bundle();
        args.putString(SUBJECT_NAME_KEY,nameSubject);
        args.putString(DATE_KEY, date);
        RatingFragment fragment = new RatingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating,container,false);
        nameSubject = getArguments().getString(SUBJECT_NAME_KEY);
        date = getArguments().getString(DATE_KEY);
        return binding.getRoot();
    }
}
