package com.example.schooldiary.Model.DAOs;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.schooldiary.Model.DayItem;

import java.util.ArrayList;

import io.reactivex.Flowable;

@Dao
public interface DaysDiaryDao {

    @Query("SELECT * FROM diary")
    Flowable<ArrayList<DayItem>> getDiary();
}
