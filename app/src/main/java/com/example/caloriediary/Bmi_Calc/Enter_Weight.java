package com.example.caloriediary.Bmi_Calc;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.caloriediary.Calorie_Homepage;
import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityEnterWeightBinding;

import java.util.ArrayList;

public class Enter_Weight extends AppCompatActivity {

    private ActivityEnterWeightBinding binding;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "Enter_Weight_Section";
    ArrayList<String> Data_For_Bmr, User_Data = new ArrayList<>();

    boolean Is_Kg_Button_Pressed = false;
    boolean Is_Stone_Button_Pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterWeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_enter_weight);
        setContentView(binding.getRoot());
        Data_For_Bmr = getIntent().getExtras().getStringArrayList("Data_For_Bmr");
        User_Data = getIntent().getExtras().getStringArrayList("User_Data");

        Log.d(TAG, "Loaded");
    }

    public void Not_Null_Checks(View view) {
        boolean Measurement_In_Stone = Is_Stone_Selected(view);
        String New_Weight_In_Kg = "";
        Log.d(TAG, "Measurement Saved = " + Measurement_In_Stone);

        String Weight_Input = binding.WeightInput.getText().toString();
        Log.d(TAG, "Checking If Weight Valid");

        try{//if weight is full number instead of decimal
            if(!(Weight_Input.contains("."))){
                Log.d(TAG, "Added decimal to weight");
                Weight_Input = Weight_Input + ".0";

            }else{
                Log.d(TAG, "decimal already in weight");
            }

        }catch(NumberFormatException e){// was index out of bounds exception
            Log.d(TAG, "Decimal check exception caught");
        }

        if (Weight_Input.isEmpty()) {
            Log.d(TAG, "Weight Empty");
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");
        } else if (Measurement_In_Stone) { //Stone Checks
            Log.d(TAG, "Weight In Stone Found at Not Null Checks");
            try{//If Weight is correct format for stone. then convert to KG. then put to arraylst
                if (((Weight_Input.charAt(1) == '.' || Weight_Input.charAt(2) == '.') && Weight_Input.length()<6)){

                    Log.d(TAG, "Valid Stone Entry");

                    New_Weight_In_Kg = Stone_To_Kg(Weight_Input);
                    Pack_Data_To_Arraylist_For_Bmr(New_Weight_In_Kg, view);
                }else{
                    Log.d(TAG, "Invalid Stone Entry");
                    reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your weight Correctly in Stone. e.g., 11.9 or 11.0");
                }
            }catch(Exception E){
                Weight_Input = Weight_Input + .0;
            }
        }else{// Kg CHECKS
            Log.d(TAG, "Weight In Kg Found at Not Null Checks");
            //checks if weight in KG is correct format. if it is. put in arraylisr
            if ((Weight_Input.charAt(1) == '.' || Weight_Input.charAt(2) == '.') || Weight_Input.length()<6 && Weight_Input.charAt(3) == '.'){ //its 6 so u allow weight like 100kg
                //if condition met

                Log.d(TAG, "Valid Weight Kg Entry");
                //New_Weight_In_Kg = Stone_To_Kg(Weight_Input);
                Pack_Data_To_Arraylist_For_Bmr(Weight_Input, view);
            } else {
                Log.d(TAG, "Invalid Weight Kg Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your weight Correctly in Kg. e.g, 67.5");
            }
        }
    }

    //Convert Stone To Kg (Kg needed for Bmr)
    private String Stone_To_Kg(String Weight_Input){
        int Num1, Num2;
        float Weight_In_Stone, Weight_In_Kg;
        String Formatted_Kg = "";

        Log.d(TAG, "Starting Calcs");

        Log.d(TAG, "String Weight = " + Weight_Input);
        //seperates the string to an int from the char "."
        String[] Weight_Array = Weight_Input.split("\\.");

        Num1 = reusableFunctions.To_Int(Weight_Array[0]);
        Num2 = reusableFunctions.To_Int(Weight_Array[1]);

        try {
            Weight_In_Stone = Float.parseFloat(Weight_Input);

            // 1 stone is approximately equal to 6.35029 kilograms
            Weight_In_Kg = Weight_In_Stone * 6.35f;
            Formatted_Kg = reusableFunctions.Two_Decimal_Place(Weight_In_Kg);
            Log.d(TAG, "Kg = " + Formatted_Kg);

            return Formatted_Kg;

        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid input: " + Weight_Input);
            reusableFunctions.Create_Toast(getApplicationContext(), "Invalid input. Please enter a valid number.");
        }

        return "Error in Stone_To_Kg Convert";
    }

    private void Pack_Data_To_Arraylist_For_Bmr(String Weight, View view){
        // 0 = age 1 = isMale 2 = height 3 = weight
        //Adds weight to the Arraylist
        Data_For_Bmr.add(Weight);
        Calculate_Bmr_From_Class(Data_For_Bmr, view);
    }

    private boolean Is_Stone_Selected(View view) {
        //checks if stone is selected

        //Craetes default vars of colors (Resource/ interface)
        int Resource_Interface_Color_Clicked_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        ColorStateList Stone_Button_Current_Color = binding.StoneButton.getBackgroundTintList();
        int Int_Stone_Button_Current_Color = Stone_Button_Current_Color.getDefaultColor();

        //if Foot button is colored. then true, else false.
        if (Int_Stone_Button_Current_Color == Resource_Interface_Color_Clicked_Color) {
            Stone_To_Kg(binding.WeightInput.getText().toString());
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
        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Bmr, Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)
        User_Data.set(4, Bmr);

        To_Calorie_Homepage();

    }

    private void To_Calorie_Homepage() {
        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Bmr, Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)

        Intent Page_Movement_Intent = new Intent(Enter_Weight.this, Calorie_Homepage.class);

        Log.d(TAG, "Size = " + User_Data.size());
        Page_Movement_Intent.putExtra("User_Data", User_Data);

        startActivity(Page_Movement_Intent);
    }


    //----------------------------------------------------------------------//
    //---------------------------------- UI --------------------------------//
    //----------------------------------------------------------------------//

    public void Stone_Button_Pressed(View view) {
        binding.WeightInput.setText("");
        binding.WeightInput.setHint("Enter Weight In Stone");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);

        Is_Kg_Button_Pressed = false;
        Is_Stone_Button_Pressed = true;

        binding.StoneButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.KgButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));
    }

    public void Kg_Button_Pressed(View view) {
        binding.WeightInput.setText("");
        binding.WeightInput.setHint("Enter Weight In Kg");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);

        Is_Kg_Button_Pressed = true;
        Is_Stone_Button_Pressed = false;

        binding.KgButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.StoneButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));
    }

    public void Add_Button_Pressed(View view) {
        if(Is_Kg_Button_Pressed == false && Is_Stone_Button_Pressed == false){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Choose Kg Or Stones");
        }else{
            Not_Null_Checks(view);
        }
    }
}