package com.example.caloriediary.RecyclerView;

// Item.java
public class Item {


    public Item(String food_Name, int calories, int fat, String servings, int sugar, int protein) {
        Food_Name = food_Name;
        Calories = calories;
        Fat = fat;
        Servings = servings;
        Sugar = sugar;
        Protein = protein;
    }

    private String Food_Name;
    private int Calories;
    private int Fat;
    private String Servings;

    public int getSugar() {
        return Sugar;
    }

    public void setSugar(int sugar) {
        Sugar = sugar;
    }

    public int getProtein() {
        return Protein;
    }

    public void setProtein(int protein) {
        Protein = protein;
    }

    private int Sugar;
    private int Protein;


    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        this.Food_Name = food_Name;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        this.Calories = calories;
    }

    public int getFat() {
        return Fat;
    }

    public void setFat(int fat) {
        this.Fat = fat;
    }

    public String getServings() {
        return Servings;
    }

    public void setServings(String servings) {
        this.Servings = servings;
    }


}
