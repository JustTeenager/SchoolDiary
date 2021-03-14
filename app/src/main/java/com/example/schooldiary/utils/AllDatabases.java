package com.example.schooldiary.utils;


import androidx.room.RoomDatabase;

import com.example.schooldiary.utils.daos.SubjectsDao;
import com.example.schooldiary.utils.daos.DaysDiaryDao;
import com.example.schooldiary.model.TableItem;

@androidx.room.Database(version = 1,entities = {TableItem.class})
public abstract class AllDatabases  extends RoomDatabase {
    public abstract SubjectsDao getSubjectsDao();
    public abstract DaysDiaryDao getTableItemDao();
}
