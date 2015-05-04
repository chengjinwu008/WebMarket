package com.lanhaijiye.WebMarket.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.SettingActivity;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UserCenterFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.u_center,container,false);
        
        ImageButton mSetting = (ImageButton) view.findViewById(R.id.u_center_title_setting_button);
        mSetting.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {
    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.u_center_title_setting_button:
                intent.setClass(getActivity().getApplicationContext(), SettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
