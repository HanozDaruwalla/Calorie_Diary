package com.example.caloriediary;

import android.util.Log;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Food_Item_Adapter_Methods {
    public static final String TAG = "Item_Adapter_Function";


    public static ArrayList<String> Find_Filter_Value(String Filter_Selected, Nutrition_Data_From_Db currentItem, String Value_For_Dyn_1, String Lb_For_Dyn_1){
        switch (Filter_Selected) {
                /*Problems
                1. Name_Of_Food is on the spinner but not here
                2. Serving Size returns N/A
                 */
            case "Calories":
                Log.d(TAG, "Calories Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getCalories();
                Lb_For_Dyn_1 = "Calories:";
                Log.d(TAG, "Set Calories to Dynamic_Textview_1");
                break;
            case "Total Fat":
                Log.d(TAG, "Total Fat Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getFat();
                Lb_For_Dyn_1 = "Total Fat:";
                Log.d(TAG, "Set Total Fat to Dynamic_Textview_1");
                break;
            case "Cholesterol":
                Log.d(TAG, "Cholesterol Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getCholesterol();
                Lb_For_Dyn_1 = "Cholesterol:";
                Log.d(TAG, "Set Cholesterol to Dynamic_Textview_1");
                break;
            case "Sodium":
                Log.d(TAG, "Sodium Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getSodium();
                Lb_For_Dyn_1 = "Sodium:";
                Log.d(TAG, "Set Sodium to Dynamic_Textview_1");
                break;
            case "Carbs":
                Log.d(TAG, "Total Carbohydrate Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getTotal_Carbs();
                Lb_For_Dyn_1 = "Carbs:";
                Log.d(TAG, "Set Total Carbohydrate to Dynamic_Textview_1");
                break;
            case "Sugar":
                Log.d(TAG, "Sugar Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getSugar();
                Lb_For_Dyn_1 = "Sugar:";
                Log.d(TAG, "Set Sugar to Dynamic_Textview_1");
                break;
            case "Dietary Fiber":
                Log.d(TAG, "Dietary Fiber Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getDietary_Fiber();
                Lb_For_Dyn_1 = "Dietary Fiber:";
                Log.d(TAG, "Set Dietary Fiber to Dynamic_Textview_1");
                break;
            case "Potassium":
                Log.d(TAG, "Potassium Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getPotassium();
                Lb_For_Dyn_1 = "Potassium:";
                Log.d(TAG, "Set Potassium to Dynamic_Textview_1");
                break;
            case "Protein":
                Log.d(TAG, "Protein Found For Filter 1");
                Value_For_Dyn_1 = currentItem.getProtein();
                Lb_For_Dyn_1 = "Protein:";
                Log.d(TAG, "Set Protein to Dynamic_Textview_1");
                break;
            default:
                Log.d(TAG, "Incorrect Filter 1 Selected: " + Filter_Selected);
                Value_For_Dyn_1 = "N/A";
                Lb_For_Dyn_1 = "Unknown:";
        }
        ArrayList<String> Return_Values = new ArrayList<>();
        Return_Values.add(Value_For_Dyn_1);
        Return_Values.add(Lb_For_Dyn_1);
        return Return_Values;
    }
}
