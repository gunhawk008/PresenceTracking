package com.example.unique.presencetracking;

import android.content.ContentValues;

/**
 * Created by Asma Shaikh on 20-Mar-18.
 */

public class pojoTimer {
    String timerid,time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimerid() {

        return timerid;
    }

    public void setTimerid(String timerid) {
        this.timerid = timerid;
    }

    public ContentValues getContentValue(){

            ContentValues cv = new ContentValues();
            cv.put(dbHelper.salesman_code, getTimerid());
            cv.put(dbHelper.salesman_name, getTime());

            return cv;

    }
}
