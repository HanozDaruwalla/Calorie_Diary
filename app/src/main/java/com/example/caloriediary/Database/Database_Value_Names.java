package com.example.caloriediary.Database;

public class Database_Value_Names {

    //Name of the Db that stores User Data
    private String Db_Users_Db_Name = "Users";

    //Name Of Db Fields
    private String Db_Email_Name = "Email";
    private String Db_Height_Name = "Height_Cm";
    private String Db_Password_Name = "Password";
    private String Db_Bmr_Name = "Bmr";
    private String Db_isMale_Name = "isMale";
    private String Db_Username_Name = "Username";
    private String Db_Weight_Name = "Weight_Kg";

    private String Db_Age_Name = "Age";

    public Database_Value_Names(String db_Email_Name, String db_Height_Name, String db_Password_Name, String db_Bmr_Name, String db_isMale_Name, String db_Username_Name, String db_Weight_Name) {
        Db_Email_Name = db_Email_Name;
        Db_Height_Name = db_Height_Name;
        Db_Password_Name = db_Password_Name;
        Db_Bmr_Name = db_Bmr_Name;
        Db_isMale_Name = db_isMale_Name;
        Db_Username_Name = db_Username_Name;
        Db_Weight_Name = db_Weight_Name;
    }

    public Database_Value_Names(){

    }

    public String getDb_Users_Db_Name() {
        return Db_Users_Db_Name;
    }

    public void setDb_Users_Db_Name(String db_Users_Db_Name) {
        Db_Users_Db_Name = db_Users_Db_Name;
    }

    public String getDb_Email_Name() {
        return Db_Email_Name;
    }

    public void setDb_Email_Name(String db_Email_Name) {
        Db_Email_Name = db_Email_Name;
    }

    public String getDb_Height_Name() {
        return Db_Height_Name;
    }

    public void setDb_Height_Name(String db_Height_Name) {
        Db_Height_Name = db_Height_Name;
    }

    public String getDb_Password_Name() {
        return Db_Password_Name;
    }

    public void setDb_Password_Name(String db_Password_Name) {
        Db_Password_Name = db_Password_Name;
    }

    public String getDb_Bmr_Name() {
        return Db_Bmr_Name;
    }

    public void setDb_Bmr_Name(String db_Bmr_Name) {
        Db_Bmr_Name = db_Bmr_Name;
    }

    public String getDb_isMale_Name() {
        return Db_isMale_Name;
    }

    public void setDb_isMale_Name(String db_isMale_Name) {
        Db_isMale_Name = db_isMale_Name;
    }

    public String getDb_Username_Name() {
        return Db_Username_Name;
    }

    public void setDb_Username_Name(String db_Username_Name) {
        Db_Username_Name = db_Username_Name;
    }

    public String getDb_Weight_Name() {
        return Db_Weight_Name;
    }

    public void setDb_Weight_Name(String db_Weight_Name) {
        Db_Weight_Name = db_Weight_Name;
    }

    public String getDb_Age_Name() {
        return Db_Age_Name;
    }

    public void setDb_Age_Name(String db_Age_Name) {
        Db_Age_Name = db_Age_Name;
    }
}
