package com.example.woody.api;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int Mode=0;
    private static final String REFNAME="JWT_SHARED_PREFERENCE";
    private static final String TOKEN="TOKEN";
    private Context context;

    public SharedPreferences.Editor getEditor(){
        return editor;
    }
    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }
    public TokenManager(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(REFNAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveToken(String token){
        editor.putString(TOKEN, token);
        editor.apply();
    }
    public String getToken(){
        return sharedPreferences.getString(TOKEN, null);
    }
    public void removeToken(){
        editor.putString(TOKEN, null);
        editor.apply();
    }
}
