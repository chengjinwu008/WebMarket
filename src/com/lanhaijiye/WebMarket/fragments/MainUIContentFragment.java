package com.lanhaijiye.WebMarket.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/4/28.
 */
public class MainUIContentFragment extends BaseFragment {

    private WebView web_content;
    private LoadingListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_ui_content,container,false);
        web_content = (WebView) view.findViewById(R.id.main_web_content);
        initWebSetting();
        return view;
    }

    private void initWeb() {
        web_content.loadUrl("http://www.zyjj.com/wap");
    }

    private void initWebSetting() {
        WebSettings settings = web_content.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//打开有限访问缓存
        web_content.requestFocus();
        web_content.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        web_content.setWebViewClient(new MyWebViewClient());
        initWeb();
//        web_content.addJavascriptInterface(new JavaScriptInterface(),"demo"); Javascript和webView交互
    }

    @Override
    public boolean canGoBack() {
        return web_content.canGoBack();
    }

    @Override
    public void goBack() {
        web_content.goBack();
    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {
        this.listener = listener;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            super.onReceivedHttpAuthRequest(view, handler, host, realm);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    getErr("连接超时");
                }
            },5000);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(listener!=null){
                listener.loadFinished();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            getErr(description);
        }
    }

    private void getErr(String description) {
        if(listener!=null){
            listener.loadError(description);
        }
    }

}
