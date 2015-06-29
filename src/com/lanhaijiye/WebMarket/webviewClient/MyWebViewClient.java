package com.lanhaijiye.WebMarket.webviewClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CommonWebViewActivity;
import com.lanhaijiye.WebMarket.entities.LoginEvent;
import com.lanhaijiye.WebMarket.fragments.CategoryFragment;
import com.lanhaijiye.WebMarket.fragments.MainUIContentFragment;
import com.lanhaijiye.WebMarket.fragments.ShoppingCartFragment;
import com.lanhaijiye.WebMarket.fragments.UserCenterFragment;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.LoginUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;
import com.ypy.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/5/6.
 */
public class MyWebViewClient extends WebViewClient {

    private BaseFragment.LoadingListener listener;
    private Context context;

    public MyWebViewClient(BaseFragment.LoadingListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("url", url);
        //todo À¹½ØurlÌø×ª
        if ((CommonDataObject.MAIN_URL + CategoryFragment.URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(1);
            CommonDataObject.refreshWebViews.get(1).reload();
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + ShoppingCartFragment.URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(2);
            CommonDataObject.refreshWebViews.get(2).reload();
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + MainUIContentFragment.URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(0);
            CommonDataObject.refreshWebViews.get(0).reload();
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + "/mobile/user.php?act=user_center").equals(url) || (CommonDataObject.MAIN_URL + "/mobile/user.php?act=login").equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserCenterFragment.CALL_CENTER_URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            CommonDataObject.userUI.NewWebview(new Intent(), CommonDataObject.userUI.getActivity().getResources().getString(R.string.call_center), CommonDataObject.MAIN_URL + UserCenterFragment.CALL_CENTER_URL);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserCenterFragment.MY_FAVOR).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            if (UserAccountUtil.getUserState(CommonDataObject.mainUI.getActivity()))//ÅÐ¶ÏµÇÂ¼
                CommonDataObject.userUI.NewWebview(new Intent(), CommonDataObject.userUI.getActivity().getResources().getString(R.string.my_favorite), CommonDataObject.MAIN_URL + UserCenterFragment.MY_FAVOR);
            else
                LoginUtil.showLoginAlertForFragment(CommonDataObject.userUI);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserCenterFragment.DILIVER_ADDR).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            if (UserAccountUtil.getUserState(CommonDataObject.mainUI.getActivity()))//ÅÐ¶ÏµÇÂ¼
                CommonDataObject.userUI.NewWebview(new Intent(), CommonDataObject.userUI.getActivity().getResources().getString(R.string.delivery_address), CommonDataObject.MAIN_URL + UserCenterFragment.DILIVER_ADDR);
            else
                LoginUtil.showLoginAlertForFragment(CommonDataObject.userUI);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserCenterFragment.MOD_URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            if (UserAccountUtil.getUserState(CommonDataObject.mainUI.getActivity()))//ÅÐ¶ÏµÇÂ¼
                CommonDataObject.userUI.NewWebview(new Intent(), CommonDataObject.userUI.getActivity().getResources().getString(R.string.modify_my_data), CommonDataObject.MAIN_URL + UserCenterFragment.MOD_URL);
            else
                LoginUtil.showLoginAlertForFragment(CommonDataObject.userUI);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserCenterFragment.INDENT_URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            if (UserAccountUtil.getUserState(CommonDataObject.mainUI.getActivity()))//ÅÐ¶ÏµÇÂ¼
                CommonDataObject.userUI.NewWebview(new Intent(), CommonDataObject.userUI.getActivity().getResources().getString(R.string.my_indent), CommonDataObject.MAIN_URL + UserCenterFragment.INDENT_URL);
            else
                LoginUtil.showLoginAlertForFragment(CommonDataObject.userUI);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + UserAccountUtil.LOGOUT_URL).equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            if (UserAccountUtil.getUserState(CommonDataObject.mainUI.getActivity()))//ÅÐ¶ÏµÇÂ¼
            {
                UserAccountUtil.doLogout(CommonDataObject.mainUI.getActivity());
                EventBus.getDefault().post(new LoginEvent());
            } else
                LoginUtil.showLoginAlertForFragment(CommonDataObject.userUI);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else if ((CommonDataObject.MAIN_URL + "/mobile/user.php?act=login&gourl=%2Fmobile%2Fbuy.php%3Fact%3Dcheckout%26appEnv%3D1").equals(url) || (CommonDataObject.MAIN_URL + "/mobile/user.php?act=login").equals(url)) {
            CommonDataObject.mainUI.onPageSelected(3);
            Intent intent = new Intent(CommonWebViewActivity.ACTION_SHUTDOWN_WINDOW);
            context.sendBroadcast(intent);
            return true;
        } else {
            if (!url.contains("appEnv")) {
                int i = url.indexOf('?');
                String sessionId = CommonDataObject.sessionId;
                if (i != -1) {
                    url += "&appEnv=1";
                    if (sessionId != null)
                        url += "&sessionId=" + CommonDataObject.sessionId + "&userId=" + CommonDataObject.userId;
                } else {
                    url += "?appEnv=1";
                    if (sessionId != null) {
                        url += "&sessionId=" + CommonDataObject.sessionId + "&userId=" + CommonDataObject.userId;
                    }
                }
                if (sessionId != null) {
                    String cookieString = "PHPSESSID=" + sessionId;
                    WebViewUtil.synCookies(CommonDataObject.mainUI.getActivity().getApplicationContext(), url, cookieString);
                }
            }
            Intent intent = new Intent();
            intent.setClass(context.getApplicationContext(), CommonWebViewActivity.class);
            intent.putExtra(CommonWebViewActivity.INTENT_TITLE_KEY, context.getString(R.string.detail));
            intent.putExtra(CommonWebViewActivity.INTENT_WEB_URL_KEY, url);
            context.startActivity(intent);

            return true;
        }
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        Log.e("url",url);
        super.onPageStarted(view, url, favicon);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getErr("connection time_out");
            }
        }, 5000);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (listener != null) {
            listener.loadFinished();
        }
        CookieManager cookieManager = CookieManager.getInstance();
        String CookieStr = cookieManager.getCookie(url);
        Log.e("cookie", CookieStr + "**********\n" + url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        getErr(description);
    }

    private void getErr(String description) {
        if (listener != null) {
            listener.loadError(description);
        }
    }
}
