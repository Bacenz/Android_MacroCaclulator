package com.example.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class secondFragment extends Fragment {

    private ArrayList<Food> foods = new ArrayList<Food>();

    public void writeToFile(){
        try{
            String filename = "food_file";
            FileOutputStream fileOut = this.getActivity().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(foods);
            objectOut.close();
        } catch (IOException e) {
            Toast.makeText(this.getActivity(), "Error: File Write Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void readFile(){
        String filename = "food_file";
        String directory = this.getContext().getFilesDir().getAbsolutePath();
        File file = new File (directory + "/" + filename);
        if(file.isFile()){
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                foods = (ArrayList<Food>) objectIn.readObject();
                objectIn.close();
            } catch (FileNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: File Not Found", Toast.LENGTH_SHORT).show();
            } catch (IOException e){
                Toast.makeText(this.getActivity(), "Error: Error Reading Files", Toast.LENGTH_SHORT).show();
            } catch (ClassNotFoundException e){
                Toast.makeText(this.getActivity(), "Error: Food Class Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public secondFragment(){
    }

    @Override
    public void onPause() {
        super.onPause();
        writeToFile();
    }

    private FoodViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        readFile();

        adapter = new FoodViewAdapter();
        adapter.setFoods(foods);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                foods.remove(positionStart);
                adapter.setFoods(foods);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        Button buttonAddFood = view.findViewById(R.id.buttonAddFood);
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(secondFragment.this.getContext(),AddFoodActivity.class);
                startActivityForResult(intent,100);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                if(bundle != null){
                    foods.add((Food) bundle.getSerializable("Food"));
                    adapter.setFoods(foods);
                }
            }
        }
    }
}