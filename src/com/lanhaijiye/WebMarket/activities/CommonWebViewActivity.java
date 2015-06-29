package com.lanhaijiye.WebMarket.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by android on 2015/5/8.
 */
public class CommonWebViewActivity extends BaseActivity implements PullToRefreshWebView.OnRefreshListener, View.OnClickListener {

    public static final String INTENT_TITLE_KEY ="title";
    public static final String INTENT_WEB_URL_KEY ="url";
    private PullToRefreshWebView webView;
    private String url = null;
    public static final String ACTION_SHUTDOWN_WINDOW="com.lanhaijiye.shutdownCommonWebView";
    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview_layout);
        Intent intent =getIntent();
        Bundle data = intent.getExtras();

        TextView text = (TextView) findViewById(R.id.title_text);
        if (data != null) {
            text.setText(data.getString(INTENT_TITLE_KEY));

            url = data.getString(INTENT_WEB_URL_KEY);
        }

        webView = (PullToRefreshWebView) findViewById(R.id.web_content);
        WebViewUtil.initWebSetting(this,webView,url,null);
        webView.setOnRefreshListener(this);

        findViewById(R.id.back).setOnClickListener(this);

        IntentFilter filter =new IntentFilter(ACTION_SHUTDOWN_WINDOW);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ACTION_SHUTDOWN_WINDOW.equals(intent.getAction())){
                    finish();
                }
            }
        };
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        webView.getRefreshableView().reload();
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
            if(webView.getRefreshableView().canGoBack()){
                webView.getRefreshableView().goBack();
                return true;
            }
        return super.onKeyDown(keyCode,event);
    }
}