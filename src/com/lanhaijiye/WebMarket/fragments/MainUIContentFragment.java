package com.lanhaijiye.WebMarket.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;
import com.lanhaijiye.WebMarket.webviewClient.MyWebViewClient;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2015/4/28.
 */
public class MainUIContentFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<WebView> {

    private PullToRefreshWebView web_content;
    private LoadingListener listener;
    private Handler mHandler = new Handler();
    private final String url = "http://192.168.1.79/wp/XM0000004/wwwroot/mobile/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_ui_content, container, false);
        web_content = (PullToRefreshWebView) view.findViewById(R.id.main_web_content);
        web_content.setOnRefreshListener(this);
        WebViewUtil.initWebSetting(web_content,url,listener);
        return view;
    }

    @Override
    public boolean canGoBack() {
        return web_content.getRefreshableView().canGoBack();
    }

    @Override
    public void goBack() {
        web_content.getRefreshableView().goBack();
    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {
        this.listener = listener;
    }

    //下拉刷新触发
    @Override
    public void onRefresh(PullToRefreshBase<WebView> refreshView) {
        mHandler .postDelayed(new Runnable() {
            @Override
            public void run() {
                web_content.getRefreshableView().loadUrl(url);
                web_content.onRefreshComplete();
            }
        },2000);
    }
}
