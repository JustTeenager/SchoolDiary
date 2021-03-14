package com.example.schooldiary.utils;

import android.content.Context;

import androidx.room.Room;

import com.example.schooldiary.utils.daos.SubjectsDao;
import com.example.schooldiary.utils.daos.DaysDiaryDao;

public class DBSingleton {
    private static AllDatabases databases;
    private static DBSingleton singleton;

    private DBSingleton(Context context){
        databases = Room.databaseBuilder(context,AllDatabases.class,"database").build();
    }

    public static DBSingleton getInstance(Context context) {
        if (singleton == null){
            singleton = new DBSingleton(context);
        }
        return singleton;
    }

    public DaysDiaryDao getTableDao(){
        return databases.getTableItemDao();
    }

    public SubjectsDao getSubjectsDao(){
        return databases.getSubjectsDao();
    }

}
