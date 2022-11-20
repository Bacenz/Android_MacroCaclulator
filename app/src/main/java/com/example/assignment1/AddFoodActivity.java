package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

        //EditText setup

        EditText editTextName = (EditText) findViewById(R.id.editTextName);
        EditText editTextCalories = (EditText) findViewById(R.id.editTextCalories);
        EditText editTextProtein = (EditText) findViewById(R.id.editTextProtein);
        EditText editTextCarbs = (EditText) findViewById(R.id.editTextCarbs);
        EditText editTextFat = (EditText) findViewById(R.id.editTextFat);

        //Text for go back, also add alert message
        TextView textBack = (TextView) findViewById(R.id.textBack);
        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(AddFoodActivity.this)
                        .setTitle("Go back")
                        .setMessage("Are you sure you want to go back?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });

        //Button setup for add food, with error checking
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

                //If no error (cont = true) then add class to intent and finish
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
    }
}