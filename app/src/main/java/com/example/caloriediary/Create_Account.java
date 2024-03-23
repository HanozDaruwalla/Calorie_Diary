package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityCreateAccountBinding;

import java.util.ArrayList;

public class Create_Account extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    int Default_Password_Input_Type;
    Intent Page_Movement_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_create_account);
        setContentView(binding.getRoot());
        Default_Password_Input_Type = binding.PasswordInput.getInputType();
    }

    public void Validation_Checks_Not_Null(View view) {

        ReusableFunctions reusableFunctions = new ReusableFunctions();

        String Username = binding.UsernameInput.getText().toString();
        String Password = binding.PasswordInput.getText().toString();
        String Password2 = binding.PasswordInput2.getText().toString();
        String Email = binding.EmailInput.getText().toString();

        //checks no fields are Null
        if(Username.equals("") || Password.equals("") || Password2.equals("") || Email.equals("") || binding.EmailConfirmationCheckBox.isChecked() == false) {
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter data to all fields and tick checkbox.");
        }else {
            Validation_Checks_Valid_Inputs(Username, Password, Password2, Email, reusableFunctions, view);
        }

    }

    public void Validation_Checks_Valid_Inputs(String Username, String Password, String Password2, String Email, ReusableFunctions reusableFunctions, View view) {
        boolean Is_UpperCase;
        Is_UpperCase = containsUpperCase(Password);

        //checks if pw >8 chars/ Cap letter and Special Char
        if (Password.length() >= 8 && Is_UpperCase == true && containsSpecialCharacter(Password) == true) {
            if (Password.equals(Password2)) { //check if passwords match
                //checks for a valid email
                if(Email.contains("@gmail") || Email.contains("@outlook") || Email.contains("@yahoo") || Email.contains("@student") || Email.contains("@hotmail")){
                    if(Email.equals("@gmail") || Email.equals("@outlook") || Email.equals("@yahoo") || Email.equals("@student") || Email.equals("@hotmail")){
                        reusableFunctions.Create_Toast(getApplicationContext(), "Invalid Email");
                    }else{
                        // ------------------------------------------------------------
                        // -------------------- Creates An Account --------------------
                        // ------------------------------------------------------------
                        To_Database(Username,Password,Email,view);
                    }
                }else{
                    reusableFunctions.Create_Toast(getApplicationContext(), "Invalid Email");
                }
            }else {
                reusableFunctions.Create_Toast(getApplicationContext(), "Your Passwords Don't Match");
            }
        }else {
            reusableFunctions.Create_Toast(getApplicationContext(), "Password Requires: 8 characters, special char + uppercase.");
        }
    }

    private void To_Database(String Username, String Password, String Email, View view){
        ArrayList Account_Info = new ArrayList();
        Account_Info.add(Username);
        Account_Info.add(Password);
        Account_Info.add(Email);

        Page_Movement_Intent = new Intent(view.getContext(), Create_Account.class);
        Page_Movement_Intent.putExtra("Sent_Info", Account_Info);
        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        Page_Movement_Intent.putExtra("Location_From",0);
        startActivity(Page_Movement_Intent);
    }

    private void Password_Checkbox_Clicked() {
        if (binding.PasswordCheckBox.isChecked()) {
            binding.PasswordInput.setInputType(0);
            binding.PasswordInput2.setInputType(0);
        } else {
            binding.PasswordInput.setInputType(Default_Password_Input_Type);
            binding.PasswordInput2.setInputType(Default_Password_Input_Type);
        }
    }

    //          ----------------------- EXTRA FUNCTIONS/ DEPENDENCIES -----------------------

    private void Email_Checkbox_Clicked() {

    }

    private static boolean containsUpperCase(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsSpecialCharacter(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                // If the character is neither a letter nor a digit, it's a special character
                return true;
            }
        }
        return false;
    }//checks if special char


}