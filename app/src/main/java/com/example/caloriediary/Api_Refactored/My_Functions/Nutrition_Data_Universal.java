package com.example.caloriediary.Api_Refactored.My_Functions;

public class Nutrition_Data_Universal {

    public static String Serving_Size_Maker(String servingQty, String servingUnit, String servingWeight){
        String servingSize;
        servingSize = servingQty + " " + servingUnit + "(" + servingWeight + "g" + ")";
        return servingSize;
    }
}
