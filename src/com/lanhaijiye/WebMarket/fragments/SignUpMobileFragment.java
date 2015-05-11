package com.lanhaijiye.WebMarket.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.CountryListActivity;
import com.lanhaijiye.WebMarket.activities.inter.Changeable;
import com.lanhaijiye.WebMarket.activities.inter.Storeable;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.utils.InputMethodUtil;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpMobileFragment extends BaseFragment implements View.OnClickListener, TextWatcher {
    private Handler mHandler=new Handler();
    private Button button;
    private EditText mobile_num;
    private SignUpMobileVerifyFragment mobile_verify_fragment;
    private final int COUNTRY_LIST_REQUEST_CODE = 0x202;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_sign_up_layout,container,false);

        //注册back按键
        view.findViewById(R.id.sign_up_back).setOnClickListener(this);
        //注册查询地区列表按键
        view.findViewById(R.id.sign_up_view_country_list).setOnClickListener(this);
        //注册下一步按钮
        button = (Button) view.findViewById(R.id.sign_up_nexn_step_btn);
        button.setOnClickListener(this);

        //注册输入框
        mobile_num = (EditText)view. findViewById(R.id.sign_up_mobile_num);
        mobile_num.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        InputMethodUtil.showSoftInputMethod(getActivity(),mHandler,mobile_num);
    }

    @Override
    public void onStop() {
        super.onStop();
        InputMethodUtil.hideSoftInputMethod(getActivity());
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_back:
                this.getActivity().finish();
                break;
            case R.id.sign_up_view_country_list:
                //读取国家区号
                Intent intent = new Intent( getActivity(),CountryListActivity.class);
                startActivityForResult(intent,COUNTRY_LIST_REQUEST_CODE);
                break;
            case R.id.sign_up_nexn_step_btn:
                //提取出号码,传递手机号码
                String num = mobile_num.getText().toString();
                ((Storeable)getActivity()).SaveNum(num);
                // 实现碎片切换
                ((Changeable)getActivity()).change(this);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case COUNTRY_LIST_REQUEST_CODE:

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    //按钮可用性切换
    @Override
    public void afterTextChanged(Editable s) {
        String x = mobile_num.getText().toString().trim();

        if (!"".equals(x)) {
            if (!button.isEnabled()) {
                button.setEnabled(true);
            }
        } else {
            if (button.isEnabled()) {
                button.setEnabled(false);
            }
        }
    }
}
