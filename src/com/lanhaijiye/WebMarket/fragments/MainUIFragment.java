package com.lanhaijiye.WebMarket.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/28.
 */
public class MainUIFragment extends BaseFragment implements BaseFragment.LoadingListener, RadioGroup.OnCheckedChangeListener {
    private LoadingListener listener;
    private ArrayList<BaseFragment> fragments;
    private Map<BaseFragment, Boolean> fragmentStatus;
    private BaseFragment mFragment;
    private RadioGroup group;
    private Handler mHandler = new Handler();
    private TextView index_page_hint;
    private TextView category_page_hint;
    private TextView shopping_page_hint;
    private TextView user_center_page_hint;
    private int NO =0;

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
        fragmentStatus = new HashMap<>();

        fragments.add(fragment);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        fragmentStatus.put(fragment, false);
        fragmentStatus.put(fragment1, false);
        fragmentStatus.put(fragment2, false);
        fragmentStatus.put(fragment3, false);

        return view;
    }

    private void changeFragment(int i) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //动画
        if (NO < i)
            transaction.setCustomAnimations(R.animator.change_fragment_right_in, R.animator.change_fragment_left_out);
        else if (NO > i)
            transaction.setCustomAnimations(R.animator.change_fragment_left_in, R.animator.change_fragment_right_out);
        NO = i;
        BaseFragment fragment = fragments.get(i);
        if (!fragmentStatus.get(fragment)) {
            transaction.add(R.id.main_content_change, fragment);
            fragmentStatus.put(fragment, true);
        }
        if (mFragment != null) {
            transaction.hide(mFragment);
        }
        transaction.show(fragment).commit();
        mFragment = fragment;
        //如果键盘弹出，把键盘弹回去
        View view = getActivity().getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {
        this.listener = listener;
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
        int i1 = 0, i2 = 3, i3 = 7, i4 = 0;
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
}
