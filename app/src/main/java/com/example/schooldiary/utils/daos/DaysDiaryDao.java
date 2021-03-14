package com.example.schooldiary.utils.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.schooldiary.model.DayItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface DaysDiaryDao {

    @Query("SELECT * FROM diary")
    Flowable<List<DayItem>> getDiary();
}
