package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import com.lanhaijiye.WebMarket.CommanDataObject;

/**
 * Created by android on 2015/5/11.
 */
public class URLUtil {
    public static String getURLStr(String s, Context context) {

        StringBuilder url = new StringBuilder();
        url.append(CommanDataObject.MAIN_URL);
        url.append(s);
        SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(context, SharedPreferenceUtil.ACCOUNT);
        //获取到从服务器发送回来的密文
        String token = preferenceUtil.readString(SharedPreferenceUtil.STATE_TOKEN_KEY);
        //因为token是base64加密的，所以我要解密，然后再用md5加密返回


        if (token != null && token.trim().length() > 0) {
            token = MessageDiagestUtil.MD5(MessageDiagestUtil.getFromBase64(token));
            if (s.contains("?")) {
                url.append("token = ").append(token);
            }else{
                url.append("?token = ").append(token);
            }
        }
        return url.toString();
    }
}
