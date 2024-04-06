package com.example.caloriediary;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityUserEnterHeightBinding;

import java.util.ArrayList;

public class Rm_Calc extends AppCompatActivity {

    private ActivityUserEnterHeightBinding binding;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "ExtraInfoPickup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserEnterHeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_enter_height);
        setContentView(binding.getRoot());

        ArrayList<String> User_Data = new ArrayList<>();
        User_Data = getIntent().getExtras().getStringArrayList("User_Data");
        Log.d(TAG, "Loaded");
    }

    public void Not_Null_Checks(){
        if(binding.HeightInput.getText().toString().equals("") || binding.WeightInput.getText().toString().equals("")){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");

        }else{
            //start calcs
            Rmi_Calcs();
        }
    }

    private void Rmi_Calcs(){
            reusableFunctions.Create_Toast(getApplicationContext(), "Starting calcs");

        Log.d(TAG, "String = " + binding.HeightInput.getText().toString());
        String[] height_Array = binding.HeightInput.getText().toString().split("\\.");

        Log.d("TAG", "after rejex");
        int Foot = reusableFunctions.To_Int(height_Array[0]);
        int Inches = reusableFunctions.To_Int(height_Array[1]);

        Log.d("TAG", "after type cast");
        Log.d(TAG, "Foot = " + Foot);
        Log.d(TAG, "Inches = " + Inches);
        feetInchesToCm(Foot, Inches);

    }

    public void feetInchesToCm(int feet, int inches) {
        // 1 foot = 30.48 centimeters
        // 1 inch = 2.54 centimeters
        int totalInches = feet * 12 + inches;
        double cm = totalInches * 2.54;
        String formattedCm = String.format("%.2f", cm);
        Log.d(TAG, "Cm = " + formattedCm);

        reusableFunctions.Create_Toast(getApplicationContext(), "Cm = " + formattedCm);
    }

    public void Add_Button_Pressed(View view) {
        Not_Null_Checks();
    }

}