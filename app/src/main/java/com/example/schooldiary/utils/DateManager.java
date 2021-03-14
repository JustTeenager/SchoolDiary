package com.example.schooldiary.utils;

import android.util.Log;

import com.example.schooldiary.model.DayItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DateManager {
    private Calendar calendar;
    private final DateFormat dateFormat;

    public DateManager(){
        dateFormat= new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault());
        calendar=Calendar.getInstance();
    }

    public ArrayList<DayItem> setupTwoWeeksFromCurrentCalendar(Calendar calendar){
        this.calendar=calendar;
        ArrayList<DayItem> items=setupTwoWeeksFromToday();
        this.calendar=Calendar.getInstance();
        return items;
    }

    public ArrayList<DayItem> setupTwoWeeksFromToday(){
        ArrayList<DayItem> twoWeeks=new ArrayList<>();
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.MONDAY:{
                loadTheWeeks(twoWeeks,0);
            }break;
            case Calendar.TUESDAY:{
                loadTheWeeks(twoWeeks,-1);
            }break;
            case Calendar.WEDNESDAY:{
                loadTheWeeks(twoWeeks,-2);
            }break;
            case Calendar.THURSDAY:{
                loadTheWeeks(twoWeeks,-3);
            }break;
            case Calendar.FRIDAY:{
                loadTheWeeks(twoWeeks,-4);
            }break;
            case Calendar.SATURDAY:{
                loadTheWeeks(twoWeeks,-5);
            }break;
            case Calendar.SUNDAY:{
                loadTheWeeks(twoWeeks,-6);
            }break;
        }
        calendar=Calendar.getInstance();
        return twoWeeks;
    }

    private void loadTheWeeks(ArrayList<DayItem> twoWeeks,int distance) {
        calendar.add(Calendar.DAY_OF_WEEK,distance);
        for (int i=0;i<14;i++){
            String formattedDate=dateFormat.format(calendar.getTime());
            twoWeeks.add(new DayItem(formattedDate));
            calendar.add(Calendar.DAY_OF_WEEK,1);
            Log.d("tut_twoWeeks",formattedDate);
        }
        Log.d("tut_twoWeeksSize", String.valueOf(twoWeeks.size()));
    }
}
