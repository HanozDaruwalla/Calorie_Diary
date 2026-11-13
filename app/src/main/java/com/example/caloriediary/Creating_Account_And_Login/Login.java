package com.example.caloriediary.Creating_Account_And_Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.Database.Database;
import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;
    Intent Page_Movement_Intent;
    int Int_Value_Of_Masked_Pw_Format = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate((getLayoutInflater()));
        setContentView(R.layout.activity_login);
        setContentView(binding.getRoot());

        String Imported_Username = getIntent().getExtras().getString("Username");
        binding.UsernameInput.setText(Imported_Username);
        Int_Value_Of_Masked_Pw_Format = binding.PasswordInput.getInputType();
        getSupportActionBar(). hide();
        EdgeToEdge.enable(this);
    }

    public void Login_Button_Clicked(View view) {
        Null_Checks(view);
    }

    private void Null_Checks(View view){

        ReusableFunctions reusableFunctions = new ReusableFunctions();
//if username and password null. then redo else Add username and pw to array and pas to db Login();
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

        if (binding.PasswordCheckBox.isChecked()) {
            binding.PasswordInput.setInputType(0);
        } else {
            binding.PasswordInput.setInputType(Int_Value_Of_Masked_Pw_Format);
        }
        binding.PasswordInput.setCursorVisible(true);
    }
}