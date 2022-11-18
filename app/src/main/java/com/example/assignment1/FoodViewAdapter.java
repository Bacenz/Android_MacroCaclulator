package com.example.assignment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder> {

    private ArrayList<Food> foods = new ArrayList<Food>();

    public FoodViewAdapter(){

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textFoodName.setText(foods.get(position).getName());
        holder.textCalories.setText("Calories: " + foods.get(position).getCalories());
        holder.textProtein.setText("Protein: " + foods.get(position).getProtein());
        holder.textFat.setText("Fat: " + foods.get(position).getFat());
        holder.textCarbs.setText("Carbs: " + foods.get(position).getCarbs());
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(ArrayList<Food> foods){
        this.foods = foods;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textFoodName, textCalories,textProtein, textFat, textCarbs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textFoodName = itemView.findViewById(R.id.textFoodName);
            textCalories = itemView.findViewById(R.id.textCalories);
            textProtein = itemView.findViewById(R.id.textProtein);
            textFat = itemView.findViewById(R.id.textFat);
            textCarbs = itemView.findViewById(R.id.textCarbs);
        }
    }


}
