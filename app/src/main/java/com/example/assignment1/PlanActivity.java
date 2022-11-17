package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        User user = (User) intent.getSerializableExtra("User");
        user.setAge(user.getAge()+18);

        TextView textBack = (TextView) findViewById(R.id.textBack);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanActivity.this,InformationActivity.class);
                user.setAge(user.getAge()-18);
                intent.putExtra("User",user);
                startActivity(intent);
                finish();
            }
        });

        //TextView
        TextView textNumberBMI = (TextView) findViewById(R.id.textNumberBMI);
        TextView textNumberBMR = (TextView) findViewById(R.id.textNumberBMR);
        TextView textNumberTDEE = (TextView) findViewById(R.id.textNumberTDEE);

        //Set text for BMI + calculation
        user.calculateBMI();
        textNumberBMI.setText(String.valueOf(user.getBmi()));

        //Set text for rating
        TextView textRating = (TextView) findViewById(R.id.textRating);
        if(user.getBmi() <= 16){
            textRating.setText("Rating: Severe Thinness");
            textRating.setTextColor(getResources().getColor(R.color.severe_red));
        } else if (user.getBmi() <= 17) {
            textRating.setText("Rating: Moderate Thinness");
            textRating.setTextColor(getResources().getColor(R.color.not_healthy));
        } else if (user.getBmi() <= 18.5){
            textRating.setText("Rating: Mild Thinness");
            textRating.setTextColor(getResources().getColor(R.color.close_normal));
        } else if (user.getBmi() <= 25) {
            textRating.setText("Rating: Normal");
            textRating.setTextColor(getResources().getColor(R.color.normal));
        } else if (user.getBmi() <= 30) {
            textRating.setText("Rating: Overweight");
            textRating.setTextColor(getResources().getColor(R.color.close_normal));
        } else if (user.getBmi() <= 35){
            textRating.setText("Rating: Obese Class I");
            textRating.setTextColor(getResources().getColor(R.color.not_healthy));
        } else if (user.getBmi() <= 40){
            textRating.setText("Rating: Obese Class II");
            textRating.setTextColor(getResources().getColor(R.color.severe_red));
        } else {
            textRating.setText("Rating: Obese Class III");
            textRating.setTextColor(getResources().getColor(R.color.fat_fuck));
        }

        //Set text for BMR + calculation
        user.calculateBMR();
        textNumberBMR.setText(String.valueOf(user.getBmr()) + " kcal/day");

        //Set text for TDEE + calculation
        user.calculateTDEE();
        textNumberTDEE.setText(String.valueOf(user.getTdee()) + " kcal/day");

        //Daily Consumption calculation + display
        user.calculateDailyConsumption();
        TextView textDailyNumber = (TextView) findViewById(R.id.textDailyNumber);
        textDailyNumber.setText(String.valueOf(user.getDailyConsumption()) + " kcal/day");

        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setPlan(i);
                user.calculatePercentage();
                user.calculateProtein();
                user.calculateCarbs();
                user.calculateFat();

                TextView textProteinNumber = (TextView) findViewById(R.id.textProteinNumber);
                textProteinNumber.setText(String.valueOf(user.getProtein()) + " grams/day (" + user.getPercentProtein()*100 + "%)");
                TextView textCarbsNumber = (TextView) findViewById(R.id.textCarbsNumber);
                textCarbsNumber.setText(String.valueOf(user.getCarbs()) + " grams/day (" + user.getPercentCarb()*100 + "%)");
                TextView textFatNumber = (TextView) findViewById(R.id.textFatNumber);
                textFatNumber.setText(String.valueOf(user.getFat()) + " grams/day (" + user.getPercentFat()*100 + "%)");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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

    ======>>>>> FORMULA: daily macro goal = (daily consumption * percent goal) / macro

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

    Low Carbs:
    Protein: 30%
    Carbs: 40%
    Fat: 30%

    Low Fat:
    Protein: 30%
    Carbs: 50%
    Fat: 20%

    High Protein:
    Protein: 35%
    Carbs: 45%
    Fat: 30%

     */
}