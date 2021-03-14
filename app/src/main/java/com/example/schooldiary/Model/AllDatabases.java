package com.example.schooldiary.Model;


import androidx.room.RoomDatabase;

import com.example.schooldiary.Model.DAOs.DaysDiaryDao;

@androidx.room.Database(version = 1,entities = {TableItem.class})
public abstract class AllDatabases  extends RoomDatabase {
    public abstract DaysDiaryDao getTableItemDao();
}
