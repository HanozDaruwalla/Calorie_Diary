package com.example.caloriediary;

public class Database_Value_Names {

    private String Db_Users_Db_Name = "Users";

    private String Db_Email_Name = "Email";
    private String Db_Height_Name = "Height_Cm";
    private String Db_Password_Name = "Password";
    private String Db_Rmi_Name = "Rmi";
    private String Db_Sex_Name = "Sex";
    private String Db_Username_Name = "Username";
    private String Db_Weight_Name = "Weight_Kg";

    public Database_Value_Names(String db_Email_Name, String db_Height_Name, String db_Password_Name, String db_Rmi_Name, String db_Sex_Name, String db_Username_Name, String db_Weight_Name) {
        Db_Email_Name = db_Email_Name;
        Db_Height_Name = db_Height_Name;
        Db_Password_Name = db_Password_Name;
        Db_Rmi_Name = db_Rmi_Name;
        Db_Sex_Name = db_Sex_Name;
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

    public String getDb_Rmi_Name() {
        return Db_Rmi_Name;
    }

    public void setDb_Rmi_Name(String db_Rmi_Name) {
        Db_Rmi_Name = db_Rmi_Name;
    }

    public String getDb_Sex_Name() {
        return Db_Sex_Name;
    }

    public void setDb_Sex_Name(String db_Sex_Name) {
        Db_Sex_Name = db_Sex_Name;
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
}
