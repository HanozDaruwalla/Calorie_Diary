/*
package com.example.caloriediary;


import android.util.Log;
import android.view.View;

public class BmrTemplate {
    //ADD THIS TO ACTIVITY LATER
    private void Bmr_Preparation(View view){

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

        heightInCm = reusableFunctions.To_Float(feetInchesToCm(Foot, Inches));
        int Age = reusableFunctions.To_Int(User_Data.get(0));
        boolean isMale = reusableFunctions.Set_Gender(User_Data.get(5));

        Feet_Selected(view);
        //calculateBMR(weightInKg,heightInCm, Age, isMale);
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

}

 */
