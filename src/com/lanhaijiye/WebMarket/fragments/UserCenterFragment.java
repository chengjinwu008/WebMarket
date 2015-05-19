package com.lanhaijiye.WebMarket.fragments;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.CommanDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CommonWebViewActivity;
import com.lanhaijiye.WebMarket.activities.LoginTableActivity;
import com.lanhaijiye.WebMarket.activities.SettingActivity;
import com.lanhaijiye.WebMarket.alertDialog.IPhoneMenu;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.LoginUtil;
import com.lanhaijiye.WebMarket.utils.ShareUtil;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener, BaseFragment.LoadingListener {

    public static final String CALL_CENTER_URL="/wp/XM0000004/wwwroot/mobile/kefu.php";
    public static final String LOGIN_STATE_CHANGE="www.lanhaijiye.com.WebMarket.ACTION_LOGIN_STATE_CHANGE";
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

        changeStateLabel();

        //注册接收登录状态改变的receiver
        IntentFilter filter = new IntentFilter(LOGIN_STATE_CHANGE);
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //todo 监听登录状态改变来改变头像
            }
        };

        //注册菜单按钮
        view.findViewById(R.id.ucenter_indent_list_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_my_collection).setOnClickListener(this);
        view.findViewById(R.id.ucenter_address_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_modify_data_btn).setOnClickListener(this);
        view.findViewById(R.id.ucenter_calling_btn).setOnClickListener(this);
        return view;
    }

    private void changeStateLabel() {
        if (!UserAccountUtil.getUserState(getActivity()))
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(loginButton).hide(userinfo).commit();
        else
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).show(userinfo).hide(loginButton).commit();
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
//
                    List<Pair<Integer,String>> menuResource = new ArrayList<>();
                    menuResource.add(new Pair<>(R.drawable.dingdan,"FirstMenu"));
                    menuResource.add(new Pair<>(null,"SecondMenu"));
                    menuResource.add(new Pair<>(null,"ThirdMenu"));
                    IPhoneMenu menu = new IPhoneMenu(getActivity(), menuResource, new IPhoneMenu.IPhoneMenuListener() {
                        @Override
                        public void onclick(AdapterView<?> parent, View view, int position, long id, AlertDialog dialog) {
                            switch (position){
                                case 0:
                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
                                    break;
                                case 1:
                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
                                    break;
                                case 2:
                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
                                    break;
                            }
                            dialog.dismiss();
                        }
                    });
                    menu.show();
                }
                break;
            case R.id.ucenter_my_collection:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的

                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                    ShareUtil.showShare(getActivity(),"这个是分享哦，亲",null);
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
