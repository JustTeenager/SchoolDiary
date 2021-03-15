package com.example.schooldiary.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.schooldiary.databinding.ItemSubjectBinding;
import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.R;
import com.example.schooldiary.databinding.ItemDiaryElementBinding;
import com.example.schooldiary.view.Callback;
import com.example.schooldiary.view.fragment.UpdateSubjectFragment;
import com.example.schooldiary.viewmodel.SubjectViewModel;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class RecViewAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<D> dataList;
    private ViewType type;
    private Callback callback;
    private final ViewBinderHelper helper = new ViewBinderHelper();

    public RecViewAdapter(ViewType type){
        this.type = type;
        dataList = new ArrayList<>();
    }

    public RecViewAdapter(ArrayList<D> dataList, ViewType type){
        this.dataList = dataList;
        this.type=type;
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
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addDataToList(D data){
        dataList.add(data);
        Log.d("tut", data.toString());
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
        protected D item;

        public BaseHolder(ViewDataBinding binding) {
            super(binding.getRoot());
        }

        public void onBind(D data) {
        }

        protected void onClickLayout(){

        }
    }

    public class DayHolder extends BaseHolder{

        private ArrayList<TableItem> tableItems;
        private ItemDiaryElementBinding binding;

        public DayHolder(ViewDataBinding binding) {
            super(binding);
            this.binding = (ItemDiaryElementBinding) binding;
        }

        @Override
        public void onBind(D data) {
            item = data;
            binding.dateTitle.setText(((DayItem)item).getDay());
        }
    }



    public class SubjectHolder extends BaseHolder{
        private SubjectItem subject;
        private ItemSubjectBinding binding;
        public SubjectHolder(ViewDataBinding binding) {
            super(binding);
            this.binding  = (ItemSubjectBinding) binding;
            this.binding.deleteSubject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
            this.binding.constrainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickLayout();
                }
            });
        }

        @Override
        public void onBind(D data) {
            subject = (SubjectItem) data;
            Log.d("tut_onBind", subject.getName());
            binding.nameSubject.setText(subject.getName());
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

    public enum ViewType{
        DayHolder,
        SubjectHolder
    }

}