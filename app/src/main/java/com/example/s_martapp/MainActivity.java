package com.example.s_martapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    Button sign_in, sign_up;
    Sign_in_sheet sign_in_sheet;
    Sign_up_sheet sign_up_sheet;
    ViewPager viewp;
    SlideViewPagerAdapter slideViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewp=findViewById(R.id.viewPager);

        slideViewPagerAdapter=new SlideViewPagerAdapter(MainActivity.this);
        viewp.setAdapter(slideViewPagerAdapter);
        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);


        sign_in = findViewById(R.id.sign_in_wel);
        sign_up = findViewById(R.id.sign_up_wel);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //invite_ques_id = qa_ques_id;
                sign_in_sheet = new Sign_in_sheet();
                sign_in_sheet.show(getSupportFragmentManager(), "Invite");
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View v) {

                sign_up_sheet = new Sign_up_sheet();
                sign_up_sheet.show(getSupportFragmentManager(), "Invite");

            }
        });

    }
    public class MyTimerTask extends TimerTask{
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewp.getCurrentItem()==0)
                        viewp.setCurrentItem(1);
                    else if(viewp.getCurrentItem()==1)
                        viewp.setCurrentItem(2);
                    else if(viewp.getCurrentItem()==2)
                        viewp.setCurrentItem(3);
                    else
                        viewp.setCurrentItem(0);
                }
            });
        }
    }
}