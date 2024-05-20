package com.example.caloriediary;

import android.widget.Toast;
import android.content.Context;

public class ReusableFunctions {

    public void Create_Toast(Context context, String Toast_Msg) {
        Toast toast = Toast.makeText(context, Toast_Msg, Toast.LENGTH_SHORT);//was "LONG"
        //toast.setGravity(Gravity.CENTER_VERTICAL,0,250);
        toast.show();
    }

    public String Two_Decimal_Place(double Decimal_Number){
        String Formatted_Decimal_Number = String.format("%.2f", Decimal_Number);

        return Formatted_Decimal_Number;
    }

    public double String_To_Double(String Imported_String) {
        try {
            double doubleNumber = Double.parseDouble(Imported_String);
            return doubleNumber;
        } catch (NumberFormatException e) {
            System.out.println("Invalid double value: " + Imported_String);
            return -0.0;

        }
    }

    public int To_Int(String Imported_String) {
        try {
            int Int_Number = Integer.parseInt(Imported_String);
            return Int_Number;
        } catch (NumberFormatException e) {
            System.out.println("Invalid Value " + Imported_String);
            return 0;

        }
    }

    public float To_Float(String Imported_String) {
        try {
            float Float_Number = Float.parseFloat(Imported_String);
            return Float_Number;
        } catch (NumberFormatException e) {
            System.out.println("Invalid float value: " + Imported_String);
            return 0.0f; // or throw an exception if appropriate
        }
    }


    public String Double_To_String(double doubleValue) {
        return String.valueOf(doubleValue);
    }

    // Convert integer to string in Java

    public static String Int_To_Strng(int number) {
        // Convert the integer to a string
        return String.valueOf(number);
    }

    public boolean String_To_Bool(String string){
        if(string.equals("true")){
            return true;
        }else{
            return false;
        }
    }



}
