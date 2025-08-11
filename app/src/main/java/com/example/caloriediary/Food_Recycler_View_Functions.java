package com.example.caloriediary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.caloriediary.Database.Database;
import com.example.caloriediary.RecyclerView.Breakfast_RecyclerView;
import com.example.caloriediary.RecyclerView.Lunch_RecyclerView;

import com.example.caloriediary.RecyclerView.Interface_Food_Data_Found;
import com.example.caloriediary.RecyclerView.ui.dashboard.DashboardFragment;

import java.util.ArrayList;

public class Food_Recycler_View_Functions {
    String TAG = "recyclerview_universal";
    ReusableFunctions reusableFunctions = new ReusableFunctions();
    Database DB = new Database();


    public void Breakfast_Data_Passer(ArrayList<String> userdata, ArrayList<String> UsernameDateFoodtype_Passed, Activity Activity_Data) {
        Log.d(TAG, "in Breakfast_Data_Passer");
        Log.d(TAG, "data = " + UsernameDateFoodtype_Passed.get(0) + "+" + UsernameDateFoodtype_Passed.get(1) + "+" + UsernameDateFoodtype_Passed.get(2));
        Log.d(TAG, "Vars Set");
        //Database DB = new Database();
        DB.Get_Food_Data(UsernameDateFoodtype_Passed, new Interface_Food_Data_Found.Food_Data_FoundListener() {
            @Override
            public void Food_Data_Found(ArrayList<ArrayList<Nutrition_Data_From_Db>> foodDataList) {
                Log.d(TAG, "Food_Data found overrided");

                Bundle bundle = new Bundle();
                bundle.putSerializable("User_Meals", foodDataList); // NutritionData must implement Serializable

                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.setArguments(bundle); // Pass the data to the dashboard fragment
                Log.d(TAG, "Bundle To Dashboard Set");

                NavController navController = Navigation.findNavController(Activity_Data, R.id.nav_host_fragment_activity_recyclerview);
                Log.d(TAG, "Nav Set");
                navController.navigate(R.id.navigation_dashboard, bundle); // Show the Dashboard fragment
            }
        });
    }

    public void Lunch_Data_Passer(ArrayList<String> userdata, ArrayList<String> UsernameDateFoodtype_Passed, Activity Activity_Data) {
        Log.d(TAG, "in Lunch_Data_Passer");
        Log.d(TAG, "data = " + UsernameDateFoodtype_Passed.get(0) + "+" + UsernameDateFoodtype_Passed.get(1) + "+" + UsernameDateFoodtype_Passed.get(2));
        Log.d(TAG, "Vars Set");
        //Database DB = new Database();
        DB.Get_Food_Data(UsernameDateFoodtype_Passed, new Interface_Food_Data_Found.Food_Data_FoundListener() {
            @Override
            public void Food_Data_Found(ArrayList<ArrayList<Nutrition_Data_From_Db>> foodDataList) {
                Log.d(TAG, "Food_Data found overrided");

                Bundle bundle = new Bundle();
                bundle.putSerializable("User_Meals", foodDataList); // NutritionData must implement Serializable

                DashboardFragment dashboardFragment = new DashboardFragment();
                dashboardFragment.setArguments(bundle); // Pass the data to the dashboard fragment
                Log.d(TAG, "Bundle To Dashboard Set");

                NavController navController = Navigation.findNavController(Activity_Data, R.id.nav_host_fragment_activity_recyclerview);
                Log.d(TAG, "Nav Set");
                navController.navigate(R.id.navigation_dashboard, bundle); // Show the Dashboard fragment
            }
        });
    }
}
