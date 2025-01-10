package com.example.caloriediary.Api_Refactored;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.caloriediary.Database.Database;
import com.example.caloriediary.R;
import com.example.caloriediary.ReusableFunctions;
import com.example.caloriediary.databinding.ActivityMain2Binding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity2 extends AppCompatActivity {


    final static String TAG =  "MainActivity2";
    int Quantity_Multiplier = 1;
    String Meal_Type = "Undefined";

    private ActivityMain2Binding binding;

    ReusableFunctions reusableFunctions = new ReusableFunctions();

    //api data
    final String API_KEY = "d016fd33a6081cbb9abd91a7e6cce48a";
    final String APP_ID = "3747b1e6";
    final String REQUEST_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    // variables

    NutritionData Nutrition_Data_Template;
    String Food_Item = "Undefined";
    InputMethodManager input_method_manager;
    int Index_Of_Nutritional_Values_Arraylist;
    ArrayList<String> Nutritional_Values_Arraylist;
    Bitmap ImageData;


    String Username = "Undefined";
    String Bmr = "Undefined";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity Loaded");
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main2);
        setContentView(binding.getRoot());
        getSupportActionBar(). hide();

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            /*SharedPreferences shared_preference = getSharedPreferences("User_Data", Context.MODE_PRIVATE);
            String data = shared_preference.getString("Data", null);

            String[] Values_In_Preference = data.split(",");
            if (Values_In_Preference.length == 2) {
                Username = Values_In_Preference[0];
                Bmr = Values_In_Preference[1];

            }else{
                Log.d(TAG, "Shared preferences has got more or less than 2 values!!!!");
            }

             */

            SharedPreferences shared_preference = getSharedPreferences("User_Data", Context.MODE_PRIVATE);
            String Username = shared_preference.getString("Username", null);
            String Bmr = shared_preference.getString("Bmr", null);

            Log.d(TAG, "Username= " + Username);
            Log.d(TAG, "Bmr = " + Bmr);
            binding.progressBar.setVisibility(View.INVISIBLE);
            input_method_manager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);

            Log.d(TAG, "OnCreate done");

            //get data from homePage
            Intent i = getIntent();
            if(i.getExtras()!=null) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                String str = i.getExtras().getString("BitmapUri");
                Uri uri = Uri.parse(str);
                Nutritional_Values_Arraylist = i.getExtras().getStringArrayList("StringArrList");

                Log.d(TAG, "1");
                reusableFunctions.Create_Toast(getApplicationContext(), Nutritional_Values_Arraylist.get(Index_Of_Nutritional_Values_Arraylist) );
                Index_Of_Nutritional_Values_Arraylist =0;
                new msyncTask().execute(uri);
            }else{
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }

            return insets;
        });

    }

    //API CALLS Method
    private void connectNutrionix(String foodName) {

        Log.d(TAG, "2");

        StringEntity stringEntity = null;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-app-id", APP_ID);
        client.addHeader("x-app-key", API_KEY);
        client.addHeader("x-remote-user-id", "0");
        JSONObject jsonObject = new JSONObject();

        Log.d(TAG, "8");
        try {
            jsonObject.put("query", foodName);
            Log.d(TAG, "9");

        } catch (JSONException e) {
            Log.d("Healthier", e.toString());
            Log.d(TAG, "10 error!");
        }
        try {
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Healthier", e.toString());
        }

        client.post(this, REQUEST_URL, stringEntity, "application/json",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        Log.d(TAG, "Success" + response.toString());
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Nutrition_Data_Template = NutritionData.fromJson(response);

                        new DownloadImageTask(binding.foodPic).execute(Nutrition_Data_Template.getPhotoUrl());
                        binding.foodPic.setVisibility(View.VISIBLE);

                        updateUI(Nutrition_Data_Template);
                    }
                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Healthier", "failed" + errorResponse.toString());
                        Toast.makeText(MainActivity2.this, "Sorry, We couldn't match any of your foods",
                                Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.INVISIBLE);


                    }
                });

    }

    private void callNutrionix(ArrayList<String> arrayList,int index) {
        Log.d(TAG, "3");
        StringEntity stringEntity = null;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-app-id", APP_ID);
        client.addHeader("x-app-key", API_KEY);
        client.addHeader("x-remote-user-id", "0");
        JSONObject jsonObject = new JSONObject();
        binding.progressBar.setVisibility(View.VISIBLE);

        try {
            jsonObject.put("query", arrayList.get(index));

        } catch (JSONException e) {
            Log.d("Healthier", e.toString());
        }
        try {
            stringEntity = new StringEntity(jsonObject.toString());
        } catch (UnsupportedEncodingException e) {
            Log.d("Healthier", e.toString());
        }

        client.post(this, REQUEST_URL, stringEntity, "application/json",
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        Log.d("Healthier", "Success" + response.toString());
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Nutrition_Data_Template = NutritionData.fromJson(response);

                        binding.foodPic.setImageBitmap(ImageData);
                        binding.foodPic.setVisibility(View.VISIBLE);

                        updateUI(Nutrition_Data_Template);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Healthier", "failed" + errorResponse.toString());

                        Index_Of_Nutritional_Values_Arraylist++;
                        if(Index_Of_Nutritional_Values_Arraylist > Nutritional_Values_Arraylist.size()-1) {
                            Toast.makeText(MainActivity2.this, "Sorry,We couldn't match any of your foods",
                                    Toast.LENGTH_SHORT).show();

                            binding.progressBar.setVisibility(View.INVISIBLE);

                        }else {
                            callNutrionix(Nutritional_Values_Arraylist, Index_Of_Nutritional_Values_Arraylist);

                        }

                    }
                });
    }

    private void updateUI(NutritionData nutritionData) {
        Log.d(TAG, "4");
        /*
        binding.foodNameR.setText(nutritionData.getFoodName());
        binding.calriesR.setText(String.valueOf(nutritionData.getCalories()));
        binding.proteinR.setText(String.valueOf(nutritionData.getProtein()) + "g");
        binding.totalFatR.setText(String.valueOf(nutritionData.getTotalfat()) + "g");
        binding.sugarR.setText(String.valueOf(nutritionData.getSugar()) + "g");
        binding.servingSizeR.setText(nutritionData.getServingSize());
        binding.totalCarbonhydrateR.setText(String.valueOf(nutritionData.getTotalCarbonhydrate() + "G"));
        binding.sodiuimR.setText(String.valueOf(nutritionData.getSodium()) + "mg");
        binding.cholesterolR.setText(String.valueOf(nutritionData.getCholesterol()) + "mg");
        binding.potassiumR.setText(String.valueOf(nutritionData.getPotassium()) + "mg");
        binding.dietaryFiberR.setText(nutritionData.getDiertaryFiber() + "g");
        binding.relativeLayout.setVisibility(View.VISIBLE);
         */

        binding.foodNameR.setText(nutritionData.getFoodName());
        binding.calriesR.setText(String.valueOf(nutritionData.getCalories() * Quantity_Multiplier));
        binding.proteinR.setText(String.valueOf(nutritionData.getProtein() * Quantity_Multiplier) + "g");
        binding.totalFatR.setText(String.valueOf(nutritionData.getTotalfat() * Quantity_Multiplier) + "g");
        binding.sugarR.setText(String.valueOf(nutritionData.getSugar() * Quantity_Multiplier) + "g");
        binding.servingSizeR.setText(String.valueOf(reusableFunctions.To_Int(nutritionData.getServingSize()) * Quantity_Multiplier));
        binding.totalCarbonhydrateR.setText(String.valueOf(nutritionData.getTotalCarbonhydrate() * Quantity_Multiplier + "G"));
        binding.sodiuimR.setText(String.valueOf(nutritionData.getSodium() * Quantity_Multiplier) + "mg");
        binding.cholesterolR.setText(String.valueOf(nutritionData.getCholesterol() * Quantity_Multiplier) + "mg");
        binding.potassiumR.setText(String.valueOf(nutritionData.getPotassium() * Quantity_Multiplier) + "mg");
        binding.dietaryFiberR.setText(nutritionData.getDiertaryFiber() * Quantity_Multiplier + "g");
        binding.relativeLayout.setVisibility(View.VISIBLE);
    }
/*
    public double toGrams(String value) {
        if (value.contains("mg")) {
            value = value.replace("m", "");
            value = value.replace("g", "");
            value = value.trim();
            ReusableFunctions reusableFunctions_temp = new ReusableFunctions(); // Create an instance
            int intValue = reusableFunctions_temp.To_Int(value); // Call the non-static method
            return intValue / 1000.0; // Return the converted value
        }else{
            return reusableFunctions.String_To_Double(value);
        }

    }

 */

    public void createBitmap(Uri uri) {

        Log.d(TAG, "5");
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                ImageData = scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 1200);

            } catch (IOException e) {
                Log.d("upload", "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("upload", "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }

    }

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        Log.d(TAG, "6");
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    public void Search_Button_Pressed(View view) {
        Log.d(TAG, "7");
        Log.d(TAG, "Search_Button_Pressed");
        if(!(binding.SearchBar.getText().toString().equals(""))){
            binding.BreakfastButton.setVisibility(View.VISIBLE);
            binding.LunchButton.setVisibility(View.VISIBLE);
            binding.DinnerButton.setVisibility(View.VISIBLE);
            binding.minus1Button.setVisibility(View.VISIBLE);
            binding.plus1Button.setVisibility(View.VISIBLE);
            binding.QuantityMultiplierTextview.setVisibility(View.VISIBLE);

            binding.progressBar.setVisibility(View.VISIBLE);
            Food_Item = binding.SearchBar.getText().toString();
            connectNutrionix(Food_Item);
            input_method_manager.hideSoftInputFromWindow(binding.SearchBar.getWindowToken(), 0);

        }else{
            Toast.makeText(MainActivity2.this, "Product Name Required", Toast.LENGTH_SHORT).show();
        }
    }

    public void Save_Button_Pressed(String Meal_Type) {
        Log.d(TAG, "Save Button Pressed");
        String Food_Name = binding.foodNameR.getText().toString();
        String Portion_Size = binding.servingSizeR.getText().toString();
        String Calories = binding.calriesR.getText().toString();
        String Fat = binding.totalFatR.getText().toString();
        String Cholesterol = binding.cholesterolR.getText().toString();
        String Sodium = binding.sodiuimR.getText().toString();
        String Potassium = binding.potassiumR.getText().toString();
        String Total_Carbs = binding.totalCarbonhydrateR.getText().toString();
        String Sugar = binding.sugarR.getText().toString();
        String Dietary_Fiber = binding.dietaryFiberR.getText().toString();
        String Protein = binding.proteinR.getText().toString();

        Log.d(TAG, "Vars made");

        ArrayList<String> Food_Arraylist = new ArrayList();
        ArrayList<String> Name_Date_Food_Arraylist = new ArrayList<>();

        Food_Arraylist.add(Food_Name);
        Food_Arraylist.add(Portion_Size);
        Food_Arraylist.add(Calories);
        Food_Arraylist.add(Fat);
        Food_Arraylist.add(Cholesterol);
        Food_Arraylist.add(Sodium);
        Food_Arraylist.add(Potassium);
        Food_Arraylist.add(Total_Carbs);
        Food_Arraylist.add(Sugar);
        Food_Arraylist.add(Dietary_Fiber);
        Food_Arraylist.add(Protein);

        Log.d(TAG, "Foods Added");
        // Index 0: Age (in years), Index 1: Email address, Index 2: Height (in centimeters)
        // Index 3: Password, Index 4: Bmr, Index 5: Sex
        // Index 6: Username, Index 7: Weight (in kilograms)

        Log.d(TAG, Username);

        Name_Date_Food_Arraylist.add(Username);
        Name_Date_Food_Arraylist.add(reusableFunctions.Date_Creator());
        Name_Date_Food_Arraylist.addAll(Food_Arraylist);
        Name_Date_Food_Arraylist.add(Meal_Type);

        Intent Page_Movement_Intent = new Intent(MainActivity2.this, Database.class);
        Page_Movement_Intent.putExtra("Sent_Info",Name_Date_Food_Arraylist);
        Page_Movement_Intent.putExtra("Sent_From",2);
        startActivity(Page_Movement_Intent);
    }

    public void Add_Breakfast_Button(View view) {
        Meal_Type = "Breakfast";
        if(Is_Data_Entered()){
            Buttons_Invisble();
            Save_Button_Pressed(Meal_Type);
        }
    }

    public void Add_Lunch_Button(View view) {
        Meal_Type = "Lunch";
        if(Is_Data_Entered()){
            Buttons_Invisble();
            Save_Button_Pressed(Meal_Type);
        }
    }

    public void Add_Dinner_Button(View view) {
        Meal_Type = "Dinner";

        if(Is_Data_Entered()){
            Buttons_Invisble();
            Save_Button_Pressed(Meal_Type);

        }

    }

    private boolean Is_Data_Entered(){
        if(binding.SearchBar.getText().equals("")){
            reusableFunctions.Create_Toast(getApplicationContext(), "Please Enter Data");
            return false;
        }else{
            return true;
        }

    }

    private void Buttons_Invisble(){
        binding.BreakfastButton.setVisibility(View.INVISIBLE);
        binding.LunchButton.setVisibility(View.INVISIBLE);
        binding.DinnerButton.setVisibility(View.INVISIBLE);
    }

    public void Add_Button_Pressed(View view) {
        Quantity_Multiplier = Quantity_Multiplier + 1;
        String string_Quantity_Multiplier = Integer.toString(Quantity_Multiplier);
        binding.QuantityMultiplierTextview.setText(string_Quantity_Multiplier);
        Log.d(TAG, "Textview Changed");
        Search_Button_Pressed(view);


    }

    public void Minus_Button_Pressed(View view) {

        if(Quantity_Multiplier == 0){
            Log.d(TAG, "Quantity Multiplier = 0");
            reusableFunctions.Create_Toast(MainActivity2.this, "Cannot have under 0 portions");
        }else{
            Quantity_Multiplier = Quantity_Multiplier - 1;
        }
        String string_Quantity_Multiplier = Integer.toString(Quantity_Multiplier);
        binding.QuantityMultiplierTextview.setText(string_Quantity_Multiplier);
        Log.d(TAG, "Textview Changed");
        Search_Button_Pressed(view);
    }


    public class msyncTask extends AsyncTask<Uri,String,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Uri... params) {
            createBitmap(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            callNutrionix(Nutritional_Values_Arraylist, Index_Of_Nutritional_Values_Arraylist);
        }

    }

}

