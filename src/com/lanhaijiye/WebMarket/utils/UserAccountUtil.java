package com.lanhaijiye.WebMarket.utils;

import android.content.Context;

/**
 * Created by android on 2015/5/8.
 */
public class UserAccountUtil {

    public static boolean getUserState(Context context) {
        SharedPreferenceUtil preferences = new SharedPreferenceUtil(context, SharedPreferenceUtil.ACCOUNT);
        String userName = preferences.readString(SharedPreferenceUtil.USERNAME_KEY);
        return !(userName == null || "".equals(userName));
    }

}
