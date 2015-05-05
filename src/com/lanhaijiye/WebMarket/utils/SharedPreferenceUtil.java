package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/5/5.
 */
public class SharedPreferenceUtil {
    private Context context;
    private SharedPreferences preferences;

    public SharedPreferenceUtil(Context context,String name) {
        this.context = context;
        preferences = context.getApplicationContext().getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public String readString(String key){
        return preferences.getString(key,null);
    }
}
