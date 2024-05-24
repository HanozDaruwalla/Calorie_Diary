package com.example.caloriediary.Creating_Account_And_Login;

import com.google.firebase.database.PropertyName;

public class User {

    @PropertyName(("Age"))
    int Age = -1;

    @PropertyName("Email")
    String Email = "Undeclared";

    @PropertyName("Height_Cm")
    String Height_Cm = "Undeclared";

    @PropertyName("Password")
    String Password = "Undeclared";

    @PropertyName("Rmi")
    String Rmi = "Undeclared";

    @PropertyName("isMale")
    boolean isMale = true; //default

    @PropertyName("Username")
    String Username = "Undeclared";

    @PropertyName("Weight")
    String Weight_Kg = "Undeclared";



    //HEIGHT/ WEIGHT @ BMI NOT ADDED TO CONSTRUCTER. (ISNT NEEDED WHEN CREATING ACCOUNT)
    public User(String username, String password, String password2, Boolean is_male, String email) {
        Username = username;
        Password = password;
        isMale = is_male;
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

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

}


