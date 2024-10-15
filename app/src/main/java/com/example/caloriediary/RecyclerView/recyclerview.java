package com.example.caloriediary.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.caloriediary.Bmi_Calc.OptionForBmi;
import com.example.caloriediary.Database.Database;
import com.example.caloriediary.R;
import com.example.caloriediary.RecyclerView.ui.dashboard.DashboardFragment;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityRecyclerviewBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class recyclerview extends AppCompatActivity {

    private ActivityRecyclerviewBinding binding;
    Intent Page_Movement_Intent;
    String TAG = "recyclerview";
    ReusableFunctions reusableFunctions = new ReusableFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> User_Data = getIntent().getExtras().getStringArrayList("User_Data");

        binding = ActivityRecyclerviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_recyclerview);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //EDIT
        //Lunch_Data_Passer(User_Data);
    }

    private void Lunch_Data_Passer(ArrayList<String> userdata){
        ArrayList<String> UsernameDateFoodtype = new ArrayList<>();
        UsernameDateFoodtype.add(userdata.get(5));
        UsernameDateFoodtype.add(reusableFunctions.Date_Creator());
        UsernameDateFoodtype.add(getString(R.string.Lunch_Meal_Type));
        //gets from string xml file

        Log.d(TAG, "data = " + UsernameDateFoodtype.get(0) + "+" + UsernameDateFoodtype.get(0) + "+" + UsernameDateFoodtype.get(2));
        Log.d(TAG, "Vars Set");
        Page_Movement_Intent = new Intent(recyclerview.this, DashboardFragment.class);//
        Log.d(TAG, "taking user to bmi option");
        Page_Movement_Intent.putExtra("UsernameDateFoodtype", UsernameDateFoodtype);
        startActivity(Page_Movement_Intent);




    }

}