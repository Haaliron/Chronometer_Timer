package com.haliron.chronometertimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity
{
    Button selectButton;
    TextView hourText;
    TextView minText;
    Button startButton;
    Button resetButton;
    Button chronometerButton;
    int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        selectButton = findViewById(R.id.selectButton);
        hourText = findViewById(R.id.hourText);
        minText = findViewById(R.id.minText);
        startButton = findViewById(R.id.startButton2);
        resetButton = findViewById(R.id.resetButton2);
        chronometerButton = findViewById(R.id.chronometerButton);
    }

    public void selectButton(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                hourText.setText(String.format(Locale.getDefault(), "%02d",hour));
                minText.setText(String.format(Locale.getDefault(), "%02d",minute));
            }
        };
        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity2.this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void startTimer(View view)
    {
        int hour = Integer.parseInt(hourText.getText().toString());
        int min = Integer.parseInt(minText.getText().toString());
        int time = 60*hour + min;

        if (hour == 0 && min == 0)
        {
            Toast.makeText(this, "Time is Can't Be Empty ! ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            startButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            chronometerButton.setEnabled(false);

            new CountDownTimer(time* 60000L,60000)
            {

                @Override
                public void onTick(long l)
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            String time = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(l),
                                    TimeUnit.MILLISECONDS.toMinutes(l) -
                                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)), Locale.getDefault());

                            final String[] hourmin = time.split(":");
                            hourText.setText(hourmin[0]);
                            minText.setText(hourmin[1]);
                        }
                    });
                }
                @Override
                public void onFinish()
                {
                    Toast.makeText(MainActivity2.this, "Timer Is Finished ! ", Toast.LENGTH_LONG).show();
                }
            }.start();
        }
    }

    public void resetTimer(View view)
    {
        startButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void chronometeR(View view)
    {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}