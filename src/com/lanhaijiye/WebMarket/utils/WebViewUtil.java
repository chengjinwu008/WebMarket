package com.lanhaijiye.WebMarket.utils;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.webviewClient.MyWebViewClient;

/**
 * Created by Administrator on 2015/5/6.
 */
public class WebViewUtil {

    private static void initWeb(PullToRefreshWebView web_content,String url) {
        web_content.getRefreshableView().loadUrl(url);
    }

    public static void initWebSetting(PullToRefreshWebView web_content,String url,BaseFragment.LoadingListener listener) {
        WebSettings settings = web_content.getRefreshableView().getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//打开有限访问缓存
        web_content.getRefreshableView().requestFocus();
        web_content.getRefreshableView().setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        web_content.getRefreshableView().setWebViewClient(new MyWebViewClient(listener));
        initWeb(web_content,url);
//        web_content.addJavascriptInterface(new JavaScriptInterface(),"demo"); Javascript和webView交互
    }

}
