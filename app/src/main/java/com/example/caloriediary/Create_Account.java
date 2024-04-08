package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityCreateAccountBinding;

import java.util.ArrayList;

public class Create_Account extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;
    int Default_Password_Input_Type;
    Intent Page_Movement_Intent;
    private static final String TAG = "Create_Account_Class";
    Boolean Gender_Selected = false;

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
        User user_entered_details = new User();
        Tester tester_details = new Tester();

        user_entered_details.setUsername(binding.UsernameInput.getText().toString());
        user_entered_details.setPassword(binding.PasswordInput.getText().toString());

        String Password2 = binding.PasswordInput2.getText().toString();
        user_entered_details.setEmail(binding.EmailInput.getText().toString());
        user_entered_details.setMale(Set_Gender_From_Buttons(user_entered_details, Gender_Selected));
        user_entered_details.setAge(reusableFunctions.To_Int(binding.AgeInput.getText().toString()));


        //checks if tester details
        if(user_entered_details.getUsername().equals(tester_details.getUsername())) {
            //auto places the rest of tester details from tester class

            user_entered_details.setPassword(tester_details.getPassword());
            binding.PasswordInput.setText(tester_details.getPassword());
            //user_entered_details.setPassword2(tester_details.getPassword());
            binding.PasswordInput2.setText(tester_details.getPassword());
            binding.EmailInput.setText(tester_details.getEmail());
            binding.EmailConfirmationCheckBox.setChecked(true);
            binding.MaleRadioButton.setChecked(true);
            binding.AgeInput.setText("21");
            Set_Gender_From_Tester(tester_details);
            Log.d(TAG, "Username = " + tester_details.getUsername());
            Log.d(TAG, "Password = " + tester_details.getPassword());
            Log.d(TAG, "Email = " + tester_details.getEmail());

            reusableFunctions.Create_Toast(getApplicationContext(), "Tester Recognised. Adding Rest.");
            Validation_Checks_Valid_Inputs(user_entered_details, Password2, reusableFunctions, Gender_Selected, view);

            //checks no fields are Null
        }else if(user_entered_details.getUsername().equals("") || user_entered_details.getPassword().equals("") || Password2.equals("") || user_entered_details.getEmail().equals("") || binding.EmailConfirmationCheckBox.isChecked() == false || (binding.FemaleRadioButton.isChecked() == false && binding.MaleRadioButton.isChecked() == false) || binding.AgeInput.getText().equals("")) {
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter data to all fields and tick checkbox.");
        }else {//accept clause
            Validation_Checks_Valid_Inputs(user_entered_details, Password2, reusableFunctions, Gender_Selected, view);
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

                    }else if(user.getAge() <18){
                        reusableFunctions.Create_Toast(getApplicationContext(), "You must be over 18 to participate in this App..");

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
        Account_Info.add(String.valueOf(user.isMale()));
        Account_Info.add(ReusableFunctions.intToString(user.getAge()));
        Account_Info.add(user.getHeight_Cm());
        Account_Info.add(user.getWeight_Kg());
        Account_Info.add(user.getRmi());


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

    private void Set_Gender_From_Tester(Tester tester_details) {
        if (binding.MaleRadioButton.isChecked()) {
            binding.MaleRadioButton.setChecked(true);
            tester_details.setMale(true);
            Gender_Selected = true;
        } else {
            binding.FemaleRadioButton.setChecked(true);
            tester_details.setMale(false);
        }
    }

    private boolean Set_Gender_From_Buttons(User user_entered_details, Boolean Gender_Selected) {
        if (binding.MaleRadioButton.isChecked()) {
            user_entered_details.setMale(true);
            Gender_Selected = true;
        } else {
            user_entered_details.setMale(false);

        }
        return user_entered_details.isMale();
    }



    public void Male_RadioButton_Pressed(View view) {
        binding.MaleRadioButton.setChecked(true);
    }

    public void Female_RadioButton_Pressed(View view) {
        binding.FemaleRadioButton.setChecked(true);
    }

}