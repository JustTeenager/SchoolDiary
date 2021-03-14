package com.example.schooldiary.Model;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecViewAdapter<T,VH extends RecViewAdapter.BaseHolder> extends RecyclerView.Adapter<VH> {


    private ArrayList<T> data;
    public RecViewAdapter(ArrayList<T> data){
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class BaseHolder extends RecyclerView.ViewHolder{
        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
