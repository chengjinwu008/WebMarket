package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.MyApplication;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.entities.LoginEvent;
import com.lanhaijiye.WebMarket.entities.UserInfoEventEntity;
import com.lanhaijiye.WebMarket.entities.UserInfoRequestEntity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;
import com.ypy.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/7.
 */
public class UIUserInfoFragment extends BaseFragment {

    public static final String USER_INFO_URL = "/service/app.php";
    private View view;
    private SharedPreferenceUtil util;
    private TextView userName;
    private Button pay_points;
    private Button user_money;

    public void onEventMainThread(UserInfoEventEntity event){
        userName.setText(event.nickname);
        pay_points.setText(getActivity().getString(R.string.pay_points)+event.pay_points);
        user_money.setText(getActivity().getString(R.string.user_money)+event.user_money);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_info, container, false);
        util = new SharedPreferenceUtil(getActivity(), SharedPreferenceUtil.ACCOUNT);
        userName = (TextView)view.findViewById(R.id.ui_user_name_text);
        pay_points =(Button)view.findViewById(R.id.pay_points);
        user_money =(Button) view.findViewById(R.id.user_money);

        EventBus.getDefault().register(this);

//        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //todo 点击头像处理
//            }
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        MyApplication application = (MyApplication) getActivity().getApplication();

        String userId = CommonDataObject.userId;
        String sessionId = CommonDataObject.sessionId;

        if(userId!=null && sessionId!=null){
            UserInfoRequestEntity.Data data = new UserInfoRequestEntity.Data(userId, sessionId);
            UserInfoRequestEntity entity = new UserInfoRequestEntity("0004", data);

            StringRequest request = new StringRequest(Request.Method.POST, CommonDataObject.MAIN_URL + USER_INFO_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("userInfo", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String code = jsonObject.getString("code");
                        if("0006".equals(code)){
                            UserAccountUtil.doLogout(getActivity());
                            EventBus.getDefault().post(new LoginEvent());
                        }
                        else if("0000".equals(code))
                        {
                            JSONObject data = jsonObject.getJSONArray("data").getJSONObject(4);
                            UserInfoEventEntity entity1=new UserInfoEventEntity("".equals(data.getString("nickname"))?data.getString("user_name"):data.getString("nickname"),data.getString("user_money"),data.getString("pay_points"));
                            EventBus.getDefault().post(entity1);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("opjson", application.gson.toJson(entity));
                    Log.i("userInfo", params.get("opjson"));
                    return params;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
            queue.start();
        }else{
            if(userId!=null||sessionId!=null){
                UserAccountUtil.doLogout(getActivity());
                EventBus.getDefault().post(new LoginEvent());
            }
        }
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }
}
