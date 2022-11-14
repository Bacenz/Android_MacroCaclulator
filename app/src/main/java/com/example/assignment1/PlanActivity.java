package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Intent intent = getIntent();
        int age = intent.getIntExtra("Age",0) + 18;
        int gender = intent.getIntExtra("Gender",0);
        double weight = intent.getDoubleExtra("Weight", 0.00);
        double height = intent.getDoubleExtra("Height", 0.00);
        int activity = intent.getIntExtra("Goal",0);
        int goal = intent.getIntExtra("Activity",0);

        double bmr = 0;
        double bmi = 0;
        double tdee = 0;

        if(gender == 1){
            bmr = 66 + (13.7 * weight) + (5 * height*100) - (6.8*age);
        } else {

        }
    }
}