package com.example.caloriediary.Creating_Account_And_Login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Database.Database;
import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityCreateAccountBinding;

import java.util.ArrayList;

public class Create_Account extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    int Int_Value_Of_Masked_Pw;//when pw are ***** there is an int value to make them unreadable
    Intent Page_Movement_Intent;

    Boolean Gender_Selected = false;
    private static final String TAG = "Create_Account_Class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_create_account);
        setContentView(binding.getRoot());
        Int_Value_Of_Masked_Pw = binding.PasswordInput.getInputType();
    }

    public void Validation_Checks_Not_Null(View view) {

        ReusableFunctions reusableFunctions = new ReusableFunctions();
        User User_Entered_Details = new User();
        Tester Tester_Details = new Tester();

        //create vars from binding
        User_Entered_Details.setUsername(binding.UsernameInput.getText().toString());
        User_Entered_Details.setPassword(binding.PasswordInput.getText().toString());
        String Password2 = binding.PasswordInput2.getText().toString();
        User_Entered_Details.setEmail(binding.EmailInput.getText().toString());
        User_Entered_Details.setMale(Set_Gender_From_Buttons(User_Entered_Details, Gender_Selected));
        User_Entered_Details.setAge(reusableFunctions.To_Int(binding.AgeInput.getText().toString()));

        //checks if tester details fill in rest
        if(User_Entered_Details.getUsername().equals(Tester_Details.getUsername())) {
            //auto places the rest of tester details from tester class

            User_Entered_Details.setPassword(Tester_Details.getPassword());
            binding.PasswordInput.setText(Tester_Details.getPassword());
            //User_Entered_Details.setPassword2(Tester_Details.getPassword());
            binding.PasswordInput2.setText(Tester_Details.getPassword());
            binding.EmailInput.setText(Tester_Details.getEmail());
            binding.EmailConfirmationCheckBox.setChecked(true);
            binding.MaleRadioButton.setChecked(true);
            binding.AgeInput.setText("21");
            Set_Gender_From_Tester(Tester_Details);
            Log.d(TAG, "Username = " + Tester_Details.getUsername());
            Log.d(TAG, "Password = " + Tester_Details.getPassword());
            Log.d(TAG, "Email = " + Tester_Details.getEmail());

            reusableFunctions.Create_Toast(getApplicationContext(), "Tester Recognised. Adding Rest.");
            Validation_Checks_Valid_Inputs(User_Entered_Details, Password2, reusableFunctions, Gender_Selected, view);

            //checks no fields are Null
        }else if(User_Entered_Details.getUsername().equals("") || User_Entered_Details.getPassword().equals("") || Password2.equals("") || User_Entered_Details.getEmail().equals("") || binding.EmailConfirmationCheckBox.isChecked() == false || (binding.FemaleRadioButton.isChecked() == false && binding.MaleRadioButton.isChecked() == false) || binding.AgeInput.getText().equals("")) {
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter data to all fields and tick checkbox.");
        }else {//accept clause
            Validation_Checks_Valid_Inputs(User_Entered_Details, Password2, reusableFunctions, Gender_Selected, view);
        }
    }

    public void Validation_Checks_Valid_Inputs(User user,  String Password2, ReusableFunctions reusableFunctions,boolean Gender_Selected, View view) {
        boolean Is_UpperCase;
        Is_UpperCase = containsUpperCase(user.getPassword());

        //checks if pw >8 chars/ Cap letter and Special Char
        if (user.getPassword().length() >= 8 && Is_UpperCase == true && containsSpecialCharacter(user.getPassword()) == true) {
            if (user.getPassword().equals(Password2)) { //check if passwords match

                //checks for a valid email
                if(user.getEmail().contains("@gmail") || user.getEmail().contains("@outlook") || user.getEmail().contains("@yahoo") || user.getEmail().contains("@student") || user.getEmail().contains("@hotmail")){
                    if(user.getEmail().equals("@gmail") || user.getEmail().equals("@outlook") || user.getEmail().equals("@yahoo") || user.getEmail().equals("@student") || user.getEmail().equals("@hotmail")){
                        reusableFunctions.Create_Toast(getApplicationContext(), "Invalid Email");

                        //checks if age >18
                    }else if(user.getAge() <18 && user.getAge()<100){
                        reusableFunctions.Create_Toast(getApplicationContext(), "You must be over 18 to participate in this App.");

                    }else{
                        // -------------------- Creates An Account --------------------
                        To_Database(user,reusableFunctions, view);
                    }
                }else{//Incorrect Details Messages
                    reusableFunctions.Create_Toast(getApplicationContext(), "Invalid Email");
                }
            }else {//if passwords dont match
                reusableFunctions.Create_Toast(getApplicationContext(), "Your Passwords Don't Match");
            }
        }else {//incorrect type of password
            reusableFunctions.Create_Toast(getApplicationContext(), "Password Requires: 8 characters, special char + uppercase.");
        }
    }

    private void To_Database(User user, ReusableFunctions reusableFunctions, View view){
        ArrayList<String> Account_Info = new ArrayList();

        //sends the user data to the db to be checked
        Account_Info.add(user.getUsername());
        Account_Info.add(user.getPassword());
        Account_Info.add(user.getEmail());
        Account_Info.add(String.valueOf(user.isMale()));
        Account_Info.add(ReusableFunctions.Int_To_Strng(user.getAge()));
        Account_Info.add(user.getHeight_Cm());
        Account_Info.add(user.getWeight_Kg());
        Account_Info.add(user.getBmr());

        Page_Movement_Intent = new Intent(view.getContext(), Database.class);

        //the info to be sent
        Page_Movement_Intent.putExtra("Sent_Info", Account_Info);

        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        //number representation of where the function is called
        Page_Movement_Intent.putExtra("Sent_From",0);
        reusableFunctions.Create_Toast(getApplicationContext(), "Going To Db");
        startActivity(Page_Movement_Intent);
    }

    public void Password_Checkbox_Clicked(View view) {
        //if see pw box ticked. show pw, otherwise hide pw
        if (binding.PasswordCheckBox.isChecked()) {
            //shows pws
            binding.PasswordInput.setInputType(0);
            binding.PasswordInput2.setInputType(0);
        } else {
            //hides pws
            binding.PasswordInput.setInputType(Int_Value_Of_Masked_Pw);
            binding.PasswordInput2.setInputType(Int_Value_Of_Masked_Pw);
        }
        binding.PasswordInput2.setCursorVisible(true);
    }

    //          ----------------------- EXTRA FUNCTIONS/ DEPENDENCIES -----------------------

    public void Email_Checkbox_Clicked(View view) {
    }

    private static boolean containsUpperCase(String Str) {
        for (int i = 0; i < Str.length(); i++) {
            if (Character.isUpperCase(Str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsSpecialCharacter(String Str) {
        for (int i = 0; i < Str.length(); i++) {
            char Current_Character = Str.charAt(i);
            if (!Character.isLetterOrDigit(Current_Character)) {
                //if it isnt a char of digit. then its a special char
                return true;
            }
        }
        return false;
    }//checks if special char

    private void Set_Gender_From_Tester(Tester Passed_Tester_Details) {
        //if male radio button pressed, set to male, else set to female
        if (binding.MaleRadioButton.isChecked()) {
            binding.MaleRadioButton.setChecked(true);
            Passed_Tester_Details.setMale(true);
            Gender_Selected = true;
        } else {
            binding.FemaleRadioButton.setChecked(true);
            Passed_Tester_Details.setMale(false);
        }
    }

    private boolean Set_Gender_From_Buttons(User Passed_User_Entered_Details, Boolean Gender_Selected) {
        if (binding.MaleRadioButton.isChecked()) {
            Passed_User_Entered_Details.setMale(true);
            Gender_Selected = true;
        } else {
            Passed_User_Entered_Details.setMale(false);
        }
        return Passed_User_Entered_Details.isMale();
    }

    public void Male_RadioButton_Pressed(View view) {
        binding.MaleRadioButton.setChecked(true);
    }

    public void Female_RadioButton_Pressed(View view) {
        binding.FemaleRadioButton.setChecked(true);
    }

}