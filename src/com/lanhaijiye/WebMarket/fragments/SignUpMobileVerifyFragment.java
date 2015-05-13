package com.lanhaijiye.WebMarket.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.inter.Changeable;
import com.lanhaijiye.WebMarket.activities.inter.TimerFlagChangeable;
import com.lanhaijiye.WebMarket.dao.AccountData;
import com.lanhaijiye.WebMarket.fragments.abs.BaseFragment;
import com.lanhaijiye.WebMarket.fragments.inter.MobileNumRecieverable;
import com.lanhaijiye.WebMarket.utils.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/7.
 */
public class SignUpMobileVerifyFragment extends BaseFragment implements TimerFlagChangeable, MobileNumRecieverable, View.OnFocusChangeListener, View.OnClickListener, TimerForSeconds.TimerListener, TextWatcher {

    private static final int SECOND_TO_WAIT = 60;
    public static final String SIGN_UP_URL = "/wp/XM0000004/wwwroot/mobile/user.php?act=act_register";
    public static final String SIGN_UP_SMS_URL = "/wp/XM0000004/wwwroot/mobile/user.php?act=act_register_sms";
    public static final int SUCCESS_SEND_NUM_CODE = 200;
    public static final String SIGN_UP_REQUEST_PHONE_NUM_KEY = "phone";
    public static final String SIGN_UP_REQUEST_NICKNAME_KEY = "nickname";
    public static final String SIGN_UP_REQUEST_VERIFYCODE_KEY = "verify";
    public static final String SIGN_UP_REQUEST_PASSWORD_KEY = "password";
    private static final int SUCCESS_REQUEST_SIGN_UP_CODE = 200;

    private TextView numberView;
    private Button timerText;
    private Handler mHandler = new Handler();
    private boolean timerFlag = true;
    private String num;
    private EditText verify_code;
    private EditText nickname_field;
    private EditText password_field;
    private String verifyText;
    private String nicknameText;
    private String passwordText;
    private Button confirmButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_sign_up_verify_layout, container, false);

        numberView = (TextView) view.findViewById(R.id.sign_up_mobile_num);
        verify_code = (EditText) view.findViewById(R.id.sign_up_verify_code_text);
        nickname_field = (EditText) view.findViewById(R.id.sign_up_nickname_text);
        password_field = (EditText) view.findViewById(R.id.sign_up_password_text);
        timerText = (Button) view.findViewById(R.id.sign_up_sms_timer);
        timerText.setOnClickListener(this);
        verify_code.setOnFocusChangeListener(this);
        //todo 按键监听
        //注册返回按键
        view.findViewById(R.id.back).setOnClickListener(this);
        confirmButton = (Button) view.findViewById(R.id.login_confirm_btn);

        confirmButton.setOnClickListener(this);

        //监听输入框的变化
        verify_code.addTextChangedListener(this);
        nickname_field.addTextChangedListener(this);
        password_field.addTextChangedListener(this);
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            Activity activity =getActivity();
            InputMethodUtil.hideSoftInputMethod(activity);
        }else{
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodUtil.showSoftInputMethod(getActivity(), mHandler, verify_code,500);
                }
            },500);
        }

    }

    @Override
    public boolean canGoBack() {
        return false;
    }

    @Override
    public void goBack() {

    }

    @Override
    public void changeNum(String num,String preNum) {
        //在号码改变的同时调用短信接口
        this.num = num;
        timerFlag = true;
        sendSendSMSOrder("+"+preNum+num);
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
                sendSendSMSOrder(num);
                break;
            case R.id.back:
                //点击返回的时候
                backToMobileInput();
                timerFlag = false;
                break;
            case R.id.login_confirm_btn:
                //点击注册按钮需要做的事情
                //点击注册按钮则需要将1验证码2昵称3密码4手机号封装进行post请求
                Map<String, String> parameter = new HashMap<>();
                parameter.put(SIGN_UP_REQUEST_PHONE_NUM_KEY, num);
                parameter.put(SIGN_UP_REQUEST_PASSWORD_KEY, passwordText);
                parameter.put(SIGN_UP_REQUEST_VERIFYCODE_KEY, verifyText);
                parameter.put(SIGN_UP_REQUEST_NICKNAME_KEY, nicknameText);
                //请求后得到一个json对象，对json解析得知注册失败还是成功
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            RequestUtil.requestURLWithParameter(RequestUtil.RequestMethod.POST, URLUtil.getURLStr(SIGN_UP_URL, getActivity()), parameter, SUCCESS_REQUEST_SIGN_UP_CODE, new StreamUtil.StreamListener() {
                                @Override
                                public void onProgressUpdate(float progress) throws IOException {

                                }

                                @Override
                                public boolean getOutState() {
                                    return false;
                                }

                                @Override
                                public void onStreamReadFinished(byte[] bytes) {
                                    //获取到返回的json
                                    String res = new String(bytes);
                                    try {
                                        JSONObject jsonRes = new JSONObject(res);
                                        //解析json
                                        if(true)
                                        {
                                            //todo 保存登录信息，方便以后登录，并且保存token
                                            //解析出的结果是成功
                                            if (!AccountData.isSaved(getActivity(), num))
                                                AccountData.putAccount(getActivity(), num);
                                            SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(getActivity(), SharedPreferenceUtil.ACCOUNT);
                                            preferenceUtil.putString(new String[]{SharedPreferenceUtil.USERNAME_KEY, SharedPreferenceUtil.PASSWORD_KEY},
                                                    new String[]{num, MessageDiagestUtil.MD5(passwordText)}
                                            );
                                            //2.返回登录成功信息
                                            getActivity().setResult(Activity.RESULT_OK);
                                        }else{
                                            //解析出的结果是失败
                                            //直接用toast打印错误信息
                                            String text="text from json";
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        Log.e("SignUp","the result is not a json");
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            Log.e("SignUp", "can not connect to sms server!");
                            e.printStackTrace();
                        }
                    }
                };

                break;
        }
    }

    private void backToMobileInput() {
        //返回手机号输入界面
        ((Changeable) getActivity()).change(this);
    }

    private void sendSendSMSOrder(String num) {
        //todo 调用短信接口
        //封装参数
        Log.i("dianwaNUm",num);
        Map<String, String> parameter = new HashMap<>();
        parameter.put(SIGN_UP_REQUEST_PHONE_NUM_KEY, num);
        //新开线程进行短信接口调用
        new Thread() {
            @Override
            public void run() {
                try {
                    RequestUtil.requestURLWithParameter(RequestUtil.RequestMethod.POST, URLUtil.getURLStr(SIGN_UP_SMS_URL, getActivity()), parameter, SUCCESS_SEND_NUM_CODE, null);
                } catch (IOException e) {
                    Log.e("SignUp", "can not connect to sms server!");
                    e.printStackTrace();
                }
            }
        };

        timerText.setEnabled(false);
        timerText.setText(SECOND_TO_WAIT + getActivity().getResources().getString(R.string.sms_send_hint));
        new TimerForSeconds(SECOND_TO_WAIT, this).start();
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

    @Override
    public boolean getTimerFlag() {
        return timerFlag;
    }

    @Override
    public void changeTimerFlag(boolean flag) {
        this.timerFlag = flag;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //判断是否都输入了，并且输入符合要求
        verifyText = verify_code.getText().toString();
        nicknameText = nickname_field.getText().toString();
        passwordText = password_field.getText().toString();

        if (
                verifyText == null ||
                        verifyText.isEmpty() ||
                        nicknameText==null ||
                        nicknameText.trim().length()<2||
                        nicknameText.trim().length()>20 ||
                        passwordText.trim().length()<6 ||
                        passwordText.trim().length()>20
                ){
            //这种情况下不允许提交
            if(confirmButton.isEnabled()){
                confirmButton.setEnabled(false);
            }
        }
        else{
            if(!confirmButton.isEnabled()){
                confirmButton.setEnabled(true);
            }
        }
    }
}
