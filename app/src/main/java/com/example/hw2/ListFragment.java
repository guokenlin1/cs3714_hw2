package com.example.hw2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    private static final String TAG = "ListFragment";
    private TextView timeView;
    private ArrayList<String> timeList;
    public ListFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        timeView = view.findViewById(R.id.timeView);

        //retrieve timestamps from bundle
        Bundle bundle = this.getArguments();
        if (bundle != null){
            timeList = bundle.getStringArrayList("time");
        }
        else {
            timeList = new ArrayList<String>();
        }

        //display timelist on textView
        ArrayList<String> timeStringList = new ArrayList<>();
        int index = 1;
        //format timelist in the form of "1. 00:00:00"
        for(String time: timeList){
//            String temp = index + ". "+ time + "\n";
            String temp = String.format("%2d. %s\n", index, time);
            timeStringList.add(temp);
            index++;
        }
        String timeString = "";
        for (String time: timeStringList){
            timeString += time;
        }
        timeView.setText(timeString);
        Log.d(TAG, "onCreateView: listFragment created");
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");


    }

    /**
     * add timestamp in the timeView
     */
    public void setTimeView(String time){
        if (timeView != null){
            timeView.setText(time);
        }
    }

}
