package com.example.caloriediary;

import android.widget.Toast;
import android.content.Context;

public class ReusableFunctions {

    public void Create_Toast(Context context, String Toast_Msg) {
        Toast toast = Toast.makeText(context, Toast_Msg, Toast.LENGTH_SHORT);//was "LONG"
        //toast.setGravity(Gravity.CENTER_VERTICAL,0,250);
        toast.show();
    }

    public String Decimal_Place_2(double number){
        String formatted_decimal = String.format("%.2f", number);

        return formatted_decimal;
    }

    public double To_Double(String Imported_String) {
        try {
            double doubleNumber = Double.parseDouble(Imported_String);
            return doubleNumber;
        } catch (NumberFormatException e) {
            System.out.println("Invalid double value: " + Imported_String);
            e.printStackTrace();
            return -0.0;

        }
    }

    public int To_Int(String Imported_String) {
        try {
            int intNumber = Integer.parseInt(Imported_String);
            return intNumber;
        } catch (NumberFormatException e) {
            System.out.println("Invalid double value: " + Imported_String);
            e.printStackTrace();
            return 0;

        }
    }

    public float To_Float(String Imported_String) {
        try {
            float floatValue = Float.parseFloat(Imported_String);
            return floatValue;
        } catch (NumberFormatException e) {
            System.out.println("Invalid float value: " + Imported_String);
            e.printStackTrace();
            return 0.0f; // or throw an exception if appropriate
        }
    }


    public String doubleToString(double doubleValue) {
        return String.valueOf(doubleValue);
    }

    // Convert integer to string in Java

    public static String intToString(int number) {
        // Convert the integer to a string
        return String.valueOf(number);
    }

    public boolean Set_Gender(String string){
        if(string.equals("true")){
            return true;
        }else{
            return false;
        }
    }




}
