package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.lanhaijiye.WebMarket.CommonDataObject;

import java.util.Date;

/**
 * Created by Administrator on 2015/5/5.
 */
public class SharedPreferenceUtil {
    private SharedPreferences preferences;

    public static final String ACCOUNT ="account";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String STATE_TOKEN_KEY = "token";
    public static final String STATE_PLATFORM = "platform";
    public static final String STATE_PLATFORM_ID = "platform_id";
    public static final String STATE_SESSION_ID="session_id";
    public static final String STATE_USER_ID ="user_id";
    public static final String STATE_SESSION_TIME="session_time";
    public static final long TIME=3600L*24L*30L*1000L;


    public SharedPreferenceUtil(Context context,String name) {
        preferences = context.getApplicationContext().getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public String readString(String key){
        if(STATE_SESSION_ID.equals(key)){
            String string = readString(STATE_SESSION_TIME);
            if(string==null){
                delete(new String[]{
                        USERNAME_KEY,PASSWORD_KEY,STATE_TOKEN_KEY,STATE_SESSION_ID,STATE_USER_ID,STATE_SESSION_TIME
                });
                return null;
            }
            Date date = new Date();
            long deltaTime = date.getTime()-Long.valueOf(string);
            if(deltaTime>TIME){
                delete(new String[]{
                        USERNAME_KEY,PASSWORD_KEY,STATE_TOKEN_KEY,STATE_SESSION_ID,STATE_USER_ID,STATE_SESSION_TIME
                });
                return null;
            }
        }
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
            if(keys[i].equals(STATE_SESSION_ID)){
                editor.putString(STATE_SESSION_TIME, String.valueOf(new Date().getTime()));
                CommonDataObject.sessionId=values[i];
            }else if(keys[i].equals(STATE_USER_ID)){
                CommonDataObject.userId=values[i];
            }
        }

        editor.apply();
    }

    public void delete(String[] keys){
        if(keys==null)
            return;
        if(keys.length==0)
            return;
        SharedPreferences.Editor editor = preferences.edit();

        for (String key : keys) {
            editor.remove(key);
            if(key.equals(STATE_SESSION_ID)){
                CommonDataObject.sessionId=null;
            }else if(key.equals(STATE_USER_ID)){
                CommonDataObject.userId=null;
            }
        }

        editor.apply();
    }
}
