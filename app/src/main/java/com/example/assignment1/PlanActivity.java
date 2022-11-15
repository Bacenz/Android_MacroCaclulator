package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        //Spinner setup
        Spinner spinnerPlan = (Spinner) findViewById(R.id.spinnerPlan);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(this,R.array.Plan, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapterActivity);

        //Getting data from intent
        Intent intent = getIntent();
        int age = intent.getIntExtra("Age",0) + 18;
        int gender = intent.getIntExtra("Gender",0);
        double weight = intent.getDoubleExtra("Weight", 0.00);
        double height = intent.getDoubleExtra("Height", 0.00);
        int goal = intent.getIntExtra("Goal",0);
        int activity = intent.getIntExtra("Activity",0);

        //Initialize BMR, BMI, TDEE for calculation
        int bmr = 0;
        double bmi = 0;
        int tdee = 0;

        //TextView
        TextView textNumberBMI = (TextView) findViewById(R.id.textNumberBMI);
        TextView textNumberBMR = (TextView) findViewById(R.id.textNumberBMR);
        TextView textNumberTDEE = (TextView) findViewById(R.id.textNumberTDEE);

        //Set text for BMI + calculation
        bmi = Math.round((weight / (height/100 * height/100)) * 100.0) / 100.0;
        textNumberBMI.setText(String.valueOf(bmi));

        //Set text for rating
        TextView textRating = (TextView) findViewById(R.id.textRating);
        if(bmi <= 16){
            textRating.setText("Rating: Severe Thinness");
            textRating.setTextColor(getResources().getColor(R.color.severe_red));
        } else if (bmi <= 17) {
            textRating.setText("Rating: Moderate Thinness");
            textRating.setTextColor(getResources().getColor(R.color.not_healthy));
        } else if (bmi <= 18.5){
            textRating.setText("Rating: Mild Thinness");
            textRating.setTextColor(getResources().getColor(R.color.close_normal));
        } else if (bmi <= 25) {
            textRating.setText("Rating: Normal");
            textRating.setTextColor(getResources().getColor(R.color.normal));
        } else if (bmi <= 30) {
            textRating.setText("Rating: Overweight");
            textRating.setTextColor(getResources().getColor(R.color.close_normal));
        } else if (bmi <= 35){
            textRating.setText("Rating: Obese Class I");
            textRating.setTextColor(getResources().getColor(R.color.not_healthy));
        } else if (bmi <= 40){
            textRating.setText("Rating: Obese Class II");
            textRating.setTextColor(getResources().getColor(R.color.severe_red));
        } else {
            textRating.setText("Rating: Obese Class III");
            textRating.setTextColor(getResources().getColor(R.color.fat_fuck));
        }

        //Set text for BMR + calculation
        if(gender == 1){
            bmr = (int) (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 *age));
        } else {
            bmr = (int) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        }
        textNumberBMR.setText(String.valueOf(bmr));
        textNumberBMR.append(" kcal/day");

        //Set text for TDEE + calculation
        switch(activity){
            case 0:
                tdee = (int) (bmr * 1.2);
                break;
            case 1:
                tdee = (int) (bmr * 1.375);
                break;
            case 2:
                tdee = (int) (bmr * 1.55);
                break;
            case 3:
                tdee = (int) (bmr * 1.725);
                break;
            case 4:
                tdee = (int) (bmr * 1.9);
                break;
        }
        textNumberTDEE.setText(String.valueOf(tdee));
        textNumberTDEE.append(" kcal/day");
    }

    /*
    Calculation for protein% fat% and carb%
    protein, carb = 4 calories          fat = 9 calories        1 gram = 7.7162 kcal

    -----BASICS-----
    1 kg of body fat = 7700 kcal
    0.13kg = 1000 kcal
    0.25kg = 1925 kcal weekly --> 275 kcal +- daily
    0.5kg = 3850 kcal --> 550 kcal +- daily
    1kg = 7700 kcal --> 1100 kcal +- daily

    --> Daily consumption = TDEE - daily deficit/surplus
    --> daily macro goal = (daily consumption * percent goal) / macro
    i.e. daily consumption = 2500; percent goal = 40 protein% 30 fat% 30 carb%; macro above
    -->
    protein = (2500 * 0.4) / 4 = 250 gram protein daily
    carb = (2500 * 0.3) / 4 = 187 gram carb daily
    fat = (2500 * 0.3) / 9 = 83 gram fat daily

    -----Recommended Macro goal-----
    Protein: 10% - 35%
    Carbs: 45% - 65%
    Fat: 20% - 35%

    Balanced:
    Protein: 25%
    Carbs: 50%
    Fat: 25%

    Low Fat:
    Protein: 30%
    Carbs: 50%
    Fat: 20%

    Low Carbs:
    Protein: 30%
    Carbs: 40%
    Fat: 30%

    High Protein:
    Protein: 35%
    Carbs: 45%
    Fat: 30%

     */
}