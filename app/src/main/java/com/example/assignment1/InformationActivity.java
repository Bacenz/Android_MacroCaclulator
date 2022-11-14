package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

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

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //SeekBar setup
        SeekBar seekBarAge = (SeekBar) findViewById(R.id.seekBarAge);
        TextView textSeekBarAge = (TextView) findViewById(R.id.textSeekBarAge);
        seekBarAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int temp = i + 18;
                textSeekBarAge.setText(Integer.toString(temp));
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
        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(InformationActivity.this, "You selected " + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Activity spinner setup
        Spinner spinnerActivity = (Spinner) findViewById(R.id.spinnerActivity);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(this,R.array.Activity, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapterActivity);
        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(InformationActivity.this, "You selected " + text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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


        //Button Save setup
        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if any field is empty (height, weight and gender)
                boolean cont = true;
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

                //If no field empty then init intent and put information in for PlanActivity
                if(cont){
                    Intent intent = new Intent (InformationActivity.this,PlanActivity.class);
                    intent.putExtra("Age",seekBarAge.getProgress());
                    if(radioGroupGender.getCheckedRadioButtonId() == R.id.radioBtnFemale){
                        intent.putExtra("Gender",1); //1 is female
                    } else {
                        intent.putExtra("Gender",2); //2 is male
                    }
                    intent.putExtra("Weight",Double.parseDouble(editTextWeight.getText().toString()));
                    intent.putExtra("Height",Double.parseDouble(editTextHeight.getText().toString()));
                    intent.putExtra("Activity",spinnerActivity.getSelectedItemPosition());
                    intent.putExtra("Goal",spinnerGoal.getSelectedItemPosition());
                    startActivity(intent);
                    finish();
                }
            }
        });

//        //Button Reset setup
//        Button buttonReset = (Button) findViewById(R.id.buttonReset);
//        buttonReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                seekBarAge.setProgress(0);
//                editTextHeight.getText().clear();
//                editTextWeight.getText().clear();
//                spinnerActivity.setSelection(0);
//                spinnerGoal.setSelection(0);
//                radioGroupGender.clearCheck();
//            }
//        });

    }
}