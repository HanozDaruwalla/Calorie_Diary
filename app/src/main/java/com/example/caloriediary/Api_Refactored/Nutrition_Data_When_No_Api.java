package com.example.caloriediary.Api_Refactored;

public class Nutrition_Data_When_No_Api {
    private String foodName, photoUrl, servingSize;
    private int calories;
    private int protein;
    private int totalfat;
    private int sugar;
    private int totalCarbonhydrate;
    private int sodium;
    private int cholesterol;
    private int potassium;
    private int diertaryFiber;

    private static final int NULL_VALUE =0;

    public Nutrition_Data_When_No_Api(String Imported_Food_Name) {

        if(Imported_Food_Name == ""){
            foodName = "ENTER FOOD NAME!";
        }else{
            foodName = Imported_Food_Name;
        }

        photoUrl = "";
        servingSize = "0 Cup (0g) [x1]";
        calories = 0;
        protein = 0;
        totalfat = 0;
        sugar = 0;
        totalCarbonhydrate = 0;
        sodium = 0;
        cholesterol = 0;
        potassium = 0;
        diertaryFiber = 0;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getTotalfat() {
        return totalfat;
    }

    public void setTotalfat(int totalfat) {
        this.totalfat = totalfat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getSugar() {
        return sugar;
    }

    public void setSugar(int sugar) {
        this.sugar = sugar;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getTotalCarbonhydrate() {
        return totalCarbonhydrate;
    }

    public void setTotalCarbonhydrate(int totalCarbonhydrate) {
        this.totalCarbonhydrate = totalCarbonhydrate;
    }

    public int getDiertaryFiber() {
        return diertaryFiber;
    }

    public void setDiertaryFiber(int diertaryFiber) {
        this.diertaryFiber = diertaryFiber;
    }

    public int getPotassium() {
        return potassium;
    }

    public void setPotassium(int potassium) {
        this.potassium = potassium;
    }
}
