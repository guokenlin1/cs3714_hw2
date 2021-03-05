package com.example.hw2;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 */
public class ControlFragment extends Fragment {

    private static final String TAG = "ControlFragment";
    /**
     * In addition to other fields, you will need these:
     */
    private TimerAsyncTask asynctask; // will use this in controller to execute timer.
    boolean running; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    boolean firstClick; //boolean to check whether start button is clicked for the first time
    Timer timer; // timer used in AsyncTask and Controller.
    TextView timerText; // the textview in your UI to display time.
    private Button startButton; //start button
    private Button lapButton; //lap button
    private Button resetButton; //reset button
    public ControlFragment() {
        setArguments(new Bundle());
        // Required empty public constructor
    }

    /**
     * getter of timer
     */
    public Timer getTimer(){
        return this.timer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (timer == null){
            timer = new Timer();
        }
        if (asynctask == null){
            asynctask = new TimerAsyncTask();
        }

    }

    /**
     * used when switching between fragments
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onSaveInstanceState");
        getArguments().putBoolean("running", running);
        getArguments().putString("startButton", startButton.getText().toString());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("running", running);
        outState.putString("time", timer.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView:");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        running = false;
        firstClick = true;
        timerText = view.findViewById(R.id.time);

        Log.d(TAG, "step 1");
        startButton = (Button) view.findViewById(R.id.start);
        lapButton = (Button) view.findViewById(R.id.lap);

        resetButton = (Button) view.findViewById(R.id.reset);
        Log.d(TAG, startButton.toString());

        //bundle for saving data when onPause()-------------------------
        Bundle bundle = getArguments();
        String time = bundle.getString("time");
        if (time != null){
            timerText.setText(timer.toString());
        }
        Log.d(TAG, "onCreateView: timer:" + timer.toString());
        running = bundle.getBoolean("running");
        //check running state
        if (running){
            new TimerAsyncTask().execute();
        }

        //check start button
        String buttonText = bundle.getString("startButton");
        if (buttonText != null && buttonText.equals("Stop")){
            startButton.setText("Stop");
        }
        //-------------------------------------------------------------------------

        //triggered when config changed, like change oriantation
        if (savedInstanceState != null){
            Log.d(TAG, "onActivityCreated: restore time");
            String timeString = savedInstanceState.getString("time");
            Log.d(TAG, "onActivityCreated: " + timeString);
            //make timerText is linked to textView in xml first
            timerText = view.findViewById(R.id.time);
            timerText.setText(time);

            //restore the running state of asynctask
            running = savedInstanceState.getBoolean("running");
            //if task is running, then start button should be stop
            if (running) {
                startButton.setText("Stop");
            }
            else{
                startButton.setText("Start");
            }

        }
        //-------------------------------------------------------------------------

        //start button
        startButton.setOnClickListener(new View.OnClickListener() {
            /**
             * triggered when start of stop button is clicked
             */
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clickStart: clicked");
                //change running start
                running = !running;

                //if running. then change start button to stop
                //and start the timer task
                if (running)
                {
                    startButton.setText("Stop");
                    asynctask.execute();
                }
                //else change back to star button
                else {
                    startButton.setText("Start");
                    asynctask.cancel(true);
                    asynctask = new TimerAsyncTask();
                }
            }
        });

        //lap button
        lapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Lap: " + "clicked");
                //add current time to timestamp list
                timer.addTime(timer.toString());
                Log.d(TAG, "timeList: " + timer.getList().toString());

            }
        });

        //reset button
        resetButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(TAG, "clickReset: clicked");
                timer = new Timer();
                timerText.setText(timer.toString()); // update UI.
                asynctask.cancel(true);
                asynctask = new TimerAsyncTask();
                running = false;
                Log.d(TAG, "Start Button: " + startButton.getText());
                //if timer is still running, change stop to start button
                if (startButton.getText().equals("Stop")){
                    startButton.setText("Start");
                }
            }
        });
        return view;
    }


    /**
     * asyncTask uses to manage timer
     */
    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

        protected void onCancelled() {
            Log.d(TAG, "on cancelled");
//            timer.reset();
//            String curr_time = String.format("%02d:%02d:%02d", 0, 0, 0);
//            timerText.setText(curr_time); // update UI.
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            String curr_time = String.format("%02d:%02d:%02d", values[0], values[1], values[2]); // used to properly format string.
            timerText.setText(curr_time); // update UI.
        }

        @Override
        protected Void doInBackground(Integer... times) {
            Log.d(TAG, "Task in Background");
            while (running) { // running boolean must be updated in controller.
                Log.d(TAG, "Task in while loop");
                //if task is cancelled, stop the loop
                if (isCancelled()){
                    Log.d(TAG, "is cancelled");
                    break;
                }

                timer.calc(); // calculate time.
                publishProgress(timer.getHours(), timer.getMinutes(), timer.getSeconds()); // publish progress for onProgressUpdate method to be triggered.
                try {
                    Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }

            return null;
        }




    }




}