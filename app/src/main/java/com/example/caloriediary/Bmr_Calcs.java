package com.example.caloriediary;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Bmr_Calcs {

    private static final String TAG = "Bmr_Calc";

    public static String calculateBMR(ArrayList<String> bmr_data, View view) {

        ReusableFunctions reusableFunctions = new ReusableFunctions();

        //Values For Formula
        final double MALE_CONST = 88.362;
        final double FEMALE_CONST = 447.593;
        final double WEIGHT_MULT = 13.397;
        final double HEIGHT_MULT = 4.799;
        final double AGE_MULT = 5.677;

        double bmr;
        int age = -1; //default value
        boolean Is_Male;
        double Height_In_Cm, Weight_In_Kg = -0.0;

        age = reusableFunctions.To_Int(bmr_data.get(0));
        Is_Male = reusableFunctions.String_To_Bool(bmr_data.get(1));
        Height_In_Cm = reusableFunctions.String_To_Double(bmr_data.get(2));
        Weight_In_Kg = reusableFunctions.String_To_Double(bmr_data.get(3));

        // -------------------------- Harris-Benedict equation ---------------------------------
        if (Is_Male) {
            bmr = MALE_CONST + (WEIGHT_MULT * Weight_In_Kg) + (HEIGHT_MULT * Height_In_Cm) - (AGE_MULT * age);
        } else {
            bmr = FEMALE_CONST + (9.247 * Weight_In_Kg) + (3.098 * Height_In_Cm) - (4.330 * age);
        }

        String Formatted_Bmr = reusableFunctions.Decimal_Place_2(bmr);
        Log.d(TAG, "BMR Calculation: Age = " + age + ", Is_Male = " + Is_Male + ", Height = " + Height_In_Cm + ", Weight = " + Weight_In_Kg + ", BMR = " + Formatted_Bmr);
        reusableFunctions.Create_Toast(view.getContext(), "BMR = " + Formatted_Bmr);

        Database db = new Database();
        //db.Edit_User_Data("Tester");
        return Formatted_Bmr;
    }
}
