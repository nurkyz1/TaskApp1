package com.example.taskapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {
    private SharedPreferences sharedPreferences;
    public  static  final  String FOR_NAME = "NAME";
    public  static  final String FOR_IMAGE = "IMAGE";

    public  Prefs(Context context){
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    }
    public   void  saveBoardState(){
        sharedPreferences.edit().putBoolean("isBoardShown",true).apply();
    }
    public  boolean isBoardShown(){
        return  sharedPreferences.getBoolean("isBoardShown",false);
    }
    public String getForName(){
        return sharedPreferences.getString(FOR_NAME,"");
    }
    public  void  setForName (String name){
        sharedPreferences.edit().putString(FOR_NAME, name).apply();
    }
    public  void  setForImage(Uri image){
        sharedPreferences.edit().putString("image",image.toString()).apply();
    }
    public String getForImage(){
        return sharedPreferences.getString("image"," ");
    }
}
