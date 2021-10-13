package com.example.taskapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences sharedPreferences;
    public  Prefs(Context context){
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    }
    public   void  saveBoardState(){
        sharedPreferences.edit().putBoolean("isBoardShown",true).apply();
    }
    public  boolean isBoardShown(){
        return  sharedPreferences.getBoolean("isBoardShown",false);
    }
}
