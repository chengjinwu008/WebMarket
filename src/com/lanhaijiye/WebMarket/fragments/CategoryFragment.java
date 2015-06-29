package com.lanhaijiye.WebMarket.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.fragments.inter.Reloadable;
import com.lanhaijiye.WebMarket.listener.SearchListener;
import com.lanhaijiye.WebMarket.utils.WebViewUtil;

/**
 * Created by Administrator on 2015/5/6.
 */
public class CategoryFragment extends BaseFragment implements Reloadable,PullToRefreshBase.OnRefreshListener<WebView> {
    private PullToRefreshWebView pw;
    public static final String URL = "/mobile/category.php";
    private Handler mHandler=new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.category_page_layout,container,false);

        pw = (PullToRefreshWebView) view.findViewById(R.id.category_page_web);
        WebViewUtil.initWebSetting(getActivity(),pw, CommonDataObject.MAIN_URL + URL, listener);
        if(!CommonDataObject.refreshWebViews.contains(this)){
            CommonDataObject.refreshWebViews.add(this);
        }
        pw.setOnRefreshListener(this);
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
        editText.setOnEditorActionListener(new SearchListener(getActivity()));
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
