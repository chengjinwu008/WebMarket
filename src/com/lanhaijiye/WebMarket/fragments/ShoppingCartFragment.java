package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.fragments.inter.Reloadable;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by Administrator on 2015/5/6.
 */
public class ShoppingCartFragment extends BaseFragment implements Reloadable,PullToRefreshBase.OnRefreshListener<WebView>{

    private PullToRefreshWebView pw;
    public static final String URL = "/mobile/cart.php";
    private Handler mHandler=new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.shopping_cart_layout,container,false);

        pw = (PullToRefreshWebView) view.findViewById(R.id.shopping_cart_webView);
        WebViewUtil.initWebSetting(getActivity(),pw, CommonDataObject.MAIN_URL+URL,listener);
        pw.setOnRefreshListener(this);
        if(!CommonDataObject.refreshWebViews.contains(this)){
            CommonDataObject.refreshWebViews.add(this);
        }
        return view;
    }

    @Override
    public boolean canGoBack() {
        return pw.getRefreshableView().canGoBack();
    }

    @Override
    public void goBack() {
        pw.getRefreshableView().goBack();
    }

    @Override
    public void reload() {
        WebViewUtil.initWebSetting(getActivity(),pw, CommonDataObject.MAIN_URL + URL, listener);
    }

    @Override
    public void onRefresh(PullToRefreshBase<WebView> refreshView) {
        mHandler .post(new Runnable() {
            @Override
            public void run() {
                pw.getRefreshableView().reload();
                pw.onRefreshComplete();
            }
        });
    }
}
