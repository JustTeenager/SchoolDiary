package com.example.schooldiary.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DiaryFragment extends Fragment {


    private FragmentDiaryBinding diaryBinding;
    private RecViewAdapter<DayItem> adapter;
    private DateManager dateManager=new DateManager();
    private boolean isCalendarOpened=false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        diaryBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_diary,container,false);
        setupRecyclerView();

        diaryBinding.datePicker.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar calendar=new GregorianCalendar(year,month,dayOfMonth);
            adapter.setDataList(dateManager.setupTwoWeeksFromCurrentCalendar(calendar));
            adapter.notifyDataSetChanged();
        });

        return diaryBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.diary_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.calendar:{
                if (!isCalendarOpened) {
                    isCalendarOpened=true;
                    diaryBinding.motionContainer.transitionToEnd();
                }
                else{
                    isCalendarOpened=false;
                    diaryBinding.motionContainer.transitionToStart();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static DiaryFragment newInstance() {

        Bundle args = new Bundle();
        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void setupRecyclerView(){
        diaryBinding.diaryRecView.setLayoutManager(new LinearLayoutManager(getContext()));
       adapter=new RecViewAdapter<>(dateManager.setupTwoWeeksFromToday(),RecViewAdapter.ViewType.DayHolder);
        diaryBinding.diaryRecView.setAdapter(adapter);
    }
}
