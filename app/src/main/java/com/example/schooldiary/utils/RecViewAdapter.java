package com.example.schooldiary.utils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.model.TableItem;
import com.example.schooldiary.R;
import com.example.schooldiary.databinding.ItemDiaryElementBinding;

import java.util.ArrayList;

public class RecViewAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum ViewType{
        DayHolder,
        SubjectHolder
    }

    private ArrayList<D> data;
    private ViewType type;
    public RecViewAdapter(ArrayList<D> data,ViewType type){
        this.data = data;
        this.type=type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (type) {
            case DayHolder:
                return new DayHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_diary_element, parent, false));
            case SubjectHolder:
                break;
        }
        return new BaseHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_diary_element,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (type) {
            case DayHolder:
                ((DayHolder) holder).onBind(data.get(position));
            case SubjectHolder:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BaseHolder extends RecyclerView.ViewHolder{
        protected ViewDataBinding binding;

        public BaseHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        protected void onBind(D data) {

        }
    }

    public class DayHolder extends BaseHolder{

        private D item;
        private ArrayList<TableItem> tableItems;

        public DayHolder(ViewDataBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(D data) {
            Log.d("tutBind","DayHolderBinding");
            item=data;
            ((ItemDiaryElementBinding) binding).dateTitle.setText(((DayItem)item).getDay());
        }
    }

}