package com.example.schooldiary.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.schooldiary.R;
import com.example.schooldiary.databinding.FragmentAddTableBinding;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.utils.DBSingleton;
import com.example.schooldiary.utils.DateManager;
import com.example.schooldiary.viewmodel.TableItemViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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

    private FragmentAddTableBinding binding;
    private DateManager dateManager;

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
       // value=new TableItem();
        dateManager=new DateManager(getContext());
        viewModel.getLiveData().observe(this, item -> value=item);
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_add_table,container,false);
        binding.timePicker.setIs24HourView(false);
        binding.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            value.setTime(dateManager.setStringFromTime(hourOfDay,minute));
        });
        binding.saveButton.setOnClickListener(v -> {
            value.setName(binding.subjectSpinner.getSelectedItem().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                value.setTime(dateManager.setStringFromTime(binding.timePicker.getHour(),binding.timePicker.getMinute()));
            }
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                DBSingleton.getInstance(getContext()).getTableItemsDao().insertTableItem(value);
                emitter.onComplete();
            }).subscribeOn(Schedulers.io()).subscribe();
            Toast.makeText(getActivity(),getActivity().getString(R.string.save),Toast.LENGTH_SHORT).show();
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
            if (value.getName()!=null){
                binding.subjectSpinner.setSelection(Arrays.asList(it).indexOf(value.getName()));
                String[] timeArray = value.getTime().split(":");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    binding.timePicker.setHour(Integer.parseInt(timeArray[0]));
                    binding.timePicker.setMinute(Integer.parseInt(timeArray[1]));
                }
            }
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