package com.example.schooldiary.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.schooldiary.model.DayItem;
import com.example.schooldiary.utils.daos.NotesDao;
import com.example.schooldiary.utils.daos.SubjectsDao;
import com.example.schooldiary.utils.daos.DaysDiaryDao;
import com.example.schooldiary.utils.daos.TableItemsDao;
import com.example.schooldiary.view.MainActivity;

import java.util.Calendar;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DBSingleton {
    private static AllDatabases databases;
    private static DBSingleton singleton;

    private DBSingleton(Context context){
        databases = Room.databaseBuilder(context,AllDatabases.class,"database").fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Observable.range(0,14).subscribeOn(Schedulers.io()).subscribe(it ->{
                            //DayItem item=new DayItem(new DateManager(context).getTheDaysFormat(it%7),it<7);
                            //getDiaryDao().insertDay(item);
                            DayItem item=new DayItem(new DateManager(context).getTheDaysFormat(it%7),it<7);
                            getInstance(context).getDiaryDao().insertDay(item);
                        });
                    }
                })
                .build();
    }

    public static DBSingleton getInstance(Context context) {
        if (singleton == null){
            singleton = new DBSingleton(context);
        }
        return singleton;
    }

    public DaysDiaryDao getDiaryDao(){
        return databases.getDiaryDao();
    }

    public TableItemsDao getTableItemsDao(){
        return databases.getTableItemsDao();
    }
    public SubjectsDao getSubjectsDao(){
        return databases.getSubjectsDao();
    }

    public NotesDao getNotesDao(){
        return databases.getNotesDao();
    }



}
