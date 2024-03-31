package com.example.caloriediary;

public class Tester extends User{
    String Username, Password, Password2, Email = "Undeclared";

    public Tester() {
        Username = "Tester";
        Password = "Tester123*";
        Email = "hanozdaru@outlook.com";
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}




