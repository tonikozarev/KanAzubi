package com.example.techgeek.kanazubi.Session;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context c;

    public Session(Context c){
        this.c = getContext();
        preferences = c.getSharedPreferences("KanAzubi", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public void saveEmail(String email){
        preferences.edit().putString("email", email).apply();
    }

    public String getEmail(){
        return preferences.getString("email", "");
    }

    public void saveUserName(String name){
        preferences.edit().putString("userName", name).apply();
    }

    public String getUserName(){
        return preferences.getString("userName", "");
    }

    public void saveId(int id){
        preferences.edit().putInt("userID", id).apply();
    }

    public int getId(){
        return preferences.getInt("userID", 0);
    }

    public void setLogIn(boolean logIn){
        editor.putBoolean("loggedIn", logIn);
        editor.commit();
    }

    public boolean loggedIn(){
        return preferences.getBoolean("loggedIn", false);
    }

    private Context getContext() {
        return c;
    }
}
