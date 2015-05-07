package com.lanhaijiye.WebMarket.webviewClient;

import android.graphics.Bitmap;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/5/6.
 */
public class MyWebViewClient extends WebViewClient {

    private BaseFragment.LoadingListener listener;

    public MyWebViewClient(BaseFragment.LoadingListener listener){
        this.listener = listener;
    }

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
                getErr("connection time_out");
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

    private void getErr(String description) {
        if(listener!=null){
            listener.loadError(description);
        }
    }
}
