package com.example.caloriediary;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityCreateAccountBinding;

public class Create_Account extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    int Default_Password_Input_Type;
    public String Error_Msg = "No Error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_create_account);
        setContentView(binding.getRoot());
        Default_Password_Input_Type = binding.PasswordInput.getInputType();


    }

    private void Create_Vars_From_Gui(View view){
        String Username = binding.UsernameInput.getText().toString();
        String Password = binding.PasswordInput.getText().toString();
        String Password2 = binding.PasswordInput2.getText().toString();
        String Email = binding.EmailInput.getText().toString();
        boolean Email_Checkbox_Ticked = Is_Email_Checkbox_Checked();
        Validation_Checks_Not_Null(Username, Password, Password2, Email, Email_Checkbox_Ticked);
    }

    public boolean Validation_Checks_Not_Null(String Username, String Password, String Password2,String Email, boolean Email_Checkbox_Ticked) {

        if(Username.equals("") || Password.equals("") || Password2.equals("") || Email.equals("") || Email_Checkbox_Ticked == false) {
            Create_Toast("Please Enter data to all fields and tick checkbox.");
            return false;
        }else {
            Validation_Checks_Valid_Inputs(Password, Password2, Email);
            Create_Toast(Error_Msg);
            return true;
        }

    }

    public boolean Validation_Checks_Valid_Inputs(String Password, String Password2, String Email) {
        boolean Is_UpperCase;
        Is_UpperCase = containsUpperCase(Password);

        if (Password.length() >= 8 && Is_UpperCase == true && containsSpecialCharacter(Password) == true) {
            if (Password.equals(Password2)) {
                if(Email.contains("@gmail") || Email.contains("@outlook") || Email.contains("@yahoo") || Email.contains("@student") || Email.contains("@hotmail")){
                    if(Email.equals("@gmail") || Email.equals("@outlook") || Email.equals("@yahoo") || Email.equals("@student") || Email.equals("@hotmail")){
                        Create_Toast("Invalid Email");
                        return false;
                    }else{
                        Create_Toast("Valid Login Details. Connecting To Db");
                        return true;
                    }
                }else{
                    Create_Toast("Invalid Email");
                    return false;
                }
            }else {
                Create_Toast("Your Passwords Don't Match");
                return false;
            }
        }else {
            Create_Toast("Password Requires: 8 characters, special char + uppercase.");
            return false;
        }
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

    private boolean Is_Email_Checkbox_Checked() {
        if(binding.EmailConfirmationCheckBox.isChecked() == false){
            return false;
        }else{
            return true;
        }
    }


    public static boolean containsUpperCase(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }//checks if uppercase char

    public static boolean containsSpecialCharacter(String str) {
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!Character.isLetterOrDigit(ch)) {
                // If the character is neither a letter nor a digit, it's a special character
                return true;
            }
        }
        return false;
    }//checks if special char

    public String Create_Toast(String Toast_Msg) {
        Toast toast = Toast.makeText(getApplicationContext(), Toast_Msg, Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.CENTER_VERTICAL,0,250);
        toast.show();
        return ("");
    }
}