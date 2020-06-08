package com.example.androidexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Creates Thread Runnable and make the app sleep for 2,3 seconds. then sends the user to the main activity
        //the activity uses the finish() method, since we dont want to see the splash screen again after entering the app
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(2300);
                    Intent intent = new Intent(SplashScreen.this,AuthActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });thread.start();
    }
}
