package com.example.caloriediary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityCalorieHomepageBinding;

import java.util.ArrayList;

public class Calorie_Homepage extends AppCompatActivity {

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
}