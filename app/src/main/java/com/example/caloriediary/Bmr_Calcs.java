package com.example.caloriediary;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class Bmr_Calcs {

    private static final String TAG = "Bmr_Calc";

    public static String calculateBMR(ArrayList<String> bmr_data, View view) {

        ReusableFunctions reusableFunctions = new ReusableFunctions();

        final double MALE_CONST = 88.362;
        final double FEMALE_CONST = 447.593;
        final double WEIGHT_MULT = 13.397;
        final double HEIGHT_MULT = 4.799;
        final double AGE_MULT = 5.677;

        int age = reusableFunctions.To_Int(bmr_data.get(0));
        boolean isMale = reusableFunctions.String_To_Bool(bmr_data.get(1));
        double HeightInCm = reusableFunctions.String_To_Double(bmr_data.get(2));
        double WeightInKg = reusableFunctions.String_To_Double(bmr_data.get(3));

        double bmr;

        // -------------------------- Harris-Benedict equation ---------------------------------
        if (isMale) {
            bmr = MALE_CONST + (WEIGHT_MULT * WeightInKg) + (HEIGHT_MULT * HeightInCm) - (AGE_MULT * age);
        } else {
            bmr = FEMALE_CONST + (9.247 * WeightInKg) + (3.098 * HeightInCm) - (4.330 * age);
        }

        String formattedBMR = reusableFunctions.Decimal_Place_2(bmr);
        Log.d(TAG, "BMR Calculation: Age = " + age + ", isMale = " + isMale + ", Height = " + HeightInCm + ", Weight = " + WeightInKg + ", BMR = " + formattedBMR);
        reusableFunctions.Create_Toast(view.getContext(), "BMR = " + formattedBMR);

        return formattedBMR;
    }
}
