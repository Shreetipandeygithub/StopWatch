package com.shreeti.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtView;
    Button btnStart,btnStop,btnHold;
    int second;
    private boolean currentTiming;
    private boolean beforeTiming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView=findViewById(R.id.txtView);
        btnHold=findViewById(R.id.btnHold);
        btnStart=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

        if(savedInstanceState!=null){

            savedInstanceState.getBoolean("currentTiming");
            savedInstanceState.getBoolean("beforeTiming");
            savedInstanceState.getInt("second");
        }
        runTimer();
    }

    public void onStart(View view){
        currentTiming=true;
    }

    public void onStop(View view){
        currentTiming=false;
        second=0;
    }
    public void onHold(View view){
        super.onPause();
        beforeTiming=currentTiming;
        currentTiming=false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("currentTiming",currentTiming);
        outState.putBoolean("beforeTiming",beforeTiming);
        outState.putInt("second",second);

    }
    private void runTimer() {
        Handler handler=new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int seconds=second%60;
                int minute=(second%36000)/60;
                int hours=second/36000;
                String time=String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minute,seconds);
                txtView.setText(time);

                if(currentTiming){
                    second++;
                }
                handler.postDelayed(this,0);
            }
        });

    }
}