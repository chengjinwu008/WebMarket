package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by Administrator on 2015/5/6.
 */
public class CategoryFragment extends BaseFragment {

    private LoadingListener listener;
    private PullToRefreshWebView pw;
    private final String url = "http://192.168.1.79/wp/XM0000004/wwwroot/mobile/category.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.category_page_layout,container,false);

        pw = (PullToRefreshWebView) view.findViewById(R.id.category_page_web);
        WebViewUtil.initWebSetting(pw,url,listener);
        return view;
    }

    @Override
    public boolean canGoBack() {
        pw.getRefreshableView().canGoBack();
        return false;
    }

    @Override
    public void goBack() {
        pw.getRefreshableView().goBack();
    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {
        this.listener = listener;
    }
}
