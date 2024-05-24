package com.example.caloriediary.Bmi_Calc;

import android.util.Log;
import android.view.View;

import com.example.caloriediary.Database.Database;
import com.example.caloriediary.ReusableFunctions;

import java.util.ArrayList;

public class Bmr_Calcs {

    private static final String TAG = "Bmr_Calc";

    public static String calculateBMR(ArrayList<String> bmr_data, View view) {

        ReusableFunctions reusableFunctions = new ReusableFunctions();

        //Values For Formula
        final double Male_Constant_Value = 88.362;
        final double Female_Constant_Value = 447.593;
        final double Weight_Multplier = 13.397;
        final double Height_Multiplier = 4.799;
        final double Age_Multiplier = 5.677;

        double Bmr_Value;
        int Age = -1;
        boolean Is_Male;
        double Height_In_Cm, Weight_In_Kg = -0.0;

        Age = reusableFunctions.To_Int(bmr_data.get(0));
        Is_Male = reusableFunctions.String_To_Bool(bmr_data.get(1));
        Height_In_Cm = reusableFunctions.String_To_Double(bmr_data.get(2));
        Weight_In_Kg = reusableFunctions.String_To_Double(bmr_data.get(3));

        // -------------------------- Harris-Benedict equation ---------------------------------
        if (Is_Male) {
            Bmr_Value = Male_Constant_Value + (Weight_Multplier * Weight_In_Kg) + (Height_Multiplier * Height_In_Cm) - (Age_Multiplier * Age);
        } else {
            Bmr_Value = Female_Constant_Value + (9.247 * Weight_In_Kg) + (3.098 * Height_In_Cm) - (4.330 * Age);
        }

        String Formatted_Bmr = reusableFunctions.Two_Decimal_Place(Bmr_Value);
        Log.d(TAG, "BMR Calculation: Age = " + Age + ", Is_Male = " + Is_Male + ", Height = " + Height_In_Cm + ", Weight = " + Weight_In_Kg + ", BMR = " + Formatted_Bmr);
        reusableFunctions.Create_Toast(view.getContext(), "BMR = " + Formatted_Bmr);

        Database db = new Database();
        //db.Edit_User_Data("Tester");
        return Formatted_Bmr;
    }
}
