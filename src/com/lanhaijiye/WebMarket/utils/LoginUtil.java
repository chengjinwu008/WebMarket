package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.alertDialog.IPhoneDialog;
import com.lanhaijiye.WebMarket.dao.AccountData;
import com.lanhaijiye.WebMarket.entities.LoginEvent;
import com.lanhaijiye.WebMarket.entities.LoginRequestEntity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.ypy.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android on 2015/5/8.
 */
public class LoginUtil {

    public static void showLoginAlertForFragment(BaseFragment activityContext){
        IPhoneDialog iPhoneDialog = new IPhoneDialog(activityContext.getActivity());
        iPhoneDialog.show();
        iPhoneDialog.setOnCancelListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        iPhoneDialog.setOnOKListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doShowLoginForFragment(activityContext);
            }
        });
    }

    public static void doShowLoginForFragment(BaseFragment activityContext) {
        Intent intent =new Intent(activityContext.getActivity(),LoginTableActivity.class);
        activityContext.startActivity(intent);
    }

    public static void doLogin(Context context,String imei,String userName,String password,Gson gson){
        //构建请求实体
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        LoginRequestEntity.Data data = new LoginRequestEntity.Data(imei,userName,password);
        LoginRequestEntity entity = new LoginRequestEntity("0002",data);
        StringRequest request = new StringRequest(Request.Method.POST, CommonDataObject.MAIN_URL + LoginTableActivity.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("login", response);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.getString("code");
                    Toast.makeText(context, object.getString("msg"), Toast.LENGTH_SHORT).show();
                    if("0000".equals(code)){
                        //储存信息
                        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(context,SharedPreferenceUtil.ACCOUNT);
                        sharedPreferenceUtil.putString(new String[]{
                                SharedPreferenceUtil.STATE_SESSION_ID,
                                SharedPreferenceUtil.STATE_USER_ID
                        },new String[]{
                                object.getJSONObject("data").getString("sessionId"),
                                object.getJSONObject("data").getString("userId")
                        });
                        if(!AccountData.isSaved(context, entity.getData().getPhoneNumber())){
                            AccountData.putAccount(context,entity.getData().getPhoneNumber());
                        }

                        EventBus.getDefault().post(new LoginEvent());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("opjson",gson.toJson(entity));
                Log.i("loginjson",gson.toJson(entity));
                return params;
            }
        };
        mRequestQueue.add(request);
        mRequestQueue.start();
    }
}
