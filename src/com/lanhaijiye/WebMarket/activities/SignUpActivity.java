package com.lanhaijiye.WebMarket.activities;

import android.os.Bundle;
import android.view.View;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_sign_up_layout);

        //注册back按键
        findViewById(R.id.sign_up_back).setOnClickListener(this);
        //注册查询地区列表按键
        findViewById(R.id.sign_up_view_country_list).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_back:
                this.finish();
                break;
            case R.id.sign_up_view_country_list:
                
                break;
        }
    }
}
