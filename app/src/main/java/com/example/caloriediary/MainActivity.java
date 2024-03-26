package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    Intent Page_Movement_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
    }

    public void GetStarted_Clicked(View view) {
        Page_Movement_Intent = new Intent(view.getContext(), Create_Account.class);
        startActivity(Page_Movement_Intent);
    }

}