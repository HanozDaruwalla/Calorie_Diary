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

    @PropertyName("Email")
    String Email = "Undeclared";

    @PropertyName("Height_Cm")
    String Height_Cm = "Undeclared";

    @PropertyName("Password")
    String Password = "Undeclared1";

    @PropertyName("Rmi")
    String Rmi = "Undeclared";

    @PropertyName("Sex")
    String Sex = "Undeclared";

    @PropertyName("Username")
    String Username = "Undeclared1";

    @PropertyName("Weight")
    String Weight_Kg = "Undeclared";

    public Tester() {
        Username = "Tester";
        Password = "Tester123*";
        Email = "Hanozdaru@outlook.com";
        Sex = "Male";
        String Height_cm = "Undeclared";
        String Weight_Kg = "Undeclared";
        String Bmi = "Undeclared";
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getEmail() {
        return Email;
    }

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
    public String getWeight_Kg() {
        return Weight_Kg;
    }

    @Override
    public void setWeight_Kg(String weight_Kg) {
        Weight_Kg = weight_Kg;
    }

    public String getRmi() {
        return Rmi;
    }

    public void setRmi(String rmi) {
        Rmi = rmi;
    }


}




