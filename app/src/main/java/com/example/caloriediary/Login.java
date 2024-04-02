package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    Intent Page_Movement_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate((getLayoutInflater()));
        setContentView(R.layout.activity_login);
        setContentView(binding.getRoot());
        String Imported_Username;
        Imported_Username = getIntent().getExtras().getString("Username");
        binding.UsernameInput.setText(Imported_Username);
    }

    public void Login_Button_Clicked(View view) {
        Null_Checks(view);
    }

    private void Null_Checks(View view){

        ReusableFunctions reusableFunctions = new ReusableFunctions();

        if(binding.UsernameInput.getText().toString().equals("") || binding.PasswordInput.getText().toString().equals("")){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Your Details");
        }else{
            ArrayList<String> Account_Info = new ArrayList();
            Account_Info.add(binding.UsernameInput.getText().toString());
            Account_Info.add(binding.PasswordInput.getText().toString());

            Page_Movement_Intent = new Intent(view.getContext(), Database.class);
            Page_Movement_Intent.putExtra("Sent_Info", Account_Info);

            // 0 = Create_Account, 1 = login, 2 = Add Product From Db
            Page_Movement_Intent.putExtra("Sent_From",1);
            reusableFunctions.Create_Toast(getApplicationContext(), "Going To Db");
            startActivity(Page_Movement_Intent);
        }
    }

    public void Password_Checkbox_Clicked(View view) {
        int Default_Password_Input_Type = binding.PasswordInput.getInputType();

        if (binding.PasswordCheckBox.isChecked()) {
            binding.PasswordInput.setInputType(0);
        } else {
            binding.PasswordInput.setInputType(Default_Password_Input_Type);
        }
    }
}