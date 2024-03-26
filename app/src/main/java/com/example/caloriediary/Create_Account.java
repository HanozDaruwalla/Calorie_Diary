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
        User user = new User();

        user.setUsername(binding.UsernameInput.getText().toString());
        user.setPassword(binding.PasswordInput.getText().toString());
        user.setPassword2(binding.PasswordInput2.getText().toString());
        user.setEmail(binding.EmailInput.getText().toString());

        //checks no fields are Null

        if(user.getUsername().equals("Tester")) {
            user.setPassword("Tester123*");
            binding.PasswordInput.setText(user.getPassword());
            user.setPassword2("Tester123*");
            binding.PasswordInput2.setText(user.getPassword2());
            user.setEmail("Hanozdaru@outlook.com");
            binding.EmailInput.setText(user.getEmail());
            binding.EmailConfirmationCheckBox.setChecked(true);
            reusableFunctions.Create_Toast(getApplicationContext(), "Tester Recognised. Adding Rest.");
            Validation_Checks_Valid_Inputs(user, reusableFunctions, view);
        }else if(user.getUsername().equals("") || user.getPassword().equals("") || user.getPassword2().equals("") || user.getEmail().equals("") || binding.EmailConfirmationCheckBox.isChecked() == false) {
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter data to all fields and tick checkbox.");
        }else {
            Validation_Checks_Valid_Inputs(user, reusableFunctions, view);
        }
    }

    public void Validation_Checks_Valid_Inputs(User user, ReusableFunctions reusableFunctions, View view) {
        boolean Is_UpperCase;
        Is_UpperCase = containsUpperCase(user.getPassword());

        //checks if pw >8 chars/ Cap letter and Special Char
        if (user.getPassword().length() >= 8 && Is_UpperCase == true && containsSpecialCharacter(user.getPassword()) == true) {
            if (user.getPassword().equals(user.getPassword2())) { //check if passwords match
                //checks for a valid email
                if(user.getEmail().contains("@gmail") || user.getEmail().contains("@outlook") || user.getEmail().contains("@yahoo") || user.getEmail().contains("@student") || user.getEmail().contains("@hotmail")){
                    if(user.getEmail().equals("@gmail") || user.getEmail().equals("@outlook") || user.getEmail().equals("@yahoo") || user.getEmail().equals("@student") || user.getEmail().equals("@hotmail")){
                        reusableFunctions.Create_Toast(getApplicationContext(), "Invalid Email");
                    }else{
                        // ------------------------------------------------------------
                        // -------------------- Creates An Account --------------------
                        // ------------------------------------------------------------
                        To_Database(user,reusableFunctions, view);
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

    private void To_Database(User user, ReusableFunctions reusableFunctions, View view){
        ArrayList<String> Account_Info = new ArrayList();
        Account_Info.add(user.getUsername());
        Account_Info.add(user.getPassword());
        Account_Info.add(user.getEmail());

        Page_Movement_Intent = new Intent(view.getContext(), Database.class);
        Page_Movement_Intent.putExtra("Sent_Info", Account_Info);
        // 0 = Create_Account, 1 = login, 2 = Add Product From Db
        Page_Movement_Intent.putExtra("Sent_From",0);
        reusableFunctions.Create_Toast(getApplicationContext(), "Going To Db");
        startActivity(Page_Movement_Intent);
    }

    public void Password_Checkbox_Clicked(View view) {
        if (binding.PasswordCheckBox.isChecked()) {
            binding.PasswordInput.setInputType(0);
            binding.PasswordInput2.setInputType(0);
        } else {
            binding.PasswordInput.setInputType(Default_Password_Input_Type);
            binding.PasswordInput2.setInputType(Default_Password_Input_Type);
        }
    }

    //          ----------------------- EXTRA FUNCTIONS/ DEPENDENCIES -----------------------

    public void Email_Checkbox_Clicked(View view) {

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