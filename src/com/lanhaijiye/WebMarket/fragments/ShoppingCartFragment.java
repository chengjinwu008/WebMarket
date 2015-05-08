package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.CommanDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by Administrator on 2015/5/6.
 */
public class ShoppingCartFragment extends BaseFragment {

    private PullToRefreshWebView pw;
    private static final String URL = "/wp/XM0000004/wwwroot/mobile/cart.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.shopping_cart_layout,container,false);

        pw = (PullToRefreshWebView) view.findViewById(R.id.shopping_cart_webView);
        WebViewUtil.initWebSetting(pw, CommanDataObject.MAIN_URL+URL,listener);
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
}
