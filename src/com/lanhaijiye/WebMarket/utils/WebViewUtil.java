package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.webviewClient.MyWebViewClient;

/**
 * Created by Administrator on 2015/5/6.
 */
public class WebViewUtil {

    private static void initWeb(Context context,PullToRefreshWebView web_content,String url) {
        web_content.getRefreshableView().loadUrl(url);
    }

    public static void initWebSetting(Context context,PullToRefreshWebView web_content,String url,BaseFragment.LoadingListener listener) {
        WebSettings settings = web_content.getRefreshableView().getSettings();
        //处理url
        int i = url.indexOf('?');
        if (i != -1) {
            url += "&appEnv=1&";
        }else{
            url += "?appEnv=1&";
        }
        settings.setJavaScriptEnabled(true);
//        settings.setBuiltInZoomControls(true);
        String sessionId = CommonDataObject.sessionId;
        if(sessionId!=null){
            String cookieString = "PHPSESSID="+sessionId;
            url+="sessionId="+sessionId+"&userId="+ CommonDataObject.userId;
            synCookies(context,url,cookieString);
        }
        //保持登录
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);//打开有限访问缓存 关闭缓存
        web_content.getRefreshableView().requestFocus();
        web_content.getRefreshableView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        web_content.getRefreshableView().setWebViewClient(new MyWebViewClient(listener,context));
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        initWeb(context, web_content, url);
    }

    public static void synCookies(Context context, String url, String cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if(!cookieManager.acceptCookie())
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
//        Log.i("cookie",cookieManager.getCookie(url));
    }


}
