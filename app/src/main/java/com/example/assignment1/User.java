package com.example.assignment1;

import java.io.Serializable;

public class User implements Serializable {
    private int age;
    private int gender;
    private double weight;
    private double height;
    private int activity;
    private int goal;

    private double bmi;
    private int bmr;
    private int tdee;

    private int dailyConsumption;
    private double percentFat;
    private double percentProtein;
    private double percentCarb;
    private int plan;

    private int protein;
    private int carbs;
    private int fat;

    public User(){
        age = 18;
        gender = 1;
        weight = 0;
        height = 0;
        activity = 0;
        goal = 0;
    }

    public User(int age, int gender, double weight, double height, int activity, int goal) {
        this.age = age;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.activity = activity;
        this.goal = goal;
    }

    public void calculateBMI(){
        bmi = Math.round((weight / (height/100 * height/100)) * 100.0) / 100.0;
    }

    public void calculateBMR(){
        if(gender == 1){
            bmr = (int) (447.593 + (9.247 * weight) + (3.098 * height) - (4.330 *age));
        } else {
            bmr = (int) (88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        }
    }

    public void calculateTDEE(){
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
        dailyConsumption = tdee;
    }

    public void calculateDailyConsumption(){
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
    }

    public void calculatePercentage(){
        switch(plan){
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
    }

    public void calculateProtein(){
        protein = (int) ((dailyConsumption * percentProtein) / 4);
    }
    public void calculateCarbs(){
        carbs = (int) ((dailyConsumption * percentCarb) / 4);
    }
    public void calculateFat(){
        fat = (int) ((dailyConsumption * percentFat) / 9);
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getActivity() {
        return activity;
    }

    public int getGoal() {
        return goal;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setActivity(int activity) {
        this.activity = activity;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public int getBmr() {
        return bmr;
    }

    public void setBmr(int bmr) {
        this.bmr = bmr;
    }

    public int getTdee() {
        return tdee;
    }

    public void setTdee(int tdee) {
        this.tdee = tdee;
    }

    public int getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(int dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public double getPercentFat() {
        return percentFat;
    }

    public void setPercentFat(double percentFat) {
        this.percentFat = percentFat;
    }

    public double getPercentProtein() {
        return percentProtein;
    }

    public void setPercentProtein(double percentProtein) {
        this.percentProtein = percentProtein;
    }

    public double getPercentCarb() {
        return percentCarb;
    }

    public void setPercentCarb(double percentCarb) {
        this.percentCarb = percentCarb;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }
}
