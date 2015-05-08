package com.lanhaijiye.WebMarket.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.CommanDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CommonWebViewActivity;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.activities.SettingActivity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.LoginUtil;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener, BaseFragment.LoadingListener {

    public static final String CALL_CENTER_URL="/wp/XM0000004/wwwroot/mobile/kefu.php";
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
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(loginButton).commit();
        else
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(userinfo).commit();

        //注册菜单按钮
        view.findViewById(R.id.ucenter_indent_list_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_my_collection).setOnClickListener(this);
        view.findViewById(R.id.ucenter_address_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_modify_data_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_calling_btn).setOnClickListener(this);
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
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.u_center_title_setting_button:
                intent.setClass(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ucenter_indent_list_btn:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的

                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_my_collection:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的

                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_address_btn:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的

                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_modify_data_btn:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的

                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_calling_btn:
                //跳转通用webview链接
                intent.setClass(getActivity().getApplicationContext(), CommonWebViewActivity.class);
                intent.putExtra(CommonWebViewActivity.INTENT_TITLE_KEY, getActivity().getResources().getString(R.string.call_center));
                intent.putExtra(CommonWebViewActivity.INTENT_WEB_URL_KEY, CommanDataObject.MAIN_URL + CALL_CENTER_URL);
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
