package com.example.caloriediary.Bmi_Calc;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityUserEnterHeightBinding;

import java.util.ArrayList;

public class Enter_Height extends AppCompatActivity {

    private ActivityUserEnterHeightBinding binding;
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    public static final String TAG = "Enter_Height_Section";
    ArrayList<String> User_Data = new ArrayList<>();
    boolean Is_Cm_Pressed = false;
    boolean Is_Inches_Pressed = false;
    //github testing4
    String Height_Error = "Invalid Height Foot Entry";

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
        boolean Measurement_In_Foot = Is_Feet_Selected(view);
        String Cm_Height = "Not Declared"; //was null
        Log.d(TAG, "Measurement Saved = " + Measurement_In_Foot);

        String Height_Input = binding.HeightInput.getText().toString();
        Log.d(TAG, "Checking If Height Valid");

        try{//if height is full number instead of decimal. turn to decimal
            if(!(Height_Input.contains("."))){
                Log.d(TAG, "Added decimal to Height");
                Height_Input = Height_Input + ".0";
            }else{
                Log.d(TAG, "decimal already in height");
            }
        }catch(NumberFormatException e){// was index out of bounds exception
            Log.d(TAG, "Error in Null_Null_Checks decimal catch");
        }

        if (Height_Input.isEmpty()) {
            Log.d(TAG, "Height Empty");
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter All Data");
        } else if (Measurement_In_Foot) { //Foot Checks
            Log.d(TAG, "Height In Foot Found at Not Null Checks");
            try{
                if ((Height_Input.length() == 1 || Height_Input.charAt(1) == '.') && Extra_Foot_Checks(Height_Input) == true){
                    //if condition met
                    Log.d(TAG, "Valid Height Foot Entry");
                    Cm_Height = Foot_Inches_To_Cm(Height_Input);
                    Pack_Data_To_Arraylist_For_Bmr(Cm_Height, view);
                } else {
                    Log.d(TAG, "Invalid Height Foot Entry");
                    reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your Height Correctly in Feet. e.g., 5.8 or '5.0'");
                }
            }catch(Exception E){
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter A Valid Height Entry");
                binding.HeightInput.setText("");
            }

        }else{// CM CHECKS
            try{
                if (!(Height_Input.length() > 4 && Height_Input.charAt(3) == '.') && !(Height_Input.length() == 3) && Extra_Cm_Checks(Height_Input)) {
                    Log.d(TAG, "Invalid Cm Entry");
                    reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your Height Correctly in Cm. e.g., 170.78 or 170");
                }else{
                    Log.d(TAG, "Valid Cm Entry");
                    Pack_Data_To_Arraylist_For_Bmr(Height_Input, view);
                }
            }catch(Exception E){
                reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter A Valid Weight Entry");
                binding.HeightInput.setText("");
            }
        }
    }

    private boolean Extra_Foot_Checks(String user_input){
        try {
            double Double_Datatype_Height = Double.parseDouble(user_input);
            return Double_Datatype_Height >= 1.0 && Double_Datatype_Height <= 8.0;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid foot height format", e);
            return false;
        }
    }

    private boolean Extra_Cm_Checks(String user_input) {
        try {
            double Double_Datatype_Height = Double.parseDouble(user_input);
            return Double_Datatype_Height >= 100.0 && Double_Datatype_Height <= 250.0;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Invalid cm height format", e);
            return false;
        }
    }


    private String Foot_Inches_To_Cm(String Passed_Height_Input){
        //converts FOOT to CM
        int Foot, Inches;
        float Height_In_Cm;

        Log.d(TAG, "Starting Calcs");

        Log.d(TAG, "String Height = " + Passed_Height_Input);

        //seperates the string to an int from the char "."
        String[] Height_Array = Passed_Height_Input.split("\\.");

        Foot = reusableFunctions.To_Int(Height_Array[0]);
        Inches = reusableFunctions.To_Int(Height_Array[1]);

        // 1 foot = 30.48 centimeters
        // 1 inch = 2.54 centimeters

        //Calculation
        int Calculated_Inches = Foot * 12 + Inches;

        Height_In_Cm = Calculated_Inches * 2.54f;
        String formattedCm = reusableFunctions.Two_Decimal_Place(Height_In_Cm);

        Log.d(TAG, "Cm = " + formattedCm);

        reusableFunctions.Create_Toast(getApplicationContext(), "Cm = " + formattedCm);
        return formattedCm;
    }

    private void Pack_Data_To_Arraylist_For_Bmr(String Height, View view){
        //Packs data to arraylist so t can be calculated later
        //Variables to be passed

        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Bmr, Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)

        ArrayList<String> Data_For_Bmr = new ArrayList<String>();

        int Age = reusableFunctions.To_Int(User_Data.get(0));
        boolean isMale = reusableFunctions.String_To_Bool(User_Data.get(5));

        Data_For_Bmr.add(ReusableFunctions.Int_To_Strng(Age));
        Data_For_Bmr.add(String.valueOf(isMale));
        Data_For_Bmr.add(Height);

        To_Enter_Weight(Data_For_Bmr);
    }

    private boolean Is_Feet_Selected(View view) {
        //Checks if Feet or Inches Selected.
        int Resource_Interface_Color_Clicked_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);

        ColorStateList Foot_Button_Color = binding.FootButton.getBackgroundTintList();
        int Int_Foot_Button_Current_Color = Foot_Button_Color.getDefaultColor();

        //if Foot button is colored. then true, else false.
        if (Int_Foot_Button_Current_Color == Resource_Interface_Color_Clicked_Color) {
            //Foot_Inches_To_Cm();
            return true;
        } else {
            Log.d(TAG, "Height Entered In Cm");
            return false;
        }
    }

    private void To_Enter_Weight(ArrayList<String> Arraylist_For_Bmr){
        //Go To Enter Weight Function and pass arraylist
        Intent Page_Movement_Intent = new Intent(Enter_Height.this, Enter_Weight.class);
        Page_Movement_Intent.putExtra("Data_For_Bmr", Arraylist_For_Bmr);
        Page_Movement_Intent.putExtra("User_Data", User_Data);

        startActivity(Page_Movement_Intent);
    }

    //----------------------------------------------------------------------//
    //---------------------------------- UI --------------------------------//
    //----------------------------------------------------------------------//

    public void Foot_Button_Pressed(View view) {
        binding.HeightInput.setText("");
        binding.HeightInput.setHint("Enter Height In Foot");

        int Resource_Interface_Color_Clicked = ContextCompat.getColor(view.getContext(), R.color.interface_color_clicked);
        int Resource_Interface_Color = ContextCompat.getColor(view.getContext(), R.color.interface_color);
        //git save3
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