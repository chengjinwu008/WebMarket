package com.lanhaijiye.WebMarket.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.adapter.TextAdapter;
import com.lanhaijiye.WebMarket.alertDialog.IPhoneDialog;
import com.lanhaijiye.WebMarket.dao.AccountData;
import com.lanhaijiye.WebMarket.utils.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/5.
 */
public class LoginTableActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private Handler mHandler = new Handler();
    private EditText user_name_text;
    //隐藏密码组件
    //private CheckBox checkBox;
    private EditText login_password_text;
    private ListView accountsList;
    private PopupWindow pop;
    private String user_name;
    private String password;
    private Button confirmButton;
    public final static int REGISTER_CODE = 0x554;

    public static final String LOGIN_PARAMETER_USERNAME_KEY = "username";
    public static final String LOGIN_PARAMETER_PASSWORD_KEY = "pwd";

    //登录的api地址
    public static final String LOGIN_URL = "/wp/XM0000004/wwwroot/mobile/user.php?act=do_login";
    public static final int LOGIN_SUCCESS_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_table_layout);
        user_name_text = (EditText) findViewById(R.id.login_username_text);
        //注册密码可见
//        checkBox = (CheckBox) findViewById(R.id.password_visible_swap);
//        checkBox.setOnCheckedChangeListener(this);
        //注册账号下拉按钮
        View down_arrow_btn = findViewById(R.id.login_username_delete);
        down_arrow_btn.setOnClickListener(this);

        login_password_text = (EditText) findViewById(R.id.login_password_text);

        //注册登录按钮可用
        user_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                login_password_text.getText().clear();
            }
        });
        login_password_text.addTextChangedListener(this);
        View view = findViewById(R.id.login_back);
        view.setOnClickListener(this);

        confirmButton = (Button) findViewById(R.id.login_confirm_btn);
        confirmButton.setOnClickListener(this);

        //手机注册按钮监听
        View mobile_sign_up = findViewById(R.id.login_mobile_sign_up_btn);
        mobile_sign_up.setOnClickListener(this);
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
        if (isChecked) {
            login_password_text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            login_password_text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_back:
                this.finish();
                break;
            case R.id.login_username_delete:
                String[] accounts = AccountData.getAccounts(this);
                // 弹出账号记录
                if (accountsList == null) {
                    accountsList = new ListView(this);
                    accountsList.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    accountsList.setBackgroundResource(R.drawable.white_with_black_border);
                    accountsList.setDivider(null);
                    //item监听
                    accountsList.setOnItemClickListener(this);
                }
                if (accounts != null) {
                    TextAdapter adapter = new TextAdapter(accounts, this);
                    accountsList.setAdapter(adapter);
                }
                if (pop == null)
                    pop = new PopupWindow(user_name_text.getMeasuredWidth(), (int) DimensionUtil.getPixel(150, this.getResources().getDisplayMetrics()));
                PopWindowUtil.show(accountsList, user_name_text, null, pop, R.style.account_list_anim);
                break;
            case R.id.login_confirm_btn:
                //提交user_name和user_name
                //组装参数
                Map<String, String> name_password = new HashMap<>();
                name_password.put(LOGIN_PARAMETER_USERNAME_KEY, user_name);
                name_password.put(LOGIN_PARAMETER_PASSWORD_KEY, MessageDiagestUtil.MD5(password));
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            RequestUtil.requestURLWithParameter(RequestUtil.RequestMethod.POST, URLUtil.getURLStr(LOGIN_URL, LoginTableActivity.this), name_password, LOGIN_SUCCESS_CODE, new StreamUtil.StreamListener() {
                                @Override
                                public void onProgressUpdate(float progress) throws IOException {

                                }

                                @Override
                                public boolean getOutState() {
                                    return false;
                                }

                                @Override
                                public void onStreamReadFinished(byte[] bytes) {

                                    String res = new String(bytes);
                                    Log.e("login", res);
                                    try {
                                        //todo 对返回的json处理，得出登录失败或者成功的代码，并存token
                                        JSONObject json_res = new JSONObject(res);
                                        //通过解析json得到登录状态，分别进行处理
                                        //通过
                                        if (false) {

                                            //1.储存密码和记录
                                            if (!AccountData.isSaved(LoginTableActivity.this, user_name))
                                                AccountData.putAccount(LoginTableActivity.this, user_name);
                                            SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(LoginTableActivity.this, SharedPreferenceUtil.ACCOUNT);
                                            //2.返回登录成功信息
                                            preferenceUtil.putString(new String[]{SharedPreferenceUtil.USERNAME_KEY, SharedPreferenceUtil.PASSWORD_KEY},
                                                    new String[]{user_name, MessageDiagestUtil.MD5(password)}
                                            );
                                            setResult(RESULT_OK);
                                            finish();
                                            //不通过
                                        }else {
                                            // 弹窗处理
                                            mHandler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    IPhoneDialog dialog = new IPhoneDialog(LoginTableActivity.this);
                                                    dialog.show();
                                                    dialog.setOnCancelListener(new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            login_password_text.setText("");
                                                        }
                                                    }).setOnOKListener(new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            login_password_text.setText("");
                                                        }
                                                    }).changeOKText(getString(R.string.ok)).changeText(getString(R.string.login_fail));
                                                }
                                            });
                                        }

                                    } catch (JSONException e) {
                                        Log.e("Login", "not a json type");
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (IOException e) {
                            Log.e("login", "request login failed");
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            case R.id.login_mobile_sign_up_btn:
                //跳转到手机注册页面
                Intent intent = new Intent(this, SignUpActivity.class);
                this.startActivityForResult(intent, REGISTER_CODE);
//                this.finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //处理注册信息
        switch (requestCode){
            case REGISTER_CODE:
                if(resultCode == RESULT_OK)
                    setResult(RESULT_OK);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        user_name = user_name_text.getText().toString().trim();
        password = login_password_text.getText().toString().trim();

        if (user_name.length() > 0 && password.length() > 0) {
            if (!confirmButton.isEnabled())
                confirmButton.setEnabled(true);
        } else {
            if (confirmButton.isEnabled())
                confirmButton.setEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pop.dismiss();
        user_name_text.setText(((TextView) view).getText().toString());
    }
}
