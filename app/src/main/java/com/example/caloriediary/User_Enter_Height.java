package com.example.caloriediary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityUserEnterHeightBinding;

public class User_Enter_Height extends AppCompatActivity {

    private ActivityUserEnterHeightBinding binding;
    Double Value = 0.0;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "ExtraInfoPickup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserEnterHeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_enter_height);
        setContentView(binding.getRoot());
        Log.d(TAG, "Loaded");
    }

    public void feetInchesToCm(int feet, int inches) {
        // 1 foot = 30.48 centimeters
        // 1 inch = 2.54 centimeters
        int totalInches = feet * 12 + inches;
        double cm = totalInches * 2.54;
        String formattedCm = String.format("%.2f", cm);
        Log.d(TAG, "Cm = " + formattedCm);
    }

    public void Add_Button_Pressed(View view) {

        Log.d(TAG, "YASS");

        //String height_Inputted_By_User = "Undefined";
        //height_Inputted_By_User = binding.HeightInput.getText().toString();

        Log.d(TAG, "YASS2");
        Log.d(TAG, "String = " + binding.HeightInput.getText().toString());
        //String[] height_Array = binding.HeightInput.getText().toString().split(".");
        String[] height_Array = binding.HeightInput.getText().toString().split("\\.");

        Log.d("TAG", "after rejex");

        int Foot = reusableFunctions.To_Int(height_Array[0]);
        int Inches = reusableFunctions.To_Int(height_Array[1]);

        Log.d("TAG", "after type cast");

        Log.d(TAG, "Foot = " + Foot);
        Log.d(TAG, "Inches = " + Inches);

        reusableFunctions.Create_Toast(getApplicationContext(), "Wahoo");
        feetInchesToCm(Foot, Inches);


        /*
        Log.d(TAG, "Button Clicked");
        reusableFunctions.Create_Toast(getApplicationContext(), "Wahoo");

        int Foot = reusableFunctions.To_Int(binding.HeightFootInput.getText().toString());
        int Inches = reusableFunctions.To_Int(binding.HeightInchesInput.getText().toString());
        Log.d(TAG, "Foot = " + Foot);
        Log.d(TAG, "Inches = " + Inches);
        feetInchesToCm(Foot, Inches);

         */
    }
}