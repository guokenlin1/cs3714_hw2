package com.example.hw2;
/**
 * The purpose of this file is to give a rough idea about how to implement a timer
 * and call it in the Async Task. This code will not be "copy paste" but you should
 * be able to use large chucks of it in your program. There are comments along the
 * way to help understand the code better.
 *
 * Author: Manav Ray <manavr>
 * Date: 03/02/2021
 */


import android.os.AsyncTask;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

/**
 * Timer class - you will most likely have this in its own file and should be able to use
 * most of the logic from here.
 *
 * Logic should be pretty easy to follow. You can post on piazza/discord or come to my office
 * hours if you have any other questions.
 */
public class Timer {

    private ArrayList<String> timeList;
    private int sec;
    private int min;
    private int hr;

    public Timer()
    {
        timeList = new ArrayList<String>();
        sec = 0;
        min = 0;
        hr = 0;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public int getSeconds() {
        return sec;
    }

    public int getMinutes() {
        return min;
    }

    public int getHours() {
        return hr;
    }

    public void calc()
    {
        sec++;
        if (sec == 60)
        {
            sec = 0;
            min++;
        }
        if (min == 60)
        {
            min = 0;
            hr++;
        }
    }

    /**
     * adda timestamp to the list when Lab button is pressed
     * @param time
     */
    public void addTime(String time)
    {
        timeList.add(time);
    }

    /**
     * reset the timer and clear the timeList
     * when Reset button is clicked
     */
    public void reset()
    {
        sec = 0;
        min = 0;
        hr = 0;
        timeList = new ArrayList<String>();
    }

    public ArrayList<String> getList(){
        return this.timeList;
    }

    public String toString(){

        return String.format("%02d:%02d:%02d", this.hr, this.min, this.sec);
    }
}

