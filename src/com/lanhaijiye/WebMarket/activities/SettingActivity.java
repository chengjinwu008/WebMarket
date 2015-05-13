package com.lanhaijiye.WebMarket.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.adapter.IconAdapter;
import com.lanhaijiye.WebMarket.utils.CacheUtil;
import com.lanhaijiye.WebMarket.utils.PackageUtil;
import com.lanhaijiye.WebMarket.utils.UserAccountUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;
    private IconAdapter adapter;
    private TextView text;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_setting_layout);
        initCommonButton();

        initDialogData();
        findViewById(R.id.clean_cache).setOnClickListener(this);
        findViewById(R.id.your_advise).setOnClickListener(this);

        //回显缓存大小
        text = (TextView) findViewById(R.id.cache_size);
        try {
            text.setText(CacheUtil.getTotalCacheSize(this));
        } catch (Exception e) {
            Log.e("Cache","can't get Cache size");
            e.printStackTrace();
        }

        //回显版本号
        TextView text2 = (TextView) findViewById(R.id.version_code_text);
        try {
            text2.setText("ver."+PackageUtil.getAppVersion(this));
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("ver",e.getMessage());
            e.printStackTrace();
        }
//        initLocationData();

    }

    private void initLocationData() {
        View view = findViewById(R.id.setting_location_request);
        view.setOnClickListener(this);
    }

    private void initDialogData() {
        List<Pair<Integer, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(R.drawable.load_failed, getString(R.string.logout)));
        pairs.add(new Pair<>(R.drawable.load_succeed, getString(R.string.shut_down_client)));
        adapter = new IconAdapter(pairs, R.layout.icon_item, this);
    }

    private void initCommonButton() {
        Button button = (Button) findViewById(R.id.setting_exit_btn);
        ImageButton back = (ImageButton) findViewById(R.id.setting_back);
        button.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_exit_btn:
                if (dialog == null)
                    dialog = new AlertDialog.Builder(this)
                            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    switch (i) {
                                        case 0://todo 退出登录
                                            UserAccountUtil.doLogout(SettingActivity.this);
                                            break;
                                        case 1://退出客户端
                                            sendBroadcast(new Intent(SHUT_DOWN_ORDER));
                                            break;
                                    }
                                }
                            })
                            .create();
                dialog.show();
                break;
            case R.id.setting_back:
                this.finish();
                break;
            case R.id.clean_cache:
                CacheUtil.clearAllCache(this);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            text.setText(CacheUtil.getTotalCacheSize(SettingActivity.this));
                        } catch (Exception e) {
                            Log.e("Cache","can't get Cache size");
                            e.printStackTrace();
                        }
                    }
                },500);
                break;
            case R.id.your_advise:
                Intent intent = new Intent(this,CommonWebViewActivity.class);
                intent.putExtra(CommonWebViewActivity.INTENT_TITLE_KEY,getString(R.string.advise));
                intent.putExtra(CommonWebViewActivity.INTENT_WEB_URL_KEY,"");//todo 意见反馈url
                startActivity(intent);
                break;
        }
    }
}
