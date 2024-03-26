package com.example.caloriediary;

public class User {

    String Username, Password, Password2, Email = "Undeclared";

    public User(String username, String password, String password2, String email) {
        Username = username;
        Password = password;
        Password2 = password2;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

}


