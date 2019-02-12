package com.kasperistudios.android.balanceit;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Luokka tallentaa päivänvalinta-activityssä valitun päivän.
 */
public class ChosenDate implements Serializable{

    private int year;
    private int month;

    public ChosenDate() {
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(calendar.YEAR);
        this.month = calendar.get(calendar.MONTH);
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
