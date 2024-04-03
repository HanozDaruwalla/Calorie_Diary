package com.example.caloriediary;

import com.google.firebase.database.PropertyName;

public class User {

    @PropertyName("Email")
    String Email = "Undeclared";

    @PropertyName("Height_Cm")
    String Height_Cm = "Undeclared";

    @PropertyName("Password")
    String Password = "Undeclared";

    @PropertyName("Rmi")
    String Rmi = "Undeclared";

    @PropertyName("Sex")
    String Sex = "Undeclared";

    @PropertyName("Username")
    String Username = "Undeclared";

    @PropertyName("Weight")
    String Weight_Kg = "Undeclared";



    //HEIGHT/ WEIGHT @ BMI NOT ADDED TO CONSTRUCTER. (ISNT NEEDED WHEN CREATING ACCOUNT)
    public User(String username, String password, String password2, String sex, String email) {
        Username = username;
        Password = password;
        Sex = sex;
        Email = email;
    }

    public User() {
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

    public String getHeight_Cm() {
        return Height_Cm;
    }

    public void setHeight_Cm(String height_Cm) {
        Height_Cm = height_Cm;
    }

    public String getWeight_Kg() {
        return Weight_Kg;
    }

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


