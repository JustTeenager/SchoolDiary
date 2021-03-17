package com.example.schooldiary.utils;

import android.content.Context;
import android.util.Log;
import com.example.schooldiary.model.DayAndTableItems;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class DateManager {
    private Calendar calendar;
    private final DateFormat dateFormat;
   private final DateFormat dbDateFormat;
    private Context context;

    public DateManager(Context context){
        dateFormat= new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault());
        dbDateFormat= new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        this.context=context;
        calendar=Calendar.getInstance();
    }

    public Flowable<DayAndTableItems> setupTwoWeeksFromCurrentCalendar(Calendar calendar){
        this.calendar=calendar;
        return setupTwoWeeksFromToday();
    }

    public Flowable<DayAndTableItems> setupTwoWeeksFromToday(){
        Log.d("tut","twoWeeksSetuping");
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,0);
            }break;
            case Calendar.TUESDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-1);
            }break;
            case Calendar.WEDNESDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-2);
            }break;
            case Calendar.THURSDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-3);
            }break;
            case Calendar.FRIDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-4);
            }break;
            case Calendar.SATURDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-5);
            }break;
            case Calendar.SUNDAY:{
                calendar.add(Calendar.DAY_OF_WEEK,-6);
            }break;
        }
        return DBSingleton.getInstance(context).getTableItemsDao().getDayTableItems().subscribeOn(Schedulers.io()).flatMapIterable(it -> {
            for (DayAndTableItems item:it) {
                loadTheWeeks(item);
            }
            calendar=Calendar.getInstance();
            return it;
        });
    }

    public int getTheDaysFormat(int range){
        calendar.add(Calendar.DAY_OF_WEEK,range);
        int num= calendar.get(Calendar.DAY_OF_WEEK);
        calendar=Calendar.getInstance();
        return num;
    }

    public static String setStringFromTime(int hour,int minute){
        return hour +":"+ minute;
    }

    private void loadTheWeeks( DayAndTableItems item) {
        String formattedDate=dateFormat.format(calendar.getTime());
        String formattedDbDate=dbDateFormat.format(calendar.getTime());
        item.getDayItem().setDateTitle(formattedDate);
        item.getDayItem().setDbDate(formattedDbDate);
        calendar.add(Calendar.DAY_OF_WEEK,1);
    }
}
