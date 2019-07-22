package com.example.countdowntimer;


import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView timerTV;
    TextView dateTV;
    long seconds = 60000;
    MediaPlayer alarm;
    boolean dateTVvisible = false;
    Date currentTime ;
    SeekBar minuteSetter;
    long minuteWanted;
    int increment = 60000;





    public void stopOnClick(View view){ //when stop button is pressed, stop the alarm and reset the app.
        alarm.stop();
        recreate();

    }

    public void timeOnClick(View view){ 
        dateTV = (TextView) findViewById(R.id.dateTV); //Text View dateTV displays the date and time
        currentTime = Calendar.getInstance().getTime(); //object currentTime of Date Class, getting the date and time

        dateTV.setAlpha(1f); //make dateTV text view visible 
        dateTVvisible = true;
        dateTV.setText(""+currentTime); //displays the date and time

    }

    public void startOnClick(View view){
        if(dateTVvisible) {
            dateTV.setAlpha(0f); //makes the date and time invisible
        }
        dateTVvisible = false;
        timerTV = (TextView) findViewById(R.id.TimerTV);
        alarm = MediaPlayer.create(this,R.raw.alarm);

        new CountDownTimer(minuteWanted, 1000) { //timer that starts at "minuteWanted", which is set by the user and counts down every 1k milliseconds (1 second)

            public void onTick(long milliSecondsTillDone){
                seconds-= 1000;//seconds is initialized at 60k milliseconds (60 sec). Every 1k milliseconds, 1k millisec is taken off.

                Log.i("time left", " "+ milliSecondsTillDone/60000 + " minutes" + milliSecondsTillDone/1000 + " seconds");


                timerTV.setText(" "+ milliSecondsTillDone/60000 + " : " + seconds/1000 );//display the minutes and seconds 

                if(seconds < 10000){
                    timerTV.setText(" "+ milliSecondsTillDone/60000 + " : 0" + seconds/1000 ); 
                }

                if(seconds == 0){ //when seconds hits 0 re-initialze seconds to 60k milliseconds 

                    seconds = 60000;
                }

            }

            public  void onFinish(){ //wehn count down stops, play alarm audio clip.
                Log.i("Buzz", "Timer done");
                    alarm.start();


            }

        }.start();

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        //____________________________ Default TIMER _____________________________________//
        timerTV = (TextView) findViewById(R.id.TimerTV);
        timerTV.setText("00 : 00 ");

        //_____________________________Minutes Seek Bar ________________________________________//


        minuteSetter = (SeekBar) findViewById(R.id.minuteSetterSeekBar);
        minuteSetter.setMax(3600000);

        minuteSetter.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int)Math.round(progress/increment ))*increment; //increase progress bar by "increment" which equals 60k. 
                Log.i("SeekBar progress", ""+progress);
                minuteWanted = progress; //set the timer's length  to the  amount the user wants. 
                timerTV.setText(" "+ minuteWanted/60000 + " : 00" );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






    }
}
