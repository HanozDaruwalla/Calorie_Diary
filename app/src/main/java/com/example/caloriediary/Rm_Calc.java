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
    ArrayList<String> User_Data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserEnterHeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_enter_height);
        setContentView(binding.getRoot());
        User_Data = getIntent().getExtras().getStringArrayList("User_Data");
        Log.d(TAG, "Loaded");
    }

    public void Not_Null_Checks(){
        if(binding.HeightInput.getText().toString().equals("") || binding.WeightInput.getText().toString().equals("")){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");
        }else{
            //start calcs
            Rmi_Preparation();
        }
    }

    private void Rmi_Preparation(){

        int Foot, Inches;
        float heightInCm, weightInKg = 0.0f;

        reusableFunctions.Create_Toast(getApplicationContext(), "Starting calcs");

        Log.d(TAG, "String Height = " + binding.HeightInput.getText().toString());
        String[] height_Array = binding.HeightInput.getText().toString().split("\\.");

        Log.d("TAG", "after rejex");
        Foot = reusableFunctions.To_Int(height_Array[0]);
        Inches = reusableFunctions.To_Int(height_Array[1]);
        Log.d("TAG", "after type cast");

        Log.d(TAG, "Foot = " + Foot);
        Log.d(TAG, "Inches = " + Inches);

        //Variables to be passed

        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Resting Metabolic Rate (RMR), Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)

        weightInKg = reusableFunctions.To_Float(binding.WeightInput.getText().toString());
        heightInCm = reusableFunctions.To_Float(feetInchesToCm(Foot, Inches));
        int Age = reusableFunctions.To_Int(User_Data.get(0));
        boolean isMale = reusableFunctions.Set_Gender(User_Data.get(5));

        calculateBMR(weightInKg,heightInCm, Age, isMale);
    }

    public String calculateBMR(double weightInKg, double heightInCm, int age, boolean isMale) {
        final double MALE_CONST = 10;
        final double FEMALE_CONST = 161;
        final double WEIGHT_MULT = 6.25;
        final double HEIGHT_MULT = 5;
        final double AGE_MULT = 5;

        String Formatted_bmr = "";

        double bmr;
        if (isMale == true) {
            bmr = (MALE_CONST * weightInKg) + (WEIGHT_MULT * heightInCm) - (AGE_MULT * age) + FEMALE_CONST;
        } else {
            bmr = (MALE_CONST * weightInKg) + (WEIGHT_MULT * heightInCm) - (AGE_MULT * age) - FEMALE_CONST;
        }
        Formatted_bmr = reusableFunctions.Decimal_Place_2(bmr);
        Log.d(TAG, "bmr = " + Formatted_bmr);
        reusableFunctions.Create_Toast(getApplicationContext(), "BMR = " + Formatted_bmr);
        return Formatted_bmr;
    }

    public String feetInchesToCm(int feet, int inches) {
        // 1 foot = 30.48 centimeters
        // 1 inch = 2.54 centimeters
        int totalInches = feet * 12 + inches;
        float cm = totalInches * 2.54f;
        String formattedCm = reusableFunctions.Decimal_Place_2(cm);
        Log.d(TAG, "Cm = " + formattedCm);

        reusableFunctions.Create_Toast(getApplicationContext(), "Cm = " + formattedCm);
        return formattedCm;
    }

    public void Add_Button_Pressed(View view) {
        Not_Null_Checks();
    }



}