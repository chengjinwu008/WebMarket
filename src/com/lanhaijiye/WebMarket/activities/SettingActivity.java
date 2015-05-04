package com.lanhaijiye.WebMarket.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.adapter.IconAdapter;
import com.lanhaijiye.WebMarket.utils.LocationUtil;
import com.lanhaijiye.WebMarket.utils.PackageUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;
    private IconAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_setting_layout);
        initCommonButton();

        initDialogData();

        initLocationData();

        initAppInfo();
    }

    private void initAppInfo() {
        View view = findViewById(R.id.setting_about_app);
        TextView text = (TextView) view.findViewById(R.id.setting_app_version_text);
        try {
            text.setText("ver."+PackageUtil.getAppVersion(this));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        view.setOnClickListener(this);
    }

    private void initLocationData() {
        View view = findViewById(R.id.setting_location_request);
        view.setOnClickListener(this);
    }

    private void initDialogData() {
        List<Pair<Integer, String>> pairs = new ArrayList<>();
        pairs.add(new Pair<>(R.drawable.load_failed, "退出登录"));
        pairs.add(new Pair<>(R.drawable.load_succeed, "退出客户端"));
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
            case R.id.setting_location_request:
                LocationUtil.getLocation(this);
                break;
            case R.id.setting_about_app:
                //todo 启动关于应用的activty
                startActivity(new Intent(this,CaptureActivity.class));
                break;
        }
    }

    // 删除numDays之前的缓存
    private int clearCacheFolder(File dir, long numDays){
        if(dir==null)
            dir=getCacheDir();
        if(numDays==0)
            numDays = System.currentTimeMillis();

        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child:dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child,numDays);
                    }

                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

}
