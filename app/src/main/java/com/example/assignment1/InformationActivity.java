package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //Class
        User user = new User();

        //SeekBar setup
        SeekBar seekBarAge = (SeekBar) findViewById(R.id.seekBarAge);
        TextView textSeekBarAge = (TextView) findViewById(R.id.textSeekBarAge);
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int temp = i + 18;
                textSeekBarAge.setText(String.valueOf(temp));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Goal spinner setup
        Spinner spinnerGoal = (Spinner) findViewById(R.id.spinnerGoal);
        ArrayAdapter<CharSequence> adapterGoal = ArrayAdapter.createFromResource(this,R.array.Goal, android.R.layout.simple_spinner_item);
        adapterGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(adapterGoal);

        //Activity spinner setup
        Spinner spinnerActivity = (Spinner) findViewById(R.id.spinnerActivity);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(this,R.array.Activity, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapterActivity);

        //EditText setup
        EditText editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        EditText editTextHeight = (EditText) findViewById(R.id.editTextHeight);

        //RadioGroup and RadioButton setup
        RadioGroup radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        RadioButton radioBtnMale = (RadioButton) findViewById(R.id.radioBtnMale);
        RadioButton radioBtnFemale = (RadioButton) findViewById(R.id.radioBtnFemale);
        radioBtnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtnFemale.setError(null);
            }
        });
        radioBtnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioBtnFemale.setError(null);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            user = (User) bundle.getSerializable("User");

            seekBarAge.setProgress(user.getAge() - 18);
            if(user.getGender() == 1) radioBtnFemale.setChecked(true);
            else radioBtnMale.setChecked(true);
            editTextWeight.setText(String.valueOf(user.getWeight()));
            editTextHeight.setText(String.valueOf(user.getHeight()));
            spinnerGoal.setSelection(user.getGoal());
            spinnerActivity.setSelection(user.getActivity());
        }


        //Button Save setup
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                //Check if any field is empty (height, weight and gender)
                boolean cont = true;

                double weight = 0;
                double height = 0;

                if(TextUtils.isEmpty(editTextHeight.getText())){
                    editTextHeight.setError("Height is required");
                    cont = false;
                }
                if(TextUtils.isEmpty(editTextWeight.getText())){
                    editTextWeight.setError("Weight is required");
                    cont = false;
                }
                if(radioGroupGender.getCheckedRadioButtonId()==-1){
                    radioBtnFemale.setError("Gender is required");
                    cont = false;
                }
                try{
                    weight = Double.parseDouble(editTextWeight.getText().toString());
                } catch(NumberFormatException e){
                    editTextWeight.setError("Invalid Weight");
                    cont = false;
                }
                try{
                    height = Double.parseDouble(editTextHeight.getText().toString());
                } catch(NumberFormatException e){
                    editTextHeight.setError("Invalid Height");
                    cont = false;
                }


                //If no field empty then init intent and put information in for PlanActivity
                if(cont){
                    Intent intent = new Intent (InformationActivity.this,PlanActivity.class);
                    user.setAge(seekBarAge.getProgress() + 18);
                    if(radioGroupGender.getCheckedRadioButtonId() == R.id.radioBtnFemale){
                        user.setGender(1); //1 is female
                    } else {
                        user.setGender(2); //2 is male
                    }
                    user.setWeight(weight);
                    user.setHeight(height);
                    user.setActivity(spinnerActivity.getSelectedItemPosition());
                    user.setGoal(spinnerGoal.getSelectedItemPosition());
                    intent.putExtra("User",user);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}