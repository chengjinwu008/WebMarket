package com.lanhaijiye.WebMarket.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.inter.Changeable;
import com.lanhaijiye.WebMarket.activities.inter.Storeable;
import com.lanhaijiye.WebMarket.fragments.SignUpMobileFragment;
import com.lanhaijiye.WebMarket.fragments.SignUpMobileVerifyFragment;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener, TextWatcher,Storeable,Changeable{
    private EditText mobile_num;
    private Button button;
    private String num;
    private BaseFragment mobile_verify_fragment;
    private BaseFragment mobile_sign_up_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mobile_verify_fragment = new SignUpMobileVerifyFragment();
        mobile_sign_up_fragment = new SignUpMobileFragment();
        getFragmentManager().beginTransaction().add(android.R.id.content, mobile_verify_fragment) .add(android.R.id.content, mobile_verify_fragment).hide(mobile_verify_fragment).commit();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void SaveNum(String num) {
        this.num = num;
    }

    @Override
    public void change(BaseFragment nowFragment) {
        if(nowFragment==mobile_sign_up_fragment){
            getFragmentManager().beginTransaction().hide(mobile_sign_up_fragment).show(mobile_verify_fragment).commit();
            //todo 实现电话号码的替换
        }else{
            getFragmentManager().beginTransaction().hide(mobile_sign_up_fragment).show(mobile_verify_fragment).commit();
        }
    }
}
