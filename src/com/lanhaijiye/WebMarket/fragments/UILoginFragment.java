package com.lanhaijiye.WebMarket.fragments;

import android.app.Activity;
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
    private LoadingListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                if(resultCode == Activity.RESULT_OK)
                    if(listener!=null)
                        listener.loadFinished();
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
        this.listener = listener;
    }
}
