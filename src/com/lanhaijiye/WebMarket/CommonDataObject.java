package com.lanhaijiye.WebMarket;

import android.content.Context;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.fragments.MainUIFragment;
import com.lanhaijiye.WebMarket.fragments.UserCenterFragment;
import com.lanhaijiye.WebMarket.fragments.inter.Reloadable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by android on 2015/5/8.
 */
public class CommonDataObject {
    public static final String SCHEMA = "http";
    public static final String HOST_NAME ="xm0000004.exam.kh888.cn";
    public static final String MAIN_URL = SCHEMA+"://"+HOST_NAME;
    public static List<Reloadable> refreshWebViews=new ArrayList<>();
    public static String sessionId="asdasdasdfasdfasfafasfas";
    public static String userId=null;
    public static MainUIFragment mainUI=null;
    public static UserCenterFragment userUI=null;
    public static String IMEI=null;
    public static Gson GSON = null;
    public static Context applicationContext=null;
}
