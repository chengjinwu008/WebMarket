package com.lanhaijiye.WebMarket.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by Administrator on 2015/4/29.
 */
public class ApplicationInfoActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info);

        ImageButton ib = (ImageButton) findViewById(R.id.app_info_back);
        ib.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.app_info_back:
                this.finish();
                break;
        }
    }
}
