package com.example.schooldiary.view.fragment;

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
import com.example.schooldiary.viewmodel.DiaryViewModel;

public class NotesFragment extends Fragment {

    private static final String POSITION_CODE = "position_code";
    private FragmentWithNotesBinding binding;
    public static NotesFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(POSITION_CODE, position);
        NotesFragment fragment = new NotesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_with_notes, container,false);
        int position = getArguments().getInt(POSITION_CODE);
        DiaryViewModel viewModel = new DiaryViewModel();
        if (position == 0){
            viewModel.setTextNote(getActivity().getString(R.string.homework_text));
            viewModel.setHintEditText(getActivity().getString(R.string.enter_homework));
        }
        else{
            viewModel.setTextNote(getActivity().getString(R.string.note));
            viewModel.setHintEditText(getActivity().getString(R.string.hint_notes_edit_text));
        }
        //TODO: поставить дату сюда нормальную
        viewModel.setDate("Наша всратая дата");
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

}
