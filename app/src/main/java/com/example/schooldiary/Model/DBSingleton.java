package com.example.schooldiary.Model;

import android.content.Context;

import androidx.room.Room;

import com.example.schooldiary.Model.DAOs.DaysDiaryDao;

public class DBSingleton {
    private AllDatabases databases;
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

}
