package com.example.caloriediary;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.caloriediary.databinding.ActivityEnterWeightBinding;

import java.util.ArrayList;

public class Enter_Weight extends AppCompatActivity {

    private ActivityEnterWeightBinding binding;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "Enter_Weight_Section";
    ArrayList<String> Data_For_Bmr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterWeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_enter_weight);
        setContentView(binding.getRoot());
        Data_For_Bmr = getIntent().getExtras().getStringArrayList("Data_For_Bmr");

        Log.d(TAG, "Loaded");
    }

    public void Not_Null_Checks(View view) {
        boolean measurementInStone = Is_Stone_Selected(view);
        String New_Weight_In_Kg = "";
        Log.d(TAG, "Measurement Saved = " + measurementInStone);

        String weightInput = binding.WeightInput.getText().toString();
        Log.d(TAG, "Checking If Weight Valid");

        if (weightInput.isEmpty()) {
            Log.d(TAG, "Weight Empty");
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");
        } else if (measurementInStone) { //Stone Checks
            Log.d(TAG, "Weight In Stone Found at Not Null Checks");
            if (((weightInput.charAt(1) == '.' || weightInput.charAt(2) == '.') && weightInput.length()<6)){

                Log.d(TAG, "Valid Stone Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Stone = " + weightInput);

                New_Weight_In_Kg = Stone_To_Kg(weightInput);
                Pack_Data_To_Arraylist_For_Bmr(New_Weight_In_Kg, view);
            }else{
                Log.d(TAG, "Invalid Stone Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your weight Correctly in Stone. e.g., 11.9 or 11.0");
            }
        }else{// Kg CHECKS
            Log.d(TAG, "Weight In Kg Found at Not Null Checks");
            if ((weightInput.charAt(1) == '.' || weightInput.charAt(2) == '.') || weightInput.length()<6 && weightInput.charAt(3) == '.'){ //its 6 so u allow weight like 100kg
                //if condition met

                Log.d(TAG, "Valid Weight Kg Entry");
                //New_Weight_In_Kg = Stone_To_Kg(weightInput);
                Pack_Data_To_Arraylist_For_Bmr(weightInput, view);
            } else {
                Log.d(TAG, "Invalid Weight Kg Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your weight Correctly in Kg. e.g, 67.5");
            }
        }
    }

    private String Stone_To_Kg(String weightInput){
        int Num1, Num2;
        float weightInStone, weightInKg;
        String formattedKg = "";

        Log.d(TAG, "Starting Calcs");

        Log.d(TAG, "String Weight = " + weightInput);
        //seperates the string to an int from the char "."
        String[] weight_Array = weightInput.split("\\.");

        Num1 = reusableFunctions.To_Int(weight_Array[0]);
        Num2 = reusableFunctions.To_Int(weight_Array[1]);

        try {
            weightInStone = Float.parseFloat(weightInput);
            // 1 stone is approximately equal to 6.35029 kilograms
            weightInKg = weightInStone * 6.35f;
            formattedKg = reusableFunctions.Decimal_Place_2(weightInKg);
            Log.d(TAG, "Kg = " + formattedKg);

            reusableFunctions.Create_Toast(getApplicationContext(), "Converted Kg = " + formattedKg);
            return formattedKg;

        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid input: " + weightInput);
            reusableFunctions.Create_Toast(getApplicationContext(), "Invalid input. Please enter a valid number.");
        }

        return "Error in Stone_To_Kg Convert";


    }

    private void Pack_Data_To_Arraylist_For_Bmr(String Weight, View view){
        // 0 = age 1 = isMale 2 = height 3 = weight
        Data_For_Bmr.add(Weight);
        Calculate_Bmr_From_Class(Data_For_Bmr, view);
    }

    private boolean Is_Stone_Selected(View view) {
        int clickedColor = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);

        ColorStateList currentColorStateList = binding.StoneButton.getBackgroundTintList();
        int currentColor = currentColorStateList.getDefaultColor();

        if (currentColor == clickedColor) {
            //Stone_Inches_To_Kg();
            return true;
        } else {
            Log.d(TAG, "Weight Entered In Kg");
            return false;
        }
    }

    private void Calculate_Bmr_From_Class(ArrayList<String> dataForBmr, View view) {
        String Bmr = "";
        Bmr_Calcs bmr_calcs = new Bmr_Calcs();

        Bmr = bmr_calcs.calculateBMR(dataForBmr, view);
        Log.d(TAG, "Bmr = " + Bmr);
        To_Calorie_Homepage();

    }

    private void To_Calorie_Homepage() {
        //This needs to be replaced with database to data data to db
        Intent pageMovementIntent = new Intent(Enter_Weight.this, Calorie_Homepage.class);
        //pageMovementIntent.putExtra("Data_For_Bmr", dataForBmr);

        startActivity(pageMovementIntent);
    }


    //----------------------------------------------------------------------//
    //---------------------------------- UI --------------------------------//
    //----------------------------------------------------------------------//

    public void Stone_Button_Pressed(View view) {
        binding.WeightInput.setText("");
        binding.WeightInput.setHint("Enter Weight In Stone");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);

        binding.StoneButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.KgButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));

    }

    public void Kg_Button_Pressed(View view) {
        binding.WeightInput.setText("");
        binding.WeightInput.setHint("Enter Weight In Kg");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);

        binding.KgButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.StoneButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));

    }
        public void Add_Button_Pressed(View view) {
        Not_Null_Checks(view);
    }
}

