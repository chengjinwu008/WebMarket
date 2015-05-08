package com.lanhaijiye.WebMarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.adapter.PagerAdapter;
import com.lanhaijiye.WebMarket.adapter.inter.PagerAdapterGettable;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/4/28.
 */
public class MainUIFragment extends BaseFragment implements BaseFragment.LoadingListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener,PagerAdapterGettable {
    private ArrayList<BaseFragment> fragments;
    private BaseFragment mFragment;
    private RadioGroup group;
    private Handler mHandler = new Handler();
    private TextView index_page_hint;
    private TextView category_page_hint;
    private TextView shopping_page_hint;
    private TextView user_center_page_hint;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getView(inflater, container);
        group = (RadioGroup) view.findViewById(R.id.bottom_bar_radio_group);
        group.setOnCheckedChangeListener(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((RadioButton) group.getChildAt(0)).setChecked(true);
            }
        }, 500);

        //消息提示view
        index_page_hint = (TextView) view.findViewById(R.id.index_page_msg_hint);
        category_page_hint = (TextView) view.findViewById(R.id.category_page_msg_hint);
        shopping_page_hint = (TextView) view.findViewById(R.id.shopping_cart_page_msg_hint);
        user_center_page_hint = (TextView) view.findViewById(R.id.user_center_page_msg_hint);
        //检查消息数目
        checkMsgCount();
        return view;
    }

    private View getView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.main, container, false);
        BaseFragment fragment = new MainUIContentFragment();
        fragment.setOnLoadFinishListener(this);
        BaseFragment fragment1 = new CategoryFragment();
        fragment1.setOnLoadFinishListener(this);
        BaseFragment fragment2 = new ShoppingCartFragment();
        fragment2.setOnLoadFinishListener(this);
        BaseFragment fragment3 = new UserCenterFragment();
        fragment3.setOnLoadFinishListener(this);

        fragments = new ArrayList<>();

        fragments.add(fragment);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        //初始化pager
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        pager = (ViewPager) view.findViewById(R.id.main_content_change);
        //提升pager的缓存数量
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(this);

        return view;
    }

    private void changeFragment(int i) {
//        FragmentManager manager = getActivity().getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        //动画
//        BaseFragment fragment = fragments.get(i);
//        if (!fragment.isAdded()) {
//            transaction.add(R.id.main_content_change, fragment);
//        }
////        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        if (mFragment != null)
//            transaction.hide(mFragment);
//        transaction.show(fragment).commit();
//        mFragment = fragment;
        //如果键盘弹出，把键盘弹回去
        if(pager.getCurrentItem()!=i){
            pager.setCurrentItem(i,true);
            mFragment = fragments.get(i);
            View view = getActivity().getWindow().peekDecorView();
            if (view != null) {
                InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public boolean canGoBack() {
        if (mFragment == fragments.get(0))
            return mFragment.canGoBack();
        else
            return mFragment != null;
    }

    @Override
    public void goBack() {
        if (mFragment != null)
            if (mFragment.canGoBack())
                mFragment.goBack();
            else if (mFragment != fragments.get(0)) {
                ((RadioButton) group.getChildAt(0)).setChecked(true);
            } else
                getActivity().sendBroadcast(new Intent(BaseActivity.SHUT_DOWN_ORDER));
    }

    @Override
    public void loadFinished() {
        if (listener != null) {
            listener.loadFinished();
        }
    }

    @Override
    public void loadError(String description) {
        if (listener != null) {
            listener.loadError(description);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        //按钮点击事件
        switch (i) {
            case R.id.index_page:
                changeFragment(0);
                if (index_page_hint.getVisibility() != View.GONE) {
                    index_page_hint.setVisibility(View.GONE);
                }
                break;
            case R.id.category_page:
                changeFragment(1);
                if (category_page_hint.getVisibility() != View.GONE) {
                    category_page_hint.setVisibility(View.GONE);
                }
                break;
            case R.id.shopping_cart_page:
                changeFragment(2);
                if (shopping_page_hint.getVisibility() != View.GONE) {
                    shopping_page_hint.setVisibility(View.GONE);
                }
                break;
            case R.id.user_center_page:
                changeFragment(3);
                if (user_center_page_hint.getVisibility() != View.GONE) {
                    user_center_page_hint.setVisibility(View.GONE);
                }
                break;
        }
    }

    public void checkMsgCount() {
        //todo 检查每个导航栏的消息数目
        //docheck
        int i1 = 0, i2 = 0, i3 = 0, i4 = 0;
        if (i1 != 0) {
            index_page_hint.setText(String.valueOf(i1));
            index_page_hint.setVisibility(View.VISIBLE);
        }
        if (i2 != 0) {
            category_page_hint.setText(String.valueOf(i2));
            category_page_hint.setVisibility(View.VISIBLE);
        }
        if (i3 != 0) {
            shopping_page_hint.setText(String.valueOf(i3));
            shopping_page_hint.setVisibility(View.VISIBLE);
        }
        if (i4 != 0) {
            user_center_page_hint.setText(String.valueOf(i4));
            user_center_page_hint.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        ((RadioButton) group.getChildAt(i)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }
}
