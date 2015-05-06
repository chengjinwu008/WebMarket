package com.lanhaijiye.WebMarket.activities;

import android.os.Bundle;
import android.os.Handler;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.dao.AccountData;

/**
 * Created by Administrator on 2015/5/5.
 */
public class LoginTableActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Handler mHandler = new Handler();
    private EditText user_name_text;
    private CheckBox checkBox;
    private EditText login_password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_table_layout);
        user_name_text = (EditText) findViewById(R.id.login_username_text);
        //注册密码可见
//        checkBox = (CheckBox) findViewById(R.id.password_visible_swap);
//        checkBox.setOnCheckedChangeListener(this);
        //todo 注册账号下拉按钮
        View down_arrow_btn = findViewById(R.id.login_username_delete);
        down_arrow_btn.setOnClickListener(this);

        login_password_text = (EditText) findViewById(R.id.login_password_text);
        View view = findViewById(R.id.login_back);
        view.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        user_name_text.requestFocus();
        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                manager.showSoftInput(user_name_text, 0);
            }
        }, 500);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            login_password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            login_password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_back:
                this.finish();
                break;
            case R.id.login_username_delete:
                String[] accounts= AccountData.getAccounts(this);

                break;
        }
    }
}
