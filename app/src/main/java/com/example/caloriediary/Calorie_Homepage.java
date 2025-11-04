package com.example.caloriediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.LuhnChecksumValidator;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Api_Refactored.Camera;
import com.example.caloriediary.Api_Refactored.MainActivity2;
import com.example.caloriediary.RecyclerView.Breakfast_RecyclerView;
import com.example.caloriediary.RecyclerView.Dinner_RecyclerView;
import com.example.caloriediary.RecyclerView.Lunch_RecyclerView;
import com.example.caloriediary.databinding.ActivityCalorieHomepageBinding;

import java.util.ArrayList;


public class Calorie_Homepage extends AppCompatActivity {

    final static String TAG = "Calorie_Homepage_";



    private ActivityCalorieHomepageBinding binding;
    ArrayList<String> User_Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalorieHomepageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_calorie_homepage);
        setContentView(binding.getRoot());
        getSupportActionBar(). hide();

        User_Data = getIntent().getExtras().getStringArrayList("User_Data");

        try{
            binding.TitleLabel.setText("Welcome " + User_Data.get(6));
        }catch(Exception E){
            binding.TitleLabel.setText("Welcome User");
        }

        Log.d(TAG, "Save data to Shared Preference");
        Log.d(TAG, "tada");
        // Save data to Shared Preference
        SharedPreferences shared_preference = getSharedPreferences("User_Data", Context.MODE_PRIVATE);
        Log.d(TAG, "1");
        SharedPreferences.Editor editor = shared_preference.edit();
        Log.d(TAG, "2");
        //first username, second is Bmr
        Log.d(TAG, "Username = " + User_Data.get(6));
        editor.putString("Age", User_Data.get(0));
        editor.putString("Email", User_Data.get(1));
        editor.putString("Height_Cm", User_Data.get(2));
        editor.putString("Password", User_Data.get(3));
        editor.putString("Bmr", User_Data.get(4));
        editor.putString("Is_male", User_Data.get(5));
        editor.putString("Username", User_Data.get(6));
        editor.putString("Weight_Kg", User_Data.get(7));
        editor.commit();  // or editor.commit() to write immediately
        Log.d(TAG, "done shared pref");

    }

    public void Camera_Button_Pressed(View view) {
        Log.d(TAG, "Camera Button Pressed");
        Intent intent = new Intent(view.getContext(), Camera.class);

        startActivity(intent);
    }

    public void Search_Button_Pressed(View view) {
        Log.d(TAG, "Search Button Pressed");
        Intent intent = new Intent(Calorie_Homepage.this, MainActivity2.class);

        startActivity(intent);
    }

    public void To_Breakfast_Recycler(View view) {


        Intent Lunch_Recycler_Intent =  new Intent(view.getContext(), Lunch_RecyclerView.class);
        Lunch_Recycler_Intent.putExtra("User_Data",User_Data);
        startActivity(Lunch_Recycler_Intent);
        Log.d(TAG, "Lunch Intent Done");

        Intent Dinner_Recycler_Intent =  new Intent(view.getContext(), Dinner_RecyclerView.class);
        Dinner_Recycler_Intent.putExtra("User_Data",User_Data);
        startActivity(Dinner_Recycler_Intent);
        Log.d(TAG, "Dinner Intent Done");

        Intent Breakfast_Recycler_Intent = new Intent(view.getContext(), Breakfast_RecyclerView.class);
        Breakfast_Recycler_Intent.putExtra("User_Data",User_Data);
        startActivity(Breakfast_Recycler_Intent);
        Log.d(TAG, "Breakfast Intent Done");








    }
}