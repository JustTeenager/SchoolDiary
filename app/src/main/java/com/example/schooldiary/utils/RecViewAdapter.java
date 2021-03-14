package com.example.schooldiary.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.databinding.ItemSubjectBinding;
import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.R;
import com.example.schooldiary.databinding.ItemDiaryElementBinding;

import java.util.ArrayList;

public class RecViewAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ViewType{
        DayHolder,
        SubjectHolder
    }

    public void setDataList(ArrayList<D> dataList) {
        this.dataList = dataList;
    }

    private ArrayList<D> dataList;

    private ViewType type;

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


    public ArrayList<D> getDataList() {
        return dataList;
    }

    public class BaseHolder extends RecyclerView.ViewHolder{
        protected ViewDataBinding binding;
        protected D item;

        public BaseHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void onBind(D data) {
        }
    }

    public class DayHolder extends BaseHolder{

        private ArrayList<TableItem> tableItems;

        public DayHolder(ViewDataBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(D data) {
            Log.d("tutBind","DayHolderBinding");
            item = data;
            ((ItemDiaryElementBinding) binding).dateTitle.setText(((DayItem)item).getDay());
        }
    }

    public class SubjectHolder extends BaseHolder{
        public SubjectHolder(ViewDataBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(D data) {
            SubjectItem subject = (SubjectItem) data;
            ((ItemSubjectBinding) binding).setNameSubject(subject.getName());
        }
    }

}