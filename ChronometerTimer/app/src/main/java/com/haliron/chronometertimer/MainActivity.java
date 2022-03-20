package com.haliron.chronometertimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    TextView goneTimeText;
    Button startButton;
    Button stopButton;
    Button resetButton;
    Button timerButton;
    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goneTimeText = findViewById(R.id.textView);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        timerButton = findViewById(R.id.button2);
        timer = new Timer();
    }
    public  void  startCount (View view)
    {
        startButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);
        resetButton.setEnabled(false);
        timerButton.setEnabled(false);
        startTimer();
    }

    public void  stopCount (View view)
    {
        startButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        resetButton.setEnabled(true);
        timerButton.setEnabled(true);
        timerTask.cancel();
    }


    public void resetCount(View view)
    {
        timerTask.cancel();
        time = 0.0;
        goneTimeText.setText(formatTime(0,0,0));
    }

    private void startTimer()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        goneTimeText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
    private String getTimerText()
    {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600 ) % 60;
        int minutes = ((rounded % 86400) % 3600 ) / 60;
        int hours = ((rounded % 86400) / 3600 );

        return formatTime(seconds,minutes,hours);
    }

    private String formatTime(int seconds, int minutes, int hours)
    {
        return String.format("%02d",hours) + ":" + String.format("%02d",minutes) + ":" + String.format("%02d",seconds);
    }


    public  void timeR(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
        startActivity(intent);
    }
}