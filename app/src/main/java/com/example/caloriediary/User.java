package com.example.caloriediary;

public class User {

    String Username = "Undeclared";
    String Password = "Undeclared";
    String Password2 = "Undeclared";
    String Sex = "Undeclared";
    String Email = "Undeclared";
    String Height_cm = "Undeclared";
    String Weight_Kg = "Undeclared";
    String Rmi = "Undeclared";


    //HEIGHT/ WEIGHT @ BMI NOT ADDED TO CONSTRUCTER. (ISNT NEEDED WHEN CREATING ACCOUNT)
    public User(String username, String password, String password2, String sex, String email) {
        Username = username;
        Password = password;
        Password2 = password2;
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

    public String getPassword2() {
        return Password2;
    }

    public void setPassword2(String password2) {
        Password2 = password2;
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

    public String getHeight_cm() {
        return Height_cm;
    }

    public void setHeight_cm(String height_cm) {
        Height_cm = height_cm;
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


