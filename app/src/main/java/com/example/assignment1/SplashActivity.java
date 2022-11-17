package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.io.File;
import java.io.FileInputStream;

public class SplashActivity extends AppCompatActivity {

    public boolean readFile(){
        String filename = "user_info";
        File directory = SplashActivity.this.getFilesDir();
        File file = new File(directory,filename);
        return file.isFile();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(readFile()){
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this,InformationActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}