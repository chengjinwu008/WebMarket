package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/5/5.
 */
public class SharedPreferenceUtil {
    private Context context;
    private SharedPreferences preferences;

    public static final String ACCOUNT ="account";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";

    public SharedPreferenceUtil(Context context,String name) {
        this.context = context;
        preferences = context.getApplicationContext().getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public String readString(String key){
        return preferences.getString(key,null);
    }

    public void putString(String[] keys,String[] values){
        if(keys==null||values==null)
            return;
        if(keys.length!=values.length)
            return;
        SharedPreferences.Editor editor = preferences.edit();

        for(int i = 0 ;i<keys.length;i++){
            editor.putString(keys[i],values[i]);
        }

        editor.apply();
    }
}
