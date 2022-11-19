package com.example.assignment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
        holder.iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Food")
                        .setMessage("Are you sure you want to delete the food?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Food removeItem = foods.get(holder.getAdapterPosition());
                                foods.remove(removeItem);
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(),foods.size());
                            }
                        })
                        .setNegativeButton("CANCEL", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(ArrayList<Food> foods){
        this.foods.clear();
        this.foods.addAll(foods);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textFoodName, textCalories,textProtein, textFat, textCarbs;
        private ImageView iconDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconDelete = itemView.findViewById(R.id.iconDelete);
            textFoodName = itemView.findViewById(R.id.textFoodName);
            textCalories = itemView.findViewById(R.id.textCalories);
            textProtein = itemView.findViewById(R.id.textProtein);
            textFat = itemView.findViewById(R.id.textFat);
            textCarbs = itemView.findViewById(R.id.textCarbs);
        }
    }
}
