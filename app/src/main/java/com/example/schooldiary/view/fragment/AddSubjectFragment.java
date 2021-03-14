package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.util.Log;
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
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.Subjects;
import com.example.schooldiary.utils.DBSingleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class AddSubjectFragment  extends Fragment {
    private FragmentAddSubjectBinding binding;
    private Subjects type;

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
                else if(binding.teacherEditText.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),R.string.enter_teacher, Toast.LENGTH_SHORT).show();
                }
                else if (!binding.anotherCheck.isChecked() && !binding.literatureCheck.isChecked() && !binding.scienceCheck.isChecked()){
                    Toast.makeText(getActivity(),R.string.check_subject, Toast.LENGTH_SHORT).show();
                }
                else{

                    Observable.create(new ObservableOnSubscribe<String>() {

                        @Override
                        public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                            //SubjectItem subject = new SubjectItem(binding.nameEditText.getText().toString(),binding.cabEditText.getText().toString(),binding.teacherEditText.getText().toString(), type);
                            //DBSingleton.getInstance(getActivity()).getSubjectsDao().addSubject(subject);
                            emitter.onComplete();
                        }
                    }).subscribeOn(Schedulers.io()).subscribe();

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
                    type = Subjects.Another;
                    Log.d("tut", type.name());
                    setCheckedFalse(binding.scienceCheck,binding.literatureCheck);
                }
            }
        });
        binding.scienceCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    type = Subjects.Science;
                    Log.d("tut", type.name());
                    setCheckedFalse(binding.anotherCheck,binding.literatureCheck);
                }
            }
        });
        binding.literatureCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    type = Subjects.Literature;
                    Log.d("tut", type.name());
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
