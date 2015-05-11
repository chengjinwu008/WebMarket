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
        //��ȡ���ӷ��������ͻ���������
        String token = preferenceUtil.readString(SharedPreferenceUtil.STATE_TOKEN_KEY);
        //��Ϊtoken��base64���ܵģ�������Ҫ���ܣ�Ȼ������md5���ܷ���


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
