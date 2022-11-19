package com.example.assignment1;

import java.util.ArrayList;

public class DailyMacro {
    private int totalCal, totalProtein, totalCarbs, totalFat;

    public DailyMacro(int totalCal, int totalProtein, int totalCarbs, int totalFat) {
        this.totalCal = totalCal;
        this.totalProtein = totalProtein;
        this.totalCarbs = totalCarbs;
        this.totalFat = totalFat;
    }

    public DailyMacro() {
        totalCal = 0;
        totalProtein = 0;
        totalCarbs = 0;
        totalFat = 0;
    }

    public int getTotalCal() {
        return totalCal;
    }

    public int getTotalProtein() {
        return totalProtein;
    }

    public int getTotalCarbs() {
        return totalCarbs;
    }

    public int getTotalFat() {
        return totalFat;
    }

    public void calculateEverything(ArrayList<Food> foods){
        for(int i = 0; i < foods.size();i++){
            totalCal+= foods.get(i).getCalories();
            totalProtein+= foods.get(i).getProtein();
            totalCarbs+= foods.get(i).getCarbs();
            totalFat+= foods.get(i).getFat();
        }
    }

}
