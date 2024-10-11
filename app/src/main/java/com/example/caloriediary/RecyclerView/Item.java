package com.example.caloriediary.RecyclerView;

// Item.java
public class Item {

    public Item(String food_name, int calories, int total_fat, String serving_size) {
        this.Food_Name = food_name;
        this.Calories = calories;
        this.Fat = total_fat;
        this.Servings = serving_size;
    }

    private String Food_Name;
    private int Calories;
    private int Fat;
    private String Servings;

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
