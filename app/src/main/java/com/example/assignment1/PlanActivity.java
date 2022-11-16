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
    private int plan = 0;
    private int dailyConsumption = 0;
    private double percentFat = 0.25, percentProtein = 0.25, percentCarb = 0.5;


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

        TextView textBack = (TextView) findViewById(R.id.textBack);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanActivity.this,InformationActivity.class);
                intent.putExtra("Age",age-36);
                intent.putExtra("Gender",gender);
                intent.putExtra("Weight",weight);
                intent.putExtra("Height",height);
                intent.putExtra("Goal",goal);
                intent.putExtra("Activity",activity);
                startActivity(intent);
                finish();
            }
        });


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
        textNumberBMR.setText(String.valueOf(bmr) + " kcal/day");

        //Set text for TDEE + calculation
        switch(activity){
            case 0: //not active
                tdee = (int) (bmr * 1.2);
                break;
            case 1: //quite active
                tdee = (int) (bmr * 1.375);
                break;
            case 2: //average active
                tdee = (int) (bmr * 1.55);
                break;
            case 3: //active
                tdee = (int) (bmr * 1.725);
                break;
            case 4: //athlete
                tdee = (int) (bmr * 1.9);
                break;
            default:
                break;
        }
        textNumberTDEE.setText(String.valueOf(tdee) + " kcal/day");

        //Daily Consumption calculation + display
        dailyConsumption = tdee;
        switch (goal){
            case 0: //maintain weight
                break;
            case 1: //mild weight loss
                dailyConsumption = dailyConsumption - 275;
                break;
            case 2: //weight loss
                dailyConsumption = dailyConsumption - 550;
                break;
            case 3: //intense weight loss
                dailyConsumption = dailyConsumption - 1100;
                break;
            case 4: //mild weight gain
                dailyConsumption = dailyConsumption + 275;
                break;
            case 5: //weight gain
                dailyConsumption = dailyConsumption + 550;
                break;
            case 6: //intense weight gain
                dailyConsumption = dailyConsumption + 1100;
                break;
            default:
                break;
        }
        TextView textDailyNumber = (TextView) findViewById(R.id.textDailyNumber);
        textDailyNumber.setText(String.valueOf(dailyConsumption) + " kcal/day");

        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0: //balanced
                        percentFat = 0.25;
                        percentProtein = 0.25;
                        percentCarb = 0.5;
                        break;
                    case 1: //low carb
                        percentFat = 0.3;
                        percentProtein = 0.3;
                        percentCarb = 0.4;
                        break;
                    case 2: //low fat
                        percentFat = 0.2;
                        percentProtein = 0.3;
                        percentCarb = 0.5;
                        break;
                    case 3: //high protein
                        percentFat = 0.30;
                        percentProtein = 0.35;
                        percentCarb = 0.45;
                        break;
                    default:
                        break;
                }
                int protein = (int) ((dailyConsumption * percentProtein) / 4);
                int carbs = (int) ((dailyConsumption * percentCarb) / 4);
                int fat = (int) ((dailyConsumption * percentFat) / 9);

                TextView textProteinNumber = (TextView) findViewById(R.id.textProteinNumber);
                textProteinNumber.setText(String.valueOf(protein) + " grams/day (" + percentProtein*100 + "%)");
                TextView textCarbsNumber = (TextView) findViewById(R.id.textCarbsNumber);
                textCarbsNumber.setText(String.valueOf(carbs) + " grams/day (" + percentCarb*100 + "%)");
                TextView textFatNumber = (TextView) findViewById(R.id.textFatNumber);
                textFatNumber.setText(String.valueOf(fat) + " grams/day (" + percentFat*100 + "%)");
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