package com.example.caloriediary;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.caloriediary.databinding.ActivityUserEnterHeightBinding;

import java.util.ArrayList;

public class Enter_Height extends AppCompatActivity {

    private ActivityUserEnterHeightBinding binding;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "Enter_Height_Section";
    ArrayList<String> User_Data = new ArrayList<>();
    boolean Is_Cm_Pressed = false;
    boolean Is_Inches_Pressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserEnterHeightBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_user_enter_height);
        setContentView(binding.getRoot());
        User_Data = getIntent().getExtras().getStringArrayList("User_Data");

        Log.d(TAG, "Loaded");
    }

    public void Not_Null_Checks(View view) {
        boolean measurementInFoot = Is_Feet_Selected(view);
        String Cm_Height = "";
        Log.d(TAG, "Measurement Saved = " + measurementInFoot);

        String heightInput = binding.HeightInput.getText().toString();

        Log.d(TAG, "Checking If Height Valid");

        if (heightInput.isEmpty()) {
            Log.d(TAG, "Height Empty");
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");
        } else if (measurementInFoot) { //Foot Checks
            Log.d(TAG, "Height In Foot Found at Not Null Checks");
            if (heightInput.length() == 1 || heightInput.charAt(1) == '.'){
                //if condition met

                Log.d(TAG, "Valid Height Foot Entry");
                Cm_Height = Foot_Inches_To_Cm(heightInput);
                Pack_Data_To_Arraylist_For_Bmr(Cm_Height, view);
            } else {
                Log.d(TAG, "Invalid Height Foot Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your Height Correctly in Feet. e.g., 5.8 or '5.0'");
            }
        }else{// CM CHECKS
            if (!(heightInput.length() > 4 && heightInput.charAt(3) == '.') && !(heightInput.length() == 3)) {
                Log.d(TAG, "Invalid Cm Entry");
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your Height Correctly in Cm. e.g., 170.78 or 170");
            }else{
                Log.d(TAG, "Valid Cm Entry");
                Pack_Data_To_Arraylist_For_Bmr(heightInput, view);
            }
        }
    }




    private String Foot_Inches_To_Cm(String heightInput){
        int Foot, Inches;
        float heightInCm;

        Log.d(TAG, "Starting Calcs");

        Log.d(TAG, "String Height = " + heightInput);
        //seperates the string to an int from the char "."
        String[] height_Array = heightInput.split("\\.");

        Foot = reusableFunctions.To_Int(height_Array[0]);
        Inches = reusableFunctions.To_Int(height_Array[1]);

        // 1 foot = 30.48 centimeters
        // 1 inch = 2.54 centimeters
        int totalInches = Foot * 12 + Inches;
        heightInCm = totalInches * 2.54f;
        String formattedCm = reusableFunctions.Decimal_Place_2(heightInCm);
        Log.d(TAG, "Cm = " + formattedCm);

        reusableFunctions.Create_Toast(getApplicationContext(), "Cm = " + formattedCm);
        return formattedCm;
    }

    private void Pack_Data_To_Arraylist_For_Bmr(String Height, View view){
        //Variables to be passed

        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Resting Metabolic Rate (RMR), Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)

        ArrayList<String> Data_For_Bmr = new ArrayList<String>();

        int Age = reusableFunctions.To_Int(User_Data.get(0));
        boolean isMale = reusableFunctions.String_To_Bool(User_Data.get(5));

        Data_For_Bmr.add(ReusableFunctions.intToString(Age));
        Data_For_Bmr.add(String.valueOf(isMale));
        Data_For_Bmr.add(Height);

        To_Enter_Weight(Data_For_Bmr);
    }

    private boolean Is_Feet_Selected(View view) {
        int clickedColor = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);

        ColorStateList currentColorStateList = binding.FootButton.getBackgroundTintList();
        int currentColor = currentColorStateList.getDefaultColor();

        if (currentColor == clickedColor) {
            //Foot_Inches_To_Cm();
            return true;
        } else {
            Log.d(TAG, "Height Entered In Cm");
            return false;
        }
    }

    private void To_Enter_Weight(ArrayList<String> dataForBmr) {
        Intent pageMovementIntent = new Intent(Enter_Height.this, Enter_Weight.class);
        pageMovementIntent.putExtra("Data_For_Bmr", dataForBmr);

        reusableFunctions.Create_Toast(getApplicationContext(), "Cm Class Complete");
        startActivity(pageMovementIntent);
    }

    //----------------------------------------------------------------------//
    //---------------------------------- UI --------------------------------//
    //----------------------------------------------------------------------//

    public void Foot_Button_Pressed(View view) {
        binding.HeightInput.setText("");
        binding.HeightInput.setHint("Enter Height In Foot");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);

        Is_Cm_Pressed = false;
        Is_Inches_Pressed = true;
        binding.FootButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.CmButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));
    }

    public void Cm_Button_Pressed(View view) {
        binding.HeightInput.setText("");
        binding.HeightInput.setHint("Enter Height In Cm");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);


        Is_Cm_Pressed = true;
        Is_Inches_Pressed = false;
        binding.CmButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color_Clicked));
        binding.FootButton.setBackgroundTintList(ColorStateList.valueOf(Resource_Interface_Color));


    }

    public void Add_Button_Pressed(View view) {
        if(Is_Cm_Pressed == false && Is_Inches_Pressed == false){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Choose Cm Or Inches");
        }else{
            Not_Null_Checks(view);
        }
    }








}