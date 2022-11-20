package com.example.assignment1;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class thirdFragment extends Fragment {

    private User user = new User();

    public thirdFragment(){

    }

    public void writeToFile(String filename, User user){
        try{
            FileOutputStream fileOut = this.getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(user);
            objectOut.close();
            Toast.makeText(this.getActivity(), "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this.getActivity(), "Error: File Write Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public Object readFile(String filename, User user){
        String directory = this.getContext().getFilesDir().getAbsolutePath();
        File file = new File (directory + "/" + filename);
        if(file.isFile()){
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                user = (User) objectIn.readObject();
                objectIn.close();
                return user;
            } catch (FileNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: File Not Found", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                Toast.makeText(this.getActivity(), "Error: Error Reading Files", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: Food Class Not Found", Toast.LENGTH_SHORT).show();
            }
        }
        return user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        //TextView setup
        TextView textPercentProtein = (TextView) view.findViewById(R.id.textPercentProtein);
        TextView textPercentCarbs = (TextView) view.findViewById(R.id.textPercentCarbs);
        TextView textPercentFat = (TextView) view.findViewById(R.id.textPercentFat);

        //SeekBar setup
        SeekBar seekBarAge = (SeekBar) view.findViewById(R.id.seekBarAge);
        TextView textSeekBarAge = (TextView) view.findViewById(R.id.textSeekBarAge);
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

        //Button
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);

        //Goal spinner setup
        Spinner spinnerGoal = (Spinner) view.findViewById(R.id.spinnerGoal);
        ArrayAdapter<CharSequence> adapterGoal = ArrayAdapter.createFromResource(getContext(),R.array.Goal, android.R.layout.simple_spinner_item);
        adapterGoal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGoal.setAdapter(adapterGoal);

        //Activity spinner setup
        Spinner spinnerActivity = (Spinner) view.findViewById(R.id.spinnerActivity);
        ArrayAdapter<CharSequence> adapterActivity = ArrayAdapter.createFromResource(getContext(),R.array.Activity, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerActivity.setAdapter(adapterActivity);

        //Plan Spinner setup
        Spinner spinnerPlan = (Spinner) view.findViewById(R.id.spinnerPlan);
        ArrayAdapter<CharSequence> adapterPlan = ArrayAdapter.createFromResource(getContext(),R.array.Plan, android.R.layout.simple_spinner_item);
        adapterActivity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlan.setAdapter(adapterPlan);

        //EditText setup
        EditText editTextWeight = (EditText) view.findViewById(R.id.editTextWeight);
        EditText editTextHeight = (EditText) view.findViewById(R.id.editTextHeight);

        //RadioGroup and RadioButton setup
        RadioGroup radioGroupGender = (RadioGroup) view.findViewById(R.id.radioGroupGender);
        RadioButton radioBtnMale = (RadioButton) view.findViewById(R.id.radioBtnMale);
        RadioButton radioBtnFemale = (RadioButton) view.findViewById(R.id.radioBtnFemale);


        //After setting up, read user_info file and put it inside edittext, spinner, seekbar
        String user_file = "user_info";
        user = (User) readFile(user_file,user);

        seekBarAge.setProgress(user.getAge()-18);
        if(user.getGender() == 1) radioBtnFemale.setChecked(true);
        else radioBtnMale.setChecked(true);
        editTextWeight.setText(String.valueOf(user.getWeight()));
        editTextHeight.setText(String.valueOf(user.getHeight()));
        spinnerGoal.setSelection(user.getGoal());
        spinnerActivity.setSelection(user.getActivity());
        spinnerPlan.setSelection(user.getPlan());
        user.setPlan(user.getPlan());

        spinnerPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setPlan(i);
                user.calculatePercentage();
                user.calculateProtein();
                user.calculateCarbs();
                user.calculateFat();

                textPercentProtein.setText(String.valueOf(user.getPercentProtein() * 100) + "%");
                textPercentCarbs.setText(String.valueOf(user.getPercentCarb() * 100) + "%");
                textPercentFat.setText(String.valueOf(user.getPercentFat() * 100) + "%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Setup button save, with error checking
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Similar to InformationActivity, check each field and use cont to decide
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

                //If cont = true (no error) then recalculate everything about user, and update to file
                if(cont){
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
                    user.calculateBMI();
                    user.calculateBMR();
                    user.calculateTDEE();
                    user.calculateDailyConsumption();
                    user.setPlan(user.getPlan());
                    user.calculatePercentage();
                    user.calculateProtein();
                    user.calculateCarbs();
                    user.calculateFat();
                    writeToFile(user_file,user);
                }
            }
        });

        return view;
    }
}