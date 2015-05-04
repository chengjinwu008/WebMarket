package com.lanhaijiye.WebMarket.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getView(inflater, container);

        group = (RadioGroup) view.findViewById(R.id.bottom_bar_fragment);
        group.setOnCheckedChangeListener(this);
        ((RadioButton) group.getChildAt(0)).setChecked(true);
        return view;
    }

    private View getView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.main, container, false);
        BaseFragment fragment = new MainUIContentFragment();
        fragment.setOnLoadFinishListener(this);
        BaseFragment fragment1 = new MainUIContentFragment();
        fragment1.setOnLoadFinishListener(this);
        BaseFragment fragment2 = new MainUIContentFragment();
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
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
        else if(mFragment!=fragments.get(0)){
                ((RadioButton) group.getChildAt(0)).setChecked(true);
            }
        else
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
        while(i>4){
            i-=4;
        }
        changeFragment(i - 1);
    }
}
