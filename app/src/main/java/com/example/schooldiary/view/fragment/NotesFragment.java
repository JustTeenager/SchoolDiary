package com.example.schooldiary.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentWithNotesBinding;
import com.example.schooldiary.model.NotesTable;
import com.example.schooldiary.utils.DBSingleton;
import com.example.schooldiary.viewmodel.DiaryViewModel;

import java.util.function.LongFunction;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class NotesFragment extends Fragment {

    private static final String POSITION_CODE = "position_code";
    private static final String SUBJECT_NAME_KEY="subject_name_key";
    private static final String DATE_KEY="date_key";
    private FragmentWithNotesBinding binding;
    private String nameSubject;
    private String date;
    private NotesTable notesTable;
    private int position;
    private DiaryViewModel viewModel;


    public static NotesFragment newInstance(int position, String nameSubject, String date) {

        Bundle args = new Bundle();
        args.putInt(POSITION_CODE, position);
        args.putString(SUBJECT_NAME_KEY, nameSubject);
        args.putString(DATE_KEY, date);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_with_notes, container,false);
        position = getArguments().getInt(POSITION_CODE);
        nameSubject = getArguments().getString(SUBJECT_NAME_KEY);
        date = getArguments().getString(DATE_KEY);
        setViewModel();
        return binding.getRoot();
    }

    private void setViewModel() {
        viewModel = new DiaryViewModel();
        if (position == 0){
            viewModel.setTextNote(getActivity().getString(R.string.homework_text)+" "+nameSubject);
            viewModel.setHintEditText(getActivity().getString(R.string.enter_homework));
        }
        else{
            viewModel.setTextNote(getActivity().getString(R.string.note)+" "+nameSubject);
            viewModel.setHintEditText(getActivity().getString(R.string.hint_notes_edit_text));
        }
        viewModel.setDate(date);
        binding.saveNotes.setOnClickListener((v)->{
            if (notesTable == null){
                notesTable = new NotesTable(date, nameSubject);
            }
            if (!binding.notesEditText.getText().toString().isEmpty()) {
                if (position == 0) {
                    notesTable.setHomework(binding.notesEditText.getText().toString());
                } else {
                    notesTable.setNote(binding.notesEditText.getText().toString());
                }
                addNotesTable();
            }
        });
        binding.setViewModel(viewModel);
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotesTable();
    }

    @SuppressLint("CheckResult")
    private void getNotesTable(){
        DBSingleton.getInstance(getActivity()).getNotesDao().getNotesTable(date,nameSubject).subscribeOn(Schedulers.io())
                .subscribe(it->{
                notesTable = it;
                if (position == 0){
                    viewModel.setNote(notesTable.getHomework());
                }
                else if(position == 1){
                    viewModel.setNote(notesTable.getNote());
                }
        });
    }

    private void addNotesTable(){
        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            DBSingleton.getInstance(getActivity()).getNotesDao().addNotesTable(notesTable);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io()).subscribe();
    }
}
