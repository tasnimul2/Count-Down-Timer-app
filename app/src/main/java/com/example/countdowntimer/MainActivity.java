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





    public void stopOnClick(View view){
        alarm.stop();
        recreate();

    }

    public void timeOnClick(View view){
        dateTV = (TextView) findViewById(R.id.dateTV);
        currentTime = Calendar.getInstance().getTime();

        dateTV.setAlpha(1f);
        dateTVvisible = true;
        dateTV.setText(""+currentTime);

    }

    public void startOnClick(View view){
        if(dateTVvisible) {
            dateTV.setAlpha(0f);
        }
        dateTVvisible = false;
        timerTV = (TextView) findViewById(R.id.TimerTV);
        alarm = MediaPlayer.create(this,R.raw.alarm);

        new CountDownTimer(minuteWanted, 1000) {

            public void onTick(long milliSecondsTillDone){
                seconds-= 1000;

                Log.i("time left", " "+ milliSecondsTillDone/60000 + " minutes" + milliSecondsTillDone/1000 + " seconds");


                timerTV.setText(" "+ milliSecondsTillDone/60000 + " : " + seconds/1000 );

                if(seconds < 10000){
                    timerTV.setText(" "+ milliSecondsTillDone/60000 + " : 0" + seconds/1000 );
                }

                if(seconds == 0){

                    seconds = 60000;
                }

            }

            public  void onFinish(){
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
                progress = ((int)Math.round(progress/increment ))*increment;
                Log.i("SeekBar progress", ""+progress);
                minuteWanted = progress;
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
