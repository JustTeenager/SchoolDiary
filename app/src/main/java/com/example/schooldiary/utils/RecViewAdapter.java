package com.example.schooldiary.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.schooldiary.databinding.ItemSubjectBinding;
import com.example.schooldiary.databinding.ItemTableBinding;
import com.example.schooldiary.model.DayAndTableItems;
import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.R;
import com.example.schooldiary.databinding.ItemDiaryElementBinding;
import com.example.schooldiary.view.Callback;
import com.example.schooldiary.view.fragment.AddTableFragment;
import com.example.schooldiary.view.fragment.EditDiaryFragment;
import com.example.schooldiary.view.fragment.UpdateSubjectFragment;
import com.example.schooldiary.viewmodel.SubjectViewModel;
import com.example.schooldiary.viewmodel.TableItemViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RecViewAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<D> dataList;
    private DayItem dayItems;
    private ViewType type;

    private Callback callback;

    private final ViewBinderHelper helper = new ViewBinderHelper();

    public RecViewAdapter(ViewType type){
        this.type = type;
        dataList = new ArrayList<>();
    }

    private RecViewAdapter(ViewType type,DayItem items){
        dataList = new ArrayList<>();
        this.type=type;
        this.dayItems=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        callback = (Callback) parent.getContext();
        switch (type) {
            case DayHolder:
                return new DayHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_diary_element, parent, false));
            case SubjectHolder:
                return new SubjectHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_subject,parent,false));
            case TableHolder:
                return new TableHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_table,parent,false));
        }
        return new BaseHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_diary_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (type) {
            case DayHolder:
                ((DayHolder) holder).onBind(dataList.get(position));
                break;
            case SubjectHolder:
                ((SubjectHolder) holder).onBind(dataList.get(position));
                helper.bind(((SubjectHolder) holder).getSwipeReveal(), ((SubjectItem)dataList.get(position)).getIdString());
                break;
            case TableHolder:
                ((TableHolder) holder).onBind(dataList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addDataToList(D data){
        dataList.add(data);
    }

    public void clearList(){
        dataList.clear();
    }


    public ArrayList<D> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<D> dataList) {
        this.dataList = dataList;
    }

    public class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }

        public void onBind(D data) {
        }

        protected void onClickLayout(){

        }
    }

    public class DayHolder extends BaseHolder{

        private ItemDiaryElementBinding binding;
        private DayAndTableItems item;
        private RecViewAdapter<TableItem> adapter;

        public DayHolder(ViewDataBinding binding) {
            super(binding);
            this.binding = (ItemDiaryElementBinding) binding;
            this.binding.tableRecView.setLayoutManager(new LinearLayoutManager(binding.getRoot().getContext()));

        }

        @Override
        public void onBind(D data) {
            item = (DayAndTableItems) data;
            binding.dateTitle.setText((item).getDayItem().getDateTitle());
            binding.addTableButton.setOnClickListener(v -> {
                //TODO Добавление нового урока в расписание
                TableItemViewModel viewModel= ViewModelProviders.of((FragmentActivity) binding.getRoot().getContext()).get(TableItemViewModel.class);
                TableItem tableItem=new TableItem();
                tableItem.setWeekEven(item.getDayItem().isEven());
                tableItem.setDayOfWeek(item.getDayItem().getDay());
                viewModel.setLiveData(tableItem);
                callback.replaceFragmentWithBackStack(AddTableFragment.newInstance());
            });
            Log.d("tut_date_adapter ", String.valueOf(dataList.size()));
            adapter= new RecViewAdapter<>(ViewType.TableHolder,item.getDayItem());
            List<TableItem> listForAdapter=(item.getSubjects()==null? new ArrayList<>() : item.getSubjects());
            fillTheSubjectsAdapter(listForAdapter);
        }

        private void fillTheSubjectsAdapter(List<TableItem> list){
            this.binding.tableRecView.setAdapter(adapter);
            Observable.fromArray(list).flatMapIterable(it -> it).subscribe(new DisposableObserver<TableItem>() {
                @Override
                public void onNext(@NonNull TableItem item) {
                    adapter.addDataToList(item);
                    adapter.notifyItemChanged(adapter.getDataList().size()-1);
                }

                @Override
                public void onError (@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }

        @Override
        protected void onClickLayout() {

        }
    }



    public class SubjectHolder extends BaseHolder{
        private SubjectItem subject;
        private ItemSubjectBinding binding;
        public SubjectHolder(ViewDataBinding binding) {
            super(binding);
            this.binding  = (ItemSubjectBinding) binding;
            this.binding.deleteSubject.setOnClickListener((v)-> {
                {
                    int position = dataList.indexOf(subject);
                    Observable.create((ObservableOnSubscribe<String>) emitter -> {
                        DBSingleton.getInstance(SubjectHolder.this.binding.getRoot().getContext()).getSubjectsDao().deleteSubject(subject);
                        emitter.onComplete();
                    }).subscribeOn(Schedulers.io()).subscribe();

                    if (position > -1 && position < dataList.size()) {
                        dataList.remove(position);
                        notifyItemRemoved(position);
                    }
                }
            });
            this.binding.constrainLayout.setOnClickListener((v)-> {
                {
                    onClickLayout();
                }
            });
        }

        @Override
        public void onBind(D data) {
            subject = (SubjectItem) data;
            Log.d("tut_onBind", subject.getName());
            binding.nameSubjectText.setText(subject.getName());
            binding.setNameSubject(subject.getName());
        }

        @Override
        protected void onClickLayout() {
            SubjectViewModel viewModel = ViewModelProviders.of((FragmentActivity) binding.getRoot().getContext()).get(SubjectViewModel.class);
            viewModel.setLiveData(subject);
            callback.replaceFragmentWithBackStack(UpdateSubjectFragment.newInstance());
        }

        public SwipeRevealLayout getSwipeReveal(){
            return  binding.swipeReveal;
        }
    }

    public class TableHolder extends BaseHolder{

        private ItemTableBinding binding;
        private TableItem item;

        public TableHolder(ViewDataBinding binding) {
            super(binding);
            this.binding= (ItemTableBinding) binding;
            this.binding.tableLayout.setOnClickListener(v-> onClickLayout());
        }

        @Override
        public void onBind(D data) {
            super.onBind(data);
            item= (TableItem) data;
            binding.subjectNameText.setText(item.getName());
            binding.timeText.setText(item.getTime());
        }

        @Override
        protected void onClickLayout() {
           callback.replaceFragmentWithBackStack(EditDiaryFragment.newInstance(item.getName(),dayItems.getDbDate()));
        }
    }

    public enum ViewType{
        DayHolder,
        SubjectHolder,
        TableHolder
    }

}