package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //Intent Page_Movement_Intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
    }

    public void GetStarted_Clicked(View view) {
        Intent Page_Movement_Intent = new Intent(view.getContext(), Create_Account.class);
        startActivity(Page_Movement_Intent);
    }

    private void To_Login_Page(){
        //Note on error: When intent var is global. can only be assigned once. so causes an error when variable is used more than once e.g. to Create Account and login
        ReusableFunctions reusableFunctions = new ReusableFunctions();

        Intent Page_Movement_Intent = new Intent(MainActivity.this, Login.class);//
        Page_Movement_Intent.putExtra("Username","");
        startActivity(Page_Movement_Intent);
    }

    public void signin_Label_Clicked(View view) {
        To_Login_Page();
    }

    public void signin2_Label_Clicked(View view) {
        To_Login_Page();
    }

}
