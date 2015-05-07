package com.lanhaijiye.WebMarket.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.fragments.inter.MobileNumRecieverable;
import com.lanhaijiye.WebMarket.utils.TimerForSeconds;

import java.util.Timer;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpMobileVerifyFragment extends BaseFragment implements MobileNumRecieverable, View.OnFocusChangeListener, View.OnClickListener, TimerForSeconds.TimerListener {

    private TextView numberView;
    private EditText verify_code;
    private static final int secondsToWait = 60;
    private Button timerText;
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_sign_up_verify_layout, container, false);

        numberView = (TextView) view.findViewById(R.id.sign_up_mobile_num);
        verify_code = (EditText) view.findViewById(R.id.sign_up_verify_code_text);
        timerText = (Button) view.findViewById(R.id.sign_up_sms_timer);
        timerText.setOnClickListener(this);
        verify_code.setOnFocusChangeListener(this);
        //todo 按键监听

        return view;
    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void setOnLoadFinishListener(LoadingListener listener) {

    }

    @Override
    public void changeNum(String num) {
        showNum(num);
    }

    private void showNum(String num) {
        numberView.setText(num);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        //验证码失去焦点的时候验证
        //todo 验证

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up_sms_timer:
                //点击发送短信按钮
                sendSendSMSOrder();
                break;
        }
    }

    private void sendSendSMSOrder() {
        timerText.setEnabled(false);
        timerText.setText(60 + getActivity().getResources().getString(R.string.sms_send_hint));
        new TimerForSeconds(60, this).start();
    }

    @Override
    public void onEverySeconds(int timeLeft) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                timerText.setText(timeLeft + getActivity().getResources().getString(R.string.sms_send_hint));
            }
        });
    }

    @Override
    public void onTimeUp() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                timerText.setEnabled(false);
                timerText.setText(getActivity().getResources().getString(R.string.send_sms));
            }
        });
    }
}
