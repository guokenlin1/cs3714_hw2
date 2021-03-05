package com.example.hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ControlFragment start;
    ListFragment list;
    Timer timer;
    private boolean firstShowList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if we are at the land mode
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }
        //don't reinitialize fragments when config changes
        if (savedInstanceState != null){
            start = (ControlFragment) getSupportFragmentManager().findFragmentByTag("control");
            list = (ListFragment) getSupportFragmentManager().findFragmentByTag("list");
        }
        else {
            //initialize fragments
            start = new ControlFragment();
            list = new ListFragment();
            //show all buttons on the frameLayout
            getSupportFragmentManager().beginTransaction().replace(R.id.buttonLayout, start).addToBackStack(null).commit();
        }

    }

    /**
     * navigate to List activity
     */
    public void goToList(View view){
        Log.d(TAG, "goToList: Click");
        timer = start.getTimer();
        //transfer timestamp to listFragment
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("time", timer.getList());
        //set fragment class arguments

        list.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.buttonLayout, list).addToBackStack(null).commit();


    }

    /**
     * navigate to Main activity
     */
    public void goToMain(View view){
        Log.d(TAG, "goToMain: Click");

        getSupportFragmentManager().beginTransaction().replace(R.id.buttonLayout, start).addToBackStack(null).commit();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }
}