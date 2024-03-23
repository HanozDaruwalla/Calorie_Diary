package com.example.caloriediary;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.caloriediary.databinding.ActivityDatabaseBinding;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Database extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;
    String[] Info;
    HashMap<String, Object> Information_Hashmap = new HashMap<>();
    String Location;
    Intent Page_Movement_Intent;
    private ActivityDatabaseBinding binding;
    DatabaseReference Database_Controller;
    private String Db_Node = "";
    String Creation_Type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
    }


}