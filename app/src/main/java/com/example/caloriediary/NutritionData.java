package com.example.caloriediary;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Api Created by yjj781265 on 7/27/2017.
 */

public class NutritionData {
    private String Name_Of_Food, Url_Of_Photo, Serving_Size;
    private int Calories, Protein, Total_Fat, Sugar, Total_Carbs, Sodium, Cholesterol, Potassium, Fiber;
    private static final int NULL_VALUE = 0;


    public static NutritionData Gather_Data_From_Json(JSONObject Json_Obj) {
        NutritionData nutritional_data = new NutritionData();
        try {
            JSONObject foods = Json_Obj.getJSONArray("foods").getJSONObject(0);
            nutritional_data.Name_Of_Food = foods.getString("Name_Of_Food");
            nutritional_data.Serving_Size = Serving_Size_Maker(foods);
            if (foods.get("nf_Calories") == null) {//was nf_calories
                nutritional_data.Calories = NULL_VALUE;
            } else {
                nutritional_data.Calories = foods.getInt("nf_Calories"); //was nf_calories
            }
            nutritional_data.Protein = foods.getInt("nf_Protein");// was nf_protein

            nutritional_data.Total_Fat = foods.getInt("nf_Total_Fat");//was nf_total_fat

            if (foods.getString("nf_Sugar").equals("null")) {//was nf_sugars
                nutritional_data.Sugar = NULL_VALUE;
            } else {
                nutritional_data.Sugar = foods.getInt("nf_Sugar");//was nf_sugars
            }

            //check below for old values
            nutritional_data.Total_Carbs = foods.getInt("nf_Total_Carbs");
            nutritional_data.Sodium = foods.getInt("nf_Sodium");
            nutritional_data.Cholesterol = foods.getInt("nf_cholesterol");
            nutritional_data.Potassium = foods.getInt("nf_Potassium");
            nutritional_data.Fiber = foods.getInt("nf_fiber");
            nutritional_data.Url_Of_Photo = foods.getJSONObject("photo").getString("highres");

            /*
            nutritional_data.Total_Carbs = foods.getInt("nf_total_carbohydrate");
            nutritional_data.Sodium = foods.getInt("nf_sodium");
            nutritional_data.Cholesterol = foods.getInt("nf_cholesterol");
            nutritional_data.Potassium = foods.getInt("nf_potassium");
            nutritional_data.Fiber = foods.getInt("nf_dietary_fiber");
            nutritional_data.Url_Of_Photo = foods.getJSONObject("photo").getString("highres");
             */

            Log.d("Success JSON", nutritional_data.Name_Of_Food + " " + nutritional_data.Serving_Size);

        } catch (JSONException e) {

            Log.d("JSON response", e.toString());

        }

        return nutritional_data;

    }

    private static String Serving_Size_Maker(JSONObject foods) {
        String Serving_Size;
        String Serving_Qty, Serving_Unit, Serving_Weight;


        try {
            Serving_Qty = String.valueOf(foods.getInt("Serving_Qty"));//was serving_qty
            Serving_Unit = foods.getString("Serving_Unit"); // was serving_unit
            Serving_Weight = String.valueOf(foods.getString("Serving_Weight_Grams"));// was serving_weight_grams

        } catch (JSONException e) {
            Serving_Size = "Not Found!";
            return Serving_Size;
        }
        Serving_Size = Serving_Qty + " " + Serving_Unit + "(" + Serving_Weight + "G" + ")";
        return Serving_Size;

    }

    public String getName_Of_Food() {
        return Name_Of_Food;
    }

    public void setName_Of_Food(String name_Of_Food) {
        Name_Of_Food = name_Of_Food;
    }

    public String getUrl_Of_Photo() {
        return Url_Of_Photo;
    }

    public void setUrl_Of_Photo(String url_Of_Photo) {
        Url_Of_Photo = url_Of_Photo;
    }

    public String getServing_Size() {
        return Serving_Size;
    }

    public void setServing_Size(String serving_Size) {
        Serving_Size = serving_Size;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getProtein() {
        return Protein;
    }

    public void setProtein(int protein) {
        Protein = protein;
    }

    public int getTotal_Fat() {
        return Total_Fat;
    }

    public void setTotal_Fat(int total_Fat) {
        Total_Fat = total_Fat;
    }

    public int getSugar() {
        return Sugar;
    }

    public void setSugar(int sugar) {
        Sugar = sugar;
    }

    public int getTotal_Carbs() {
        return Total_Carbs;
    }

    public void setTotal_Carbs(int total_Carbs) {
        Total_Carbs = total_Carbs;
    }

    public int getSodium() {
        return Sodium;
    }

    public void setSodium(int sodium) {
        Sodium = sodium;
    }

    public int getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        Cholesterol = cholesterol;
    }

    public int getPotassium() {
        return Potassium;
    }

    public void setPotassium(int potassium) {
        Potassium = potassium;
    }

    public int getFiber() {
        return Fiber;
    }

    public void setFiber(int fiber) {
        Fiber = fiber;
    }

}


