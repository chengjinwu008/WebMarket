package com.lanhaijiye.WebMarket.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.services.UpdateService;
import com.lanhaijiye.WebMarket.utils.PopWindowUtil;

/**
 * Created by Administrator on 2015/4/29.
 */
public class BaseActivity extends FragmentActivity {
    public static final int LOGIN_CODE = 0x349;//登录代码
    public static final String SHUT_DOWN_ORDER = "com.lanhaijiye.WebMarket.ACTION_SHUT_DOWN";
    public static final String SHOW_UPDATE_CONFIRM_ORDER = "com.lanhaijiye.WebMarket.ACTION_SHOW_UPDATE_CONFIRM";
    protected boolean visible = false;
    private BroadcastReceiver receiver;
    private View view;

    @Override
    protected void onStart() {
        super.onStart();
        visible =true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(SHUT_DOWN_ORDER);
        filter.addAction(SHOW_UPDATE_CONFIRM_ORDER);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()){
                    case SHUT_DOWN_ORDER:
                        BaseActivity.this.finish();
                        break;
                    case SHOW_UPDATE_CONFIRM_ORDER:
                        if(visible){
                            //显示更新框框
                            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            if(view == null)
                            view = inflater.inflate(R.layout.alert_dialog,null);
//                            //todo 添加按钮响应，设置文字内容
                            Button ok_btn = (Button) view.findViewById(R.id.alert_dialog_button_ok);

                            Button cancel_btn = (Button) view.findViewById(R.id.alert_dialog_button_cancel);

                            PopupWindow window = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            View.OnClickListener listener =new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    window.dismiss();
                                    Log.i(getString(R.string.btn_click), v.getId()+";"+R.id.alert_dialog_button_ok);

                                    switch (v.getId()){
                                        case R.id.alert_dialog_button_ok:
                                            //下载并安装更新
                                            startService(new Intent(UpdateService.UPDATE_DOWNLOAD_INSTALL));
                                            Log.i(getString(R.string.btn_click),getString(R.string.send_download_request));
                                            break;
                                        case R.id.alert_dialog_button_cancel:
                                            break;
                                    }
                                }
                            };
                            ok_btn.setOnClickListener(listener);
                            cancel_btn.setOnClickListener(listener);
                            PopWindowUtil.show(view,null ,findViewById(android.R.id.content), window, R.style.alert_dialog_anim);
                        }
                        break;
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        visible=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
