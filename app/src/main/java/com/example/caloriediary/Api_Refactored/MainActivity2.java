package com.example.caloriediary.Api_Refactored;

import android.content.Intent;
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

    private ActivityMain2Binding binding;

    ReusableFunctions reusableFunctions = new ReusableFunctions();

    //api data
    final String API_KEY = "d016fd33a6081cbb9abd91a7e6cce48a";
    final String APP_ID = "3747b1e6";
    final String REQUEST_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    // variables

    NutritionData data;
    String product;
    InputMethodManager mgr;
    int arrListIndex;
    ArrayList<String> mArrayList;
    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity Loaded");
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main2);
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);



            binding.progressBar.setVisibility(View.INVISIBLE);
            mgr = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);

            Log.d(TAG, "OnCreate done");

            //get data from homePage
            Intent i = getIntent();
            if(i.getExtras()!=null) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                String str = i.getExtras().getString("BitmapUri");
                Uri uri = Uri.parse(str);
                mArrayList = i.getExtras().getStringArrayList("StringArrList");

                Log.d(TAG, "1");
                reusableFunctions.Create_Toast(getApplicationContext(), mArrayList.get(arrListIndex) );
                arrListIndex =0;
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
                        Log.d("Healthier", "Success" + response.toString());
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        data = NutritionData.fromJson(response);

                        new DownloadImageTask(binding.foodPic).execute(data.getPhotoUrl());
                        binding.foodPic.setVisibility(View.VISIBLE);

                        updateUI(data);
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
                        data = NutritionData.fromJson(response);

                        binding.foodPic.setImageBitmap(mBitmap);
                        binding.foodPic.setVisibility(View.VISIBLE);

                        updateUI(data);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Healthier", "failed" + errorResponse.toString());

                        arrListIndex++;
                        if(arrListIndex > mArrayList.size()-1) {
                            Toast.makeText(MainActivity2.this, "Sorry,We couldn't match any of your foods",
                                    Toast.LENGTH_SHORT).show();

                            binding.progressBar.setVisibility(View.INVISIBLE);

                        }else {
                            callNutrionix(mArrayList,arrListIndex);

                        }

                    }
                });
    }

    private void updateUI(NutritionData nutritionData) {
        Log.d(TAG, "4");
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

    }

    public void createBitmap(Uri uri) {

        Log.d(TAG, "5");
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                mBitmap = scaleBitmapDown(MediaStore.Images.Media.getBitmap(getContentResolver(), uri), 1200);

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
            binding.progressBar.setVisibility(View.VISIBLE);
            product = binding.SearchBar.getText().toString();
            connectNutrionix(product);
            mgr.hideSoftInputFromWindow(binding.SearchBar.getWindowToken(), 0);

        }else{
            Toast.makeText(MainActivity2.this, "Product Name Required", Toast.LENGTH_SHORT).show();
        }
    }


    public class msyncTask extends AsyncTask<Uri,String,ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Uri... params) {
            createBitmap(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            callNutrionix(mArrayList,arrListIndex);
        }

    }

}
