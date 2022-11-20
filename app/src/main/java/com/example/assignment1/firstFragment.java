package com.example.assignment1;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class firstFragment extends Fragment {
    public firstFragment(){
    }

    private ArrayList<Food> foods = new ArrayList<Food>();

    public Object readFile(String filename, Object object){
        String directory = this.getContext().getFilesDir().getAbsolutePath();
        File file = new File (directory + "/" + filename);
        if(file.isFile()){
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                object = (Object) objectIn.readObject();
                objectIn.close();
                return object;
            } catch (FileNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: File Not Found", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                Toast.makeText(this.getActivity(), "Error: Error Reading Files", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: Food Class Not Found", Toast.LENGTH_SHORT).show();
            }
        }
        return object;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        //Setup textView and progressbar
        TextView textTodayCalories = view.findViewById(R.id.textTodayCalories);
        TextView textNeededCalories = view.findViewById(R.id.textNeededCalories);
        TextView textProteinRemains = view.findViewById(R.id.textProteinRemains);
        TextView textCarbsRemains = view.findViewById(R.id.textCarbsRemains);
        TextView textFatRemains = view.findViewById(R.id.textFatRemains);
        ProgressBar progressBarCalories = view.findViewById(R.id.progressBarCalories);
        ProgressBar progressBarProtein = view.findViewById(R.id.progressBarProtein);
        ProgressBar progressBarCarbs = view.findViewById(R.id.progressBarCarbs);
        ProgressBar progressBarFat = view.findViewById(R.id.progressBarFat);

        //Handler for progressbar updates
        Handler progressBarHandler = new Handler();

        //File IO and classes
        DailyMacro dailyMacro = new DailyMacro();
        User user = new User();
        String food_file = "food_file";
        String user_file = "user_info";

        foods = (ArrayList<Food>) readFile(food_file,foods);
        user = (User) readFile(user_file,user);

        //After getting all food of the day, calculate total calories + protein + carbs + fat
        dailyMacro.calculateEverything(foods);

        //After calculate then set the text of textview
        textTodayCalories.setText(String.valueOf(dailyMacro.getTotalCal()));
        textNeededCalories.setText(String.valueOf(user.getDailyConsumption()));
        textProteinRemains.setText(String.valueOf(dailyMacro.getTotalProtein()) + " g of " + user.getProtein() + " g");
        textCarbsRemains.setText(String.valueOf(dailyMacro.getTotalCarbs()) + " g of " + user.getCarbs() + " g");
        textFatRemains.setText(String.valueOf(dailyMacro.getTotalFat()) + " g of " + user.getFat() + " g");

        //Also set max value of progressbar
        progressBarCalories.setMax(user.getDailyConsumption());
        progressBarProtein.setMax(user.getProtein());
        progressBarCarbs.setMax(user.getCarbs());
        progressBarFat.setMax(user.getFat());

        //Thread used to update progress bar
        progressBarHandler.post(new Runnable() {
            @Override
            public void run() {
                progressBarCalories.setProgress(dailyMacro.getTotalCal());
                progressBarProtein.setProgress(dailyMacro.getTotalProtein());
                progressBarCarbs.setProgress(dailyMacro.getTotalCarbs());
                progressBarFat.setProgress(dailyMacro.getTotalFat());
            }
        });

        return view;
    }
}