package com.lanhaijiye.WebMarket;

import android.app.Application;
import android.telephony.TelephonyManager;
import com.google.gson.Gson;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;

/**
 * Created by android on 2015/6/11.
 */
public class MyApplication extends Application {
    public String imei;
    public Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        imei =((TelephonyManager)getApplicationContext().getSystemService(TELEPHONY_SERVICE)).getDeviceId();
        CommonDataObject.applicationContext = getApplicationContext();
        CommonDataObject.IMEI=imei;
        gson=new Gson();
        CommonDataObject.GSON =gson;
        //初始化的时候应该读取是否有保存sessionid和userid
        SharedPreferenceUtil util =new SharedPreferenceUtil(getApplicationContext(),SharedPreferenceUtil.ACCOUNT);
        CommonDataObject.sessionId = util.readString(SharedPreferenceUtil.STATE_SESSION_ID);
        CommonDataObject.userId = util.readString(SharedPreferenceUtil.STATE_USER_ID);
    }
}
