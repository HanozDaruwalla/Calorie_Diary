package com.example.caloriediary.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.caloriediary.Api_Refactored.NutritionData;
import com.example.caloriediary.Bmi_Calc.OptionForBmi;
import com.example.caloriediary.Creating_Account_And_Login.User;
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
        getSupportActionBar(). hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_recyclerview);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //EDIT
        Log.d(TAG, "User_Data [0] = " + User_Data.get(0));
        Lunch_Data_Passer(User_Data);
    }

    public interface Food_Data_FoundListener {
        void Food_Data_Found(ArrayList<ArrayList<NutritionData>> foodDataList);
    }

    private void Lunch_Data_Passer(ArrayList<String> userdata){

        Log.d(TAG, "In Function");
        ArrayList<String> UsernameDateFoodtype = new ArrayList<>();
        UsernameDateFoodtype.add(userdata.get(6));
        UsernameDateFoodtype.add(reusableFunctions.Date_Creator());
        UsernameDateFoodtype.add(getString(R.string.Lunch_Meal_Type));
        Log.d(TAG, "Assigned");
        //gets from string xml file

        Log.d(TAG, "data = " + UsernameDateFoodtype.get(0) + "+" + UsernameDateFoodtype.get(1) + "+" + UsernameDateFoodtype.get(2));
        Log.d(TAG, "Vars Set");
        Database DB = new Database();
        DB.Get_Food_Data(UsernameDateFoodtype, new Food_Data_FoundListener() {
            @Override
            public void Food_Data_Found(ArrayList<ArrayList<NutritionData>> foodDataList) {
                Log.d(TAG, "Food_Data found overrided");

                Bundle bundle = new Bundle();
                bundle.putSerializable("User_Meals", foodDataList); // NutritionData must implement Serializable

                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.setArguments(bundle); // Pass the data to the dashboard fragment

                NavController navController = Navigation.findNavController(recyclerview.this, R.id.nav_host_fragment_activity_recyclerview);
                navController.navigate(R.id.navigation_dashboard, bundle); // Show the Dashboard fragment


            }
        });

    }

}