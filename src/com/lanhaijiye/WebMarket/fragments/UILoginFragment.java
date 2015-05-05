package com.lanhaijiye.WebMarket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;

/**
 * Created by Administrator on 2015/5/5.
 */
public class UILoginFragment extends BaseFragment {

    public static final int LOGIN_CODE = 0x349;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(getActivity(), BaseFragment.USER_INFO);
        String state = preferenceUtil.readString(BaseFragment.USER_STATE);

        if(state!=null&&state.trim().length()>0){
            return inflater.inflate(R.layout.user_info,container,false);
        }
        View view = inflater.inflate(R.layout.login_button,container,false);
        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        return view;
    }

    private void doLogin() {
        Intent intent =new Intent(getActivity(),LoginTableActivity.class);
        startActivityForResult(intent, LOGIN_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case LOGIN_CODE:
                //todo 处理登录活动返回的参数
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {

    }
}
