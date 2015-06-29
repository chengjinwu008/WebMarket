package com.lanhaijiye.WebMarket.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CommonWebViewActivity;
import com.lanhaijiye.WebMarket.activities.SettingActivity;
import com.lanhaijiye.WebMarket.entities.LoginEvent;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.fragments.inter.Reloadable;
import com.lanhaijiye.WebMarket.utils.LoginUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;
import com.ypy.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener, BaseFragment.LoadingListener {

    public static final String CALL_CENTER_URL="/mobile/kefu.php";
    public static final String LOGIN_STATE_CHANGE="www.lanhaijiye.com.WebMarket.ACTION_LOGIN_STATE_CHANGE";
    private UILoginFragment loginButton;
    private UIUserInfoFragment userinfo;
    public static final String INDENT_URL="/mobile/user.php?act=order_list";
    public static final String MOD_URL="/mobile/user.php?act=profile";
    public static final String DILIVER_ADDR="/mobile/user.php?act=address_list";
    public static final String MY_FAVOR="/mobile/favorites.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册登录事件接收器
        EventBus eventBus = EventBus.getDefault();
        eventBus.register(this);
    }

    public void onEventMainThread(LoginEvent event){
        changeStateLabel();
        List<Reloadable> refreshWebViews= CommonDataObject.refreshWebViews;
        for(Reloadable refreshWebView:refreshWebViews){
            refreshWebView.reload();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.u_center, container, false);

        CommonDataObject.userUI = this;

        ImageButton mSetting = (ImageButton) view.findViewById(R.id.u_center_title_setting_button);
        mSetting.setOnClickListener(this);
        loginButton = new UILoginFragment();
        loginButton.setOnLoadFinishListener(this);
        userinfo = new UIUserInfoFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.user_info_fragment, userinfo).add(R.id.user_info_fragment, loginButton).commit();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void changeStateLabel() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!UserAccountUtil.getUserState(getActivity()))
            transaction.show(loginButton).hide(userinfo).commitAllowingStateLoss();
        else
            transaction.show(userinfo).hide(loginButton).commitAllowingStateLoss();
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
                    NewWebview(intent,getActivity().getResources().getString(R.string.my_indent), CommonDataObject.MAIN_URL + INDENT_URL);
                }else{
                    //没有登录
//
//                    List<Pair<Integer,String>> menuResource = new ArrayList<>();
//                    menuResource.add(new Pair<>(R.drawable.dingdan,"FirstMenu"));
//                    menuResource.add(new Pair<>(null,"SecondMenu"));
//                    menuResource.add(new Pair<>(null,"ThirdMenu"));
//                    IPhoneMenu menu = new IPhoneMenu(getActivity(), menuResource, new IPhoneMenu.IPhoneMenuListener() {
//                        @Override
//                        public void onclick(AdapterView<?> parent, View view, int position, long id, AlertDialog dialog) {
//                            switch (position){
//                                case 0:
//                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
//                                    break;
//                                case 1:
//                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
//                                    break;
//                                case 2:
//                                    LoginUtil.showLoginAlertForFragment(UserCenterFragment.this);
//                                    break;
//                            }
//                            dialog.dismiss();
//                        }
//                    });
//                    menu.show();
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_my_collection:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的
                    NewWebview(intent,getActivity().getResources().getString(R.string.my_favorite), CommonDataObject.MAIN_URL + MY_FAVOR);
                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
//                    ShareUtil.showShare(getActivity(),"这个是分享哦，亲",null);
                }
                break;
            case R.id.ucenter_address_btn:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的
                    NewWebview(intent,getActivity().getResources().getString(R.string.delivery_address), CommonDataObject.MAIN_URL + DILIVER_ADDR);
                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_modify_data_btn:
                //判断用户登录没有
                if(UserAccountUtil.getUserState(getActivity())){
                    //登录了的
                    NewWebview(intent,getActivity().getResources().getString(R.string.modify_my_data), CommonDataObject.MAIN_URL + MOD_URL);
                }else{
                    //没有登录
                    LoginUtil.showLoginAlertForFragment(this);
                }
                break;
            case R.id.ucenter_calling_btn:
                //跳转通用webview链接
                NewWebview(intent,getActivity().getResources().getString(R.string.call_center), CommonDataObject.MAIN_URL + CALL_CENTER_URL);
                break;
        }
    }

    public void NewWebview(Intent intent,String title,String url) {
        intent.setClass(getActivity().getApplicationContext(), CommonWebViewActivity.class);
        intent.putExtra(CommonWebViewActivity.INTENT_TITLE_KEY,title);
        intent.putExtra(CommonWebViewActivity.INTENT_WEB_URL_KEY,url);
        startActivity(intent);
    }

    @Override
    public void loadFinished() {
        getFragmentManager().beginTransaction().hide(loginButton).show(userinfo).commit();
    }

    @Override
    public void loadError(String string) {

    }
}
