package com.lanhaijiye.WebMarket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.activities.SettingActivity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener, BaseFragment.LoadingListener {


    private UILoginFragment loginButton;
    private UIUserInfoFragment userinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.u_center, container, false);

        ImageButton mSetting = (ImageButton) view.findViewById(R.id.u_center_title_setting_button);
        mSetting.setOnClickListener(this);
        loginButton = new UILoginFragment();
        loginButton.setOnLoadFinishListener(this);
        userinfo = new UIUserInfoFragment();

        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(getActivity(), SharedPreferenceUtil.ACCOUNT);
        String user_name = sharedPreferenceUtil.readString(SharedPreferenceUtil.USERNAME_KEY);
        String password = sharedPreferenceUtil.readString(SharedPreferenceUtil.PASSWORD_KEY);

        if (user_name == null || user_name.trim().length() < 0)
            getFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(loginButton).commit();
        else
            getFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(userinfo).commit();

        return view;
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.u_center_title_setting_button:
                intent.setClass(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void loadFinished() {
        getFragmentManager().beginTransaction().hide(loginButton).show(userinfo).commit();
    }

    @Override
    public void loadError(String string) {

    }
}
