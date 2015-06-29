package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.entities.UserInfoRequestEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 2015/5/8.
 */
public class UserAccountUtil {

    public static final String LOGOUT_URL="/mobile/app.php";

    public static boolean getUserState(Context context) {
        SharedPreferenceUtil preferences = new SharedPreferenceUtil(context, SharedPreferenceUtil.ACCOUNT);
        String userName = preferences.readString(SharedPreferenceUtil.STATE_USER_ID);
        return !(userName == null || "".equals(userName));
    }

    public static void doLogout(Context context){
        SharedPreferenceUtil preferences = new SharedPreferenceUtil(context, SharedPreferenceUtil.ACCOUNT);
        preferences.delete(new String[]{SharedPreferenceUtil.USERNAME_KEY,SharedPreferenceUtil.PASSWORD_KEY,SharedPreferenceUtil.STATE_SESSION_ID,SharedPreferenceUtil.STATE_USER_ID});
        //todo 请求登出接口
        UserInfoRequestEntity.Data data =new UserInfoRequestEntity.Data(CommonDataObject.userId, CommonDataObject.sessionId);
        UserInfoRequestEntity entity = new UserInfoRequestEntity("0006",data);
        StringRequest request = new StringRequest(Request.Method.POST, CommonDataObject.MAIN_URL+LOGOUT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject =new JSONObject(response);
                    Toast.makeText(context,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("opjson", CommonDataObject.GSON.toJson(entity));
                return params;
            }
        };

    }

}
