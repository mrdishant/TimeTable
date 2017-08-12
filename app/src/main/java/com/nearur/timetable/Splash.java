package com.nearur.timetable;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    TextView textView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        textView=(TextView)findViewById(R.id.textWelcome);
        sharedPreferences=getSharedPreferences("Time",MODE_PRIVATE);
        h.sendEmptyMessageDelayed(7623,0);
        h.sendEmptyMessageDelayed(7567,2500);
    }

    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==7623){
                Animation animation= AnimationUtils.loadAnimation(Splash.this,R.anim.splash);
                if(sharedPreferences.contains("name")){
                    textView.setText("Welcome\n"+sharedPreferences.getString("name",""));
                }
                textView.setVisibility(View.VISIBLE);
                textView.startAnimation(animation);
            }else if(msg.what==7567){
                Intent i=new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    };
}
