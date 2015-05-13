package com.lanhaijiye.WebMarket.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.CommanDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.listener.SearchListener;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by Administrator on 2015/4/28.
 */
public class MainUIContentFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener<WebView>{

    private PullToRefreshWebView web_content;
    private Handler mHandler = new Handler();
    private static final String URL = "/wp/XM0000004/wwwroot/mobile/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.main_ui_content, container, false);
        web_content = (PullToRefreshWebView) view.findViewById(R.id.main_web_content);
        web_content.setOnRefreshListener(this);
        WebViewUtil.initWebSetting(web_content, CommanDataObject.MAIN_URL + URL, listener);

        initSearchView(view);
        return view;
    }

    private void initSearchView(View view) {
        EditText editText = (EditText) view.findViewById(R.id.search_edit_text);
        SpannableString string = new SpannableString(getResources().getString(R.string.search_hint));

        Drawable drawable = getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0,0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25,getResources().getDisplayMetrics()));
        string.setSpan(new ImageSpan(drawable),0,3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        editText.setHint(string);
        editText.setOnEditorActionListener(new SearchListener());
    }

    @Override
    public boolean canGoBack() {
        return web_content.getRefreshableView().canGoBack();
    }

    @Override
    public void goBack() {
        web_content.getRefreshableView().goBack();
    }

    //下拉刷新触发
    @Override
    public void onRefresh(PullToRefreshBase<WebView> refreshView) {
        mHandler .postDelayed(new Runnable() {
            @Override
            public void run() {
                web_content.getRefreshableView().loadUrl(URL);
                web_content.onRefreshComplete();
            }
        },2000);
    }
}
