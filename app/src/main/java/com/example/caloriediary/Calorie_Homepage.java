package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Api_Refactored.Camera;
import com.example.caloriediary.Api_Refactored.MainActivity2;
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

        //User_Data = getIntent().getExtras().getStringArrayList("User_Data");
        //binding.TitleLabel.setText("Welcome " + username );
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
}