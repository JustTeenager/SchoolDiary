package com.example.schooldiary.Model;


import androidx.room.Database;

import com.example.schooldiary.Model.DAOs.TableItemDao;

@Database(version = 1,entities = {TableItem.class})
public abstract class DBSingleton {
    abstract TableItemDao getTableItemDao();
}
