package com.example.schooldiary.utils;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.model.SubjectItem;
import com.example.schooldiary.utils.daos.SubjectsDao;
import com.example.schooldiary.utils.daos.DaysDiaryDao;
import com.example.schooldiary.model.TableItem;

@Database(version = 1,entities = {TableItem.class, DayItem.class, SubjectItem.class})
public abstract class AllDatabases  extends RoomDatabase {
    public abstract SubjectsDao getSubjectsDao();
    public abstract DaysDiaryDao getTableItemDao();
}
