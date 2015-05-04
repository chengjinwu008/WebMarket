package com.lanhaijiye.WebMarket.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import com.lanhaijiye.WebMarket.R;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2015/4/29.
 */
public class BaseActivity extends Activity {

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
                        Log.w("对话框","显示更新请求");
                        if(visible){
//                            Log.w("对话框","显示更新请求");
                            //显示更新框框
                            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                            if(view == null)
                            view = inflater.inflate(R.layout.alert_dialog,null);
//                            //todo 添加按钮响应，设置文字内容
//                            AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this).setView(view).create();
//                            dialog.show();
                            Button ok_btn = (Button) view.findViewById(R.id.alert_dialog_button_ok);

                            Button cancel_btn = (Button) view.findViewById(R.id.alert_dialog_button_cancel);

                            PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            window.setAnimationStyle(R.style.alert_dialog_anim);
                            window.setTouchable(true);
                            window.setOutsideTouchable(false);
                            window.setFocusable(true);
                            window.setBackgroundDrawable(new ColorDrawable(0));
                            window.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
                            View.OnClickListener listener =new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    window.dismiss();
                                    switch (v.getId()){
                                        case R.id.alert_dialog_button_ok:
                                            //todo 下载并安装更新
                                            break;
                                        case R.id.alert_dialog_button_cancel:
                                            break;
                                    }
                                }
                            };
                            ok_btn.setOnClickListener(listener);
                            cancel_btn.setOnClickListener(listener);
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
