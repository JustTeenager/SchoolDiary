package com.example.schooldiary.Model;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DateManager {
    private Calendar calendar;
    private final DateFormat dateFormat;

    public DateManager(){
        dateFormat= new SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault());
        calendar=Calendar.getInstance();
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
        return twoWeeks;
    }

    private void loadTheWeeks(ArrayList<DayItem> twoWeeks,int distance) {
        calendar.add(Calendar.DAY_OF_WEEK,distance);
        for (int i=0;i<15;i++){
            twoWeeks.add(new DayItem(dateFormat.format(calendar.getTime())));
            calendar.add(Calendar.DAY_OF_WEEK,1);
            Log.d("tut_twoWeeks", dateFormat.format(calendar.getTime()));
        }
        Log.d("tut_twoWeeksSize", String.valueOf(twoWeeks.size()));
    }
}
