package com.example.schooldiary.Model;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.schooldiary.Model.DAOs.TableItemDao;

@androidx.room.Database(version = 1,entities = {TableItem.class})
public abstract class AllDatabases  extends RoomDatabase {
    public abstract TableItemDao getTableItemDao();
}
