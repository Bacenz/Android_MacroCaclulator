package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextCalories = (EditText) findViewById(R.id.editTextCalories);
        EditText editTextProtein = (EditText) findViewById(R.id.editTextProtein);
        EditText editTextCarbs = (EditText) findViewById(R.id.editTextCarbs);
        EditText editTextFat = (EditText) findViewById(R.id.editTextFat);



        Button buttonProceed = (Button) findViewById(R.id.buttonProceed);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            String name = "";
            int calories = 0, protein = 0, fat = 0, carbs = 0;
            boolean cont = true;

            @Override
            public void onClick(View view) {
                try {
                    calories = Integer.parseInt(editTextCalories.getText().toString());
                } catch(NumberFormatException e){
                    editTextCalories.setError("Invalid Calories");
                    cont = false;
                }
                try {
                    protein = Integer.parseInt(editTextProtein.getText().toString());
                } catch(NumberFormatException e){
                    editTextProtein.setError("Invalid Protein");
                    cont = false;
                }
                try {
                    carbs = Integer.parseInt(editTextCarbs.getText().toString());
                } catch(NumberFormatException e){
                    editTextCarbs.setError("Invalid Carbs");
                    cont = false;
                }
                try {
                    fat = Integer.parseInt(editTextFat.getText().toString());
                } catch(NumberFormatException e){
                    editTextFat.setError("Invalid Fat");
                    cont = false;
                }
                if(TextUtils.isEmpty(editTextName.getText())){
                    editTextName.setError("Name cannot be empty");
                }
                if(TextUtils.isEmpty(editTextCalories.getText())){
                    editTextCalories.setError("Calories cannot be empty");
                }
                if(cont){
                    Food food = new Food();
                    food.setName(editTextName.getText().toString());
                    food.setCalories(calories);
                    food.setProtein(protein);
                    food.setCarbs(carbs);
                    food.setFat(fat);
                }

            }
        });

        /*
        The idea is to transfer the Food class back to fragment_second using startActivityForResult and saving it into an object array (ArrayList<Food>)
        I might need another object called something food_of_today to save the arraylist of food (for today) and save it to a file (food_file) after OnStop() is called
        When fragment_second is opened and food_file is present, read data from food_file and display it on fragment_second
        When fragment_first is opened and food_file is present, read data from food_file and calculate remaining calories + macro nutrients
        The class food_of_today should save an instance of total protein + fat + carbs + calories for reuse
        After the end of day reset food_file data
         */

    }
}