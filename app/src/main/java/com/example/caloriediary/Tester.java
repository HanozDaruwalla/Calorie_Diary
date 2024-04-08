package com.example.caloriediary;

import com.google.firebase.database.PropertyName;

public class Tester extends User{
    /*
    @PropertyName("Username")
    String Username = "Undeclared";
    @PropertyName("Password")
    String Password = "Undeclared";

    String Password2 = "Undeclared";
    @PropertyName("Sex")
    String Sex = "Undeclared";
    @PropertyName("Email")
    String Email = "Undeclared";
    @PropertyName("Height_Cm")
    String Height_cm = "Undeclared";
    @PropertyName("Weight")
    String Weight_Kg = "Undeclared";
    @PropertyName("Rmi")
    String Rmi = "Undeclared";
    */
    @PropertyName("Age")
    int Age = -1;

    @PropertyName("Email")
    String Email = "Undeclared";

    @PropertyName("Height_Cm")
    String Height_Cm = "Undeclared";

    @PropertyName("Password")
    String Password = "Undeclared1";

    @PropertyName("Rmi")
    String Rmi = "Undeclared";

    @PropertyName("isMale")
    boolean isMale = false; //default

    @PropertyName("Username")
    String Username = "Undeclared1";

    @PropertyName("Weight")
    String Weight_Kg = "Undeclared";

    public Tester() {
        Username = "Tester";
        Password = "Tester123*";
        Email = "Hanozdaru@outlook.com";
        isMale = true; // default
        String Height_cm = "Undeclared";
        String Weight_Kg = "Undeclared";
        String Bmi = "Undeclared";
    }

    @Override
    public int getAge() {
        return Age;
    }

    @Override
    public void setAge(int age) {
        Age = age;
    }

    @Override
    public String getEmail() {
        return Email;
    }

    @Override
    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String getHeight_Cm() {
        return Height_Cm;
    }

    @Override
    public void setHeight_Cm(String height_Cm) {
        Height_Cm = height_Cm;
    }

    @Override
    public String getPassword() {
        return Password;
    }

    @Override
    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String getRmi() {
        return Rmi;
    }

    @Override
    public void setRmi(String rmi) {
        Rmi = rmi;
    }

    @Override
    public boolean isMale() {
        return isMale;
    }

    @Override
    public void setMale(boolean male) {
        isMale = male;
    }

    @Override
    public String getUsername() {
        return Username;
    }

    @Override
    public void setUsername(String username) {
        Username = username;
    }

    @Override
    public String getWeight_Kg() {
        return Weight_Kg;
    }

    @Override
    public void setWeight_Kg(String weight_Kg) {
        Weight_Kg = weight_Kg;
    }


}




