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
import com.lanhaijiye.WebMarket.fragments.inter.MobileNumRecieverable;

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
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, mobile_sign_up_fragment) .add(android.R.id.content, mobile_verify_fragment).hide(mobile_verify_fragment).commit();
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
        //todo 复现手机号
        ((MobileNumRecieverable)mobile_verify_fragment).changeNum(num);
    }

    @Override
    public void change(BaseFragment nowFragment) {
        if(nowFragment==mobile_sign_up_fragment){
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.change_fragment_left_out,R.anim.change_fragment_right_in).hide(mobile_sign_up_fragment).show(mobile_verify_fragment).commit();
        }else{
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.change_fragment_left_in,R.anim.change_fragment_right_out).hide(mobile_verify_fragment).show(mobile_sign_up_fragment).commit();
        }
    }
}
