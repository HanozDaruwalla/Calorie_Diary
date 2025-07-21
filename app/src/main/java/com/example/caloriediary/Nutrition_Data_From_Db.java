package com.example.caloriediary;

import com.example.caloriediary.Database.Database_Value_Names;
import com.google.firebase.database.PropertyName;

public class Nutrition_Data_From_Db {
//was Undefined
    @PropertyName(("Calories"))
    String Calories = "0";

    @PropertyName(("Cholesterol"))
    String Cholesterol = "0";

    @PropertyName(("Date"))
    String Date = "0";

    @PropertyName(("Dietary_Fiber"))
    String Dietary_Fiber = "0";

    @PropertyName(("Fat"))
    String Fat = "0";

    @PropertyName(("Name_Of_Food"))
    String Name_Of_Food = "No Data Added Today";

    @PropertyName(("Portion_Size"))
    String Portion_Size = "0";

    @PropertyName(("Potassium"))
    String Potassium = "0";

    @PropertyName(("Protein"))
    String Protein = "0";

    @PropertyName(("Sodium"))
    String Sodium = "0";

    @PropertyName(("Sugar"))
    String Sugar = "0";

    @PropertyName(("Total_Carbs"))
    String Total_Carbs = "0";

    @PropertyName(("Type Of Meal"))
    String Type_Of_Meal = "?";

    @PropertyName(("Username"))
    String Username = "?";

    public Nutrition_Data_From_Db() {
    }

    public Nutrition_Data_From_Db(String calories, String cholesterol, String date, String dietary_Fiber, String fat, String name_Of_Food, String portion_Size, String potassium, String protein, String sodium, String sugar, String total_Carbs, String type_Of_Meal, String username) {
        Calories = calories;
        Cholesterol = cholesterol;
        Date = date;
        Dietary_Fiber = dietary_Fiber;
        Fat = fat;
        Name_Of_Food = name_Of_Food;
        Portion_Size = portion_Size;
        Potassium = potassium;
        Protein = protein;
        Sodium = sodium;
        Sugar = sugar;
        Total_Carbs = total_Carbs;
        Type_Of_Meal = type_Of_Meal;
        Username = username;
    }


    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        Cholesterol = cholesterol;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDietary_Fiber() {
        return Dietary_Fiber;
    }

    public void setDietary_Fiber(String dietary_Fiber) {
        Dietary_Fiber = dietary_Fiber;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getName_Of_Food() {
        return Name_Of_Food;
    }

    public void setName_Of_Food(String name_Of_Food) {
        Name_Of_Food = name_Of_Food;
    }

    public String getPortion_Size() {
        return Portion_Size;
    }

    public void setPortion_Size(String portion_Size) {
        Portion_Size = portion_Size;
    }

    public String getPotassium() {
        return Potassium;
    }

    public void setPotassium(String potassium) {
        Potassium = potassium;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getSodium() {
        return Sodium;
    }

    public void setSodium(String sodium) {
        Sodium = sodium;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getTotal_Carbs() {
        return Total_Carbs;
    }

    public void setTotal_Carbs(String total_Carbs) {
        Total_Carbs = total_Carbs;
    }

    public String getType_Of_Meal() {
        return Type_Of_Meal;
    }

    public void setType_Of_Meal(String type_Of_Meal) {
        Type_Of_Meal = type_Of_Meal;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

}
