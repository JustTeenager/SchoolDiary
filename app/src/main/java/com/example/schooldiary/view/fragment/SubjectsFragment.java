package com.example.schooldiary.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentSubjectsBinding;

public class SubjectsFragment extends Fragment {
    private FragmentSubjectsBinding binding;
    private Callback callback;

    public static SubjectsFragment newInstance() {
        Bundle args = new Bundle();
        SubjectsFragment fragment = new SubjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subjects,container,false);
        getActivity().setTitle(R.string.subjects);
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_subject_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.plus_subject:{
                addSubject();
            }break;
        }
        return true;
    }

    private void addSubject() {
        callback.addFragment(AddSubjectFragment.newInstance());
    }

    public interface Callback{
        void addFragment(Fragment fragment);
    }
}
