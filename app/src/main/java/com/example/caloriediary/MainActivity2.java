package com.example.caloriediary;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
    // constants
    //nutionIX api reference

    final static String TAG =  "MainActivity2";

    private ActivityMain2Binding binding;
    final String API_KEY = "d016fd33a6081cbb9abd91a7e6cce48a";
    final String APP_ID = "3747b1e6";
    final String REQUEST_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";

    // variables
    EditText mEditText;
    TextView foodName, foodNameTextBox, calories, protein, totalfat, sugar, servingSize,
            totalCarbonhydrate, sodium, cholesterol, potassium, diertaryFiber;
    ProgressBar mProgressBar;
    NutritionData data;
    String product;
    InputMethodManager mgr;
    int arrListIndex;
    ArrayList<String> mArrayList;
    Bitmap mBitmap;

    RelativeLayout mRelativeLayout;
    ScrollView mScrollView;

    ImageView foodPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Activity Loaded");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            binding = ActivityMain2Binding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            mEditText = (EditText) findViewById(R.id.Search_Bar);
            foodName = (TextView) findViewById(R.id.food_name_r);
            foodNameTextBox = (TextView) findViewById(R.id.food_name);
            calories = (TextView) findViewById(R.id.calries_r);
            protein = (TextView) findViewById(R.id.protein_r);
            totalfat = (TextView) findViewById(R.id.total_fat_r);
            sugar = (TextView) findViewById(R.id.sugar_r);
            servingSize = (TextView) findViewById(R.id.serving_size_r);
            totalCarbonhydrate = (TextView) findViewById(R.id.total_carbonhydrate_r);
            sodium = (TextView) findViewById(R.id.sodiuim_r);
            cholesterol = (TextView) findViewById(R.id.cholesterol_r);
            potassium = (TextView) findViewById(R.id.potassium_r);
            diertaryFiber = (TextView) findViewById(R.id.dietary_fiber_r);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            mProgressBar.setVisibility(View.INVISIBLE);
            mgr = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            foodPic = (ImageView) findViewById(R.id.foodPic);
            mScrollView = (ScrollView)findViewById(R.id.mscrollView);

            mRelativeLayout =(RelativeLayout)findViewById(R.id.relativeLayout);
            Log.d(TAG, "OnCreate done");

            //get data from homaPage
            Intent i = getIntent();
            if(i.getExtras()!=null) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                String str = i.getExtras().getString("BitmapUri");
                Uri uri = Uri.parse(str);
                mArrayList = i.getExtras().getStringArrayList("StringArrList");
                Toast.makeText(this, mArrayList.get(arrListIndex), Toast.LENGTH_SHORT);
                arrListIndex =0;
                new msyncTask().execute(uri);
            }else{
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }



            return insets;
        });
/*
// Set a focus change listener on the edit text field to always make the cursor visible when it gains focus.
mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mEditText.setCursorVisible(true);
    }
});

// Set a touch listener on the scroll view to handle swipe gestures.
mScrollView.setOnTouchListener(new OnSwipeTouchListener(this){
    // If a swipe to the right is detected, start the HomePage activity and finish the current activity.
    public void onSwipeRight() {
        Intent i = new Intent(getBaseContext(), HomePage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
});

 */
    }

    //API CALLS Method
    private void connectNutrionix(String foodName) {
        StringEntity stringEntity = null;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-app-id", APP_ID);
        client.addHeader("x-app-key", API_KEY);
        client.addHeader("x-remote-user-id", "0");
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("query", foodName);

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
                        mProgressBar.setVisibility(View.INVISIBLE);
                        data = NutritionData.fromJson(response);
                        mEditText.setCursorVisible(false);
                        new DownloadImageTask(foodPic)
                                .execute(data.getPhotoUrl());
                        foodPic.setVisibility(View.VISIBLE);

                        updateUI(data);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Healthier", "failed" + errorResponse.toString());
                        Toast.makeText(MainActivity2.this, "Sorry, We couldn't match any of your foods",
                                Toast.LENGTH_SHORT).show();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mEditText.setCursorVisible(false);

                    }
                });

    }

    private void callNutrionix(ArrayList<String> arrayList,int index) {

        StringEntity stringEntity = null;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-app-id", APP_ID);
        client.addHeader("x-app-key", API_KEY);
        client.addHeader("x-remote-user-id", "0");
        JSONObject jsonObject = new JSONObject();
        mProgressBar.setVisibility(View.VISIBLE);

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
                        mProgressBar.setVisibility(View.INVISIBLE);
                        data = NutritionData.fromJson(response);
                        mEditText.setCursorVisible(false);
                        foodPic.setImageBitmap(mBitmap);
                        foodPic.setVisibility(View.VISIBLE);

                        updateUI(data);
                    }

                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Healthier", "failed" + errorResponse.toString());

                        arrListIndex++;
                        if(arrListIndex > mArrayList.size()-1) {
                            Toast.makeText(MainActivity2.this, "Sorry,We couldn't match any of your foods",
                                    Toast.LENGTH_SHORT).show();

                            mProgressBar.setVisibility(View.INVISIBLE);
                            mEditText.setCursorVisible(false);
                        }else {

                            callNutrionix(mArrayList,arrListIndex);

                        }

                    }
                });
    }

    private void updateUI(NutritionData nutritionData) {
        foodName.setText(nutritionData.getFoodName());

        calories.setText(String.valueOf(nutritionData.getCalories()));

        protein.setText(String.valueOf(nutritionData.getProtein()) + "g");
        totalfat.setText(String.valueOf(nutritionData.getTotalfat()) + "g");
        sugar.setText(String.valueOf(nutritionData.getSugar()) + "g");
        servingSize.setText(nutritionData.getServingSize());
        totalCarbonhydrate.setText(String.valueOf(nutritionData.getTotalCarbonhydrate() + "g"));
        sodium.setText(String.valueOf(nutritionData.getSodium()) + "mg");
        cholesterol.setText(String.valueOf(nutritionData.getCholesterol()) + "mg");
        potassium.setText(String.valueOf(nutritionData.getPotassium()) + "mg");
        diertaryFiber.setText(nutritionData.getDiertaryFiber() + "g");
        mRelativeLayout.setVisibility(View.VISIBLE);

    }

    public void createBitmap(Uri uri) {

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

        Log.d(TAG, "Search_Button_Pressed");
        if(!(binding.SearchBar.getText().toString().equals(""))){
            mProgressBar.setVisibility(View.VISIBLE);
            product = mEditText.getText().toString();
            connectNutrionix(product);
            mgr.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);

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
