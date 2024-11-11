package com.example.caloriediary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Api_Refactored.Camera;
import com.example.caloriediary.Api_Refactored.MainActivity2;
import com.example.caloriediary.Creating_Account_And_Login.User;
import com.example.caloriediary.RecyclerView.recyclerview;
import com.example.caloriediary.databinding.ActivityCalorieHomepageBinding;

import java.util.ArrayList;


public class Calorie_Homepage extends AppCompatActivity {

    final static String TAG = "Calorie_Homepage";

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

    }

    public void Camera_Button_Pressed(View view) {
        Log.d(TAG, "Camera Button Pressed");
        Intent intent = new Intent(view.getContext(), Camera.class);

        startActivity(intent);
    }

    public void Search_Button_Pressed(View view) {
        Log.d(TAG, "Search Button Pressed");
        Intent intent = new Intent(Calorie_Homepage.this, MainActivity2.class);

        Log.d(TAG, "Save data to Shared Preference");

        // Save data to Shared Preference
        SharedPreferences shared_preference = getSharedPreferences("User_Data", Context.MODE_PRIVATE);
        Log.d(TAG, "1");
        SharedPreferences.Editor editor = shared_preference.edit();
        Log.d(TAG, "2");
        //first username, second is Bmr
        Log.d(TAG, "Username = " + User_Data.get(6));
        editor.putString("Data", User_Data.get(6) + "," +  User_Data.get(5));
        Log.d(TAG,  "5");
        editor.commit();  // or editor.commit() to write immediately
        Log.d(TAG, "4");

        startActivity(intent);
    }

    public void To_Breakfast_Recycler(View view) {
        Intent intent = new Intent(view.getContext(), recyclerview.class);
        intent.putExtra("User_Data",User_Data);
        startActivity(intent);
    }
}