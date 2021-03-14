package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentAddSubjectBinding;

public class AddSubjectFragment  extends Fragment {
    private FragmentAddSubjectBinding binding;

    public static AddSubjectFragment newInstance() {
        Bundle args = new Bundle();
        AddSubjectFragment fragment = new AddSubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_subject,container,false);
        checkingCheckBoxes();
        binding.addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nameEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),R.string.enter_lesson, Toast.LENGTH_SHORT).show();
                }
                else if (binding.cabEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),R.string.enter_cab, Toast.LENGTH_SHORT).show();
                }
                else if (!binding.anotherCheck.isChecked() && !binding.literatureCheck.isChecked() && !binding.scienceCheck.isChecked()){
                    Toast.makeText(getActivity(),R.string.check_subject, Toast.LENGTH_SHORT).show();
                }
                else{
                  //  SubjectItem subject = new SubjectItem()
                   // DBSingleton.getInstance(getActivity()).getSubjectsDao().addSubject();
                }
            }
        });
        return binding.getRoot();
    }

    private void checkingCheckBoxes() {
        binding.anotherCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setCheckedFalse(binding.scienceCheck,binding.literatureCheck);
                }
            }
        });
        binding.scienceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setCheckedFalse(binding.anotherCheck,binding.literatureCheck);
                }
            }
        });
        binding.literatureCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    setCheckedFalse(binding.scienceCheck, binding.anotherCheck);
                }
            }
        });
    }

    private void setCheckedFalse(CheckBox firstCheck, CheckBox secondCheck){
        firstCheck.setChecked(false);
        secondCheck.setChecked(false);
    }
}
