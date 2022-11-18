package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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

        TextView textBack = (TextView) findViewById(R.id.textBack);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button buttonProceed = (Button) findViewById(R.id.buttonProceed);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "";
                int calories = 0, protein = 0, fat = 0, carbs = 0;
                boolean cont = true;

                if(TextUtils.isEmpty(editTextName.getText())){
                    editTextName.setError("Name cannot be empty");
                    cont = false;
                }
                if(TextUtils.isEmpty(editTextCalories.getText())){
                    editTextCalories.setError("Calories cannot be empty");
                    cont = false;
                }

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


                if(cont){
                    Food food = new Food();
                    food.setName(editTextName.getText().toString());
                    food.setCalories(calories);
                    food.setProtein(protein);
                    food.setCarbs(carbs);
                    food.setFat(fat);
                    Intent intent = new Intent(AddFoodActivity.this,secondFragment.class);
                    intent.putExtra("Food",food);
                    setResult(RESULT_OK,intent);
                    finish();
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