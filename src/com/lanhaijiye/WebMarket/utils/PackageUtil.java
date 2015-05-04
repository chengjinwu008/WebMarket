package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/4/29.
 */
public class PackageUtil {

    public static String getAppVersion(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo info =  packageManager.getPackageInfo(context.getPackageName(), 0);
        return info.versionCode+"";
    }
}
