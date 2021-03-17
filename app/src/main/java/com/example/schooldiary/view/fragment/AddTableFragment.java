package com.example.schooldiary.view.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentAddTableBinding;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.utils.DBSingleton;
import com.example.schooldiary.utils.DateManager;
import com.example.schooldiary.viewmodel.TableItemViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddTableFragment extends Fragment {


    public static AddTableFragment newInstance() {

        Bundle args = new Bundle();

        AddTableFragment fragment = new AddTableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TableItem value;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TableItemViewModel viewModel= ViewModelProviders.of(getActivity()).get(TableItemViewModel.class);
        value=new TableItem();
        viewModel.getLiveData().observe(this, item -> value=item);
        FragmentAddTableBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_table,container,false);
        binding.timePicker.setIs24HourView(false);
        binding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            Log.d("tut_time_listener","changing");
            value.setTime(DateManager.setStringFromTime(hourOfDay,minute));

        });
        binding.saveButton.setOnClickListener(v -> {
            value.setName(binding.subjectSpinner.getSelectedItem().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                value.setTime(DateManager.setStringFromTime(binding.timePicker.getHour(),binding.timePicker.getMinute()));
            }
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                DBSingleton.getInstance(getContext()).getTableItemsDao().insertTableItem(value);
                emitter.onComplete();
            }).subscribeOn(Schedulers.io()).subscribe();
        });
        DBSingleton.getInstance(getContext()).getSubjectsDao().getSubjects().subscribeOn(Schedulers.io()).map(new Function<List<SubjectItem>, String[]>() {

            @Override
            public String[] apply(@NonNull List<SubjectItem> subjectItems) {
               String[] list=new String[subjectItems.size()];
               for(int i=0;i<list.length;i++) list[i]=subjectItems.get(i).getName();
                return list;
            }
        }).toSingle().observeOn(AndroidSchedulers.mainThread()).subscribe(it->{
            ArrayAdapter<String> adapter  = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, it);
            binding.subjectSpinner.setAdapter(adapter);
        });

        return binding.getRoot();
    }
}

/*flatMap(new Function<List<SubjectItem>, MaybeSource<?>>() {
            @Override
            public MaybeSource<?> apply(@NonNull List<SubjectItem> subjectItems) {
                ArrayList<String> list=new ArrayList<>();
                for (SubjectItem item:subjectItems){
                    list.add(item.getName());
                }
                return new MaybeSource<ArrayList<String>>() {
                    @Override
                    public void subscribe(@io.reactivex.annotations.NonNull MaybeObserver<? super ArrayList<String>> observer) {

                    }
                };
            }
        }).subscribe(it->{
        })*/