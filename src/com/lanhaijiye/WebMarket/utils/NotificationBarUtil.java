package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015/5/4.
 */
public class NotificationBarUtil {
    public static void collapseStatusBar(Context context) {
        try {
            Object statusBarManager = context.getSystemService("statusbar");
            Method collapse;
            if (Build.VERSION.SDK_INT <= 16) {
                collapse = statusBarManager.getClass().getMethod("collapse");
            } else {
                collapse = statusBarManager.getClass().getMethod("collapsePanels");
            }
            collapse.invoke(statusBarManager);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}
