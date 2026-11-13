package com.example.caloriediary.Api_Refactored.My_Functions;

public class Nutrition_Data_Template {
    public Nutrition_Data_Template(String foodName, String photoUrl, String servingSize, int calories, int protein, int totalfat, int sugar, int totalCarbonhydrate, int sodium, int cholesterol, int potassium, int diertaryFiber) {
        this.foodName = foodName;
        this.photoUrl = photoUrl;
        this.servingSize = servingSize;
        this.calories = calories;
        this.protein = protein;
        this.totalfat = totalfat;
        this.sugar = sugar;
        this.totalCarbonhydrate = totalCarbonhydrate;
        this.sodium = sodium;
        this.cholesterol = cholesterol;
        this.potassium = potassium;
        this.diertaryFiber = diertaryFiber;
    }

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
    Nutrition_Data_Universal Nutri_Universal = new Nutrition_Data_Universal();

    public Nutrition_Data_Template(String Imported_Food_Name) {

        if(Imported_Food_Name == ""){
            this.foodName = "ENTER FOOD NAME!";
        }else{
            this.foodName = Imported_Food_Name;
        }

        this.photoUrl = "";
        this.calories = 0;
        this.protein = 0;
        this.totalfat = 0;
        this.sugar = 0;
        this.totalCarbonhydrate = 0;
        this.sodium = 0;
        this.cholesterol = 0;
        this.potassium = 0;
        this.diertaryFiber = 0;
        //                                               servingQty  servingUnit  servingWeight
        this.servingSize = Nutrition_Data_Universal.Serving_Size_Maker("0", "g", "0");
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
