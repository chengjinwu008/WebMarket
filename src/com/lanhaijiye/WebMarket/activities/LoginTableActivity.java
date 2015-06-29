package com.lanhaijiye.WebMarket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.*;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lanhaijiye.WebMarket.CommonDataObject;
import com.lanhaijiye.WebMarket.MyApplication;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.dao.AccountData;
import com.lanhaijiye.WebMarket.entities.LoginEvent;
import com.lanhaijiye.WebMarket.entities.LoginRequestEntity;
import com.lanhaijiye.WebMarket.utils.InputMethodUtil;
import com.lanhaijiye.WebMarket.utils.SharedPreferenceUtil;
import com.ypy.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/5.
 */
public class LoginTableActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener, PlatformActionListener {

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
    public static final String LOGIN_URL = "/service/app.php";
    private RequestQueue mRequestQueue;

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

        //初始化第三方
//        ShareSDK.initSDK(this);
//        findViewById(R.id.login_wechat).setOnClickListener(this);
//        findViewById(R.id.login_sina).setOnClickListener(this);
//        findViewById(R.id.login_QQ).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        InputMethodUtil.showSoftInputMethod(this, mHandler, user_name_text, 500);
    }

    @Override
    protected void onStop() {
        super.onStop();
        InputMethodUtil.hideSoftInputMethod(this);
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
//            case R.id.login_username_delete:
//                String[] accounts = AccountData.getAccounts(this);
//                // 弹出账号记录
//                if (accountsList == null) {
//                    accountsList = new ListView(this);
//
//                    accountsList.setBackgroundResource(R.drawable.white_with_black_border);
//                    accountsList.setDivider(null);
//                    //item监听
//                    accountsList.setOnItemClickListener(this);
//                }
//                if (accounts != null) {
//                    TextAdapter adapter = new TextAdapter(accounts, this);
//                    accountsList.setAdapter(adapter);
//                }
//                if (pop == null)
//                    pop = new PopupWindow(user_name_text.getMeasuredWidth(), (int) DimensionUtil.getPixel(150, this.getResources().getDisplayMetrics()));
//                PopWindowUtil.show(accountsList, user_name_text, null, pop, R.style.account_list_anim);
//                break;
            case R.id.login_confirm_btn:
                //提交user_name和user_name
                //组装参数
                Map<String, String> name_password = new HashMap<>();
                name_password.put(LOGIN_PARAMETER_USERNAME_KEY, user_name);
                name_password.put(LOGIN_PARAMETER_PASSWORD_KEY, password);
                try {
                    doLogin(name_password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.login_mobile_sign_up_btn:
                //跳转到手机注册页面
                Intent intent = new Intent(this, SignUpActivity.class);
                this.startActivityForResult(intent, REGISTER_CODE);
//                this.finish();
                break;
//            case R.id.login_sina:
//                dealPlatform(ShareSDK.getPlatform(SinaWeibo.NAME));
//                break;
//            case R.id.login_QQ:
//                dealPlatform(ShareSDK.getPlatform(QQ.NAME));
//                break;
//            case R.id.login_wechat:
//                dealPlatform(ShareSDK.getPlatform(Wechat.NAME));
//                break;
        }
    }

//    private void dealPlatform(Platform platform) {
//
//        //判断指定平台是否已经完成授权
//        if (platform.isValid()) {
//            String userId = platform.getDb().getUserId();
//            if (userId != null) {
//                doLogin(platform.getName(), userId);
//                return;
//            }
//        }
//        platform.setPlatformActionListener(this);
//        // true不使用SSO授权，false使用SSO授权
//        platform.SSOSetting(false);
//        //获取用户资料
//        platform.showUser(null);
//    }

    private void doLogin(final Map<String, String> name_password) throws JSONException {
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    RequestUtil.requestURLWithParameter(RequestUtil.RequestMethod.POST, URLUtil.getURLStr(LOGIN_URL, LoginTableActivity.this), name_password, LOGIN_SUCCESS_CODE, new StreamUtil.StreamListener() {
//                        @Override
//                        public void onProgressUpdate(float progress) throws IOException {
//
//                        }
//
//                        @Override
//                        public boolean getOutState() {
//                            return false;
//                        }
//
//                        @Override
//                        public void onStreamReadFinished(byte[] bytes) {
//
//                            String res = new String(bytes);
//                            Log.e("login", res);
//                            try {
//                                //todo 对返回的json处理，得出登录失败或者成功的代码，并存token
//                                JSONObject json_res = new JSONObject(res);
//                                //通过解析json得到登录状态，分别进行处理
//                                //通过
//                                if (false) {
//
//                                    //1.储存密码和记录
//                                    if (!AccountData.isSaved(LoginTableActivity.this, user_name))
//                                        AccountData.putAccount(LoginTableActivity.this, user_name);
//                                    SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(LoginTableActivity.this, SharedPreferenceUtil.ACCOUNT);
//                                    //2.返回登录成功信息
//                                    preferenceUtil.putString(new String[]{SharedPreferenceUtil.USERNAME_KEY, SharedPreferenceUtil.PASSWORD_KEY},
//                                            new String[]{user_name, MessageDiagestUtil.MD5(password)}
//                                    );
//                                    setResult(RESULT_OK);
//                                    finish();
//                                    //不通过
//                                } else {
//                                    // 弹窗处理
//                                    mHandler.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            IPhoneDialog dialog = new IPhoneDialog(LoginTableActivity.this);
//                                            dialog.show();
//                                            dialog.setOnCancelListener(new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                    login_password_text.setText("");
//                                                }
//                                            }).setOnOKListener(new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    dialog.dismiss();
//                                                    login_password_text.setText("");
//                                                }
//                                            }).changeOKText(getString(R.string.ok)).changeText(getString(R.string.login_fail));
//                                        }
//                                    });
//                                }
//                            } catch (JSONException e) {
//                                Log.e("Login", "not a json type");
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } catch (IOException e) {
//                    Log.e("login", "request login failed");
//                    e.printStackTrace();
//                }
//            }
//        }.start();
        MyApplication application = (MyApplication) getApplication();
        //构建请求实体

        LoginRequestEntity.Data data = new LoginRequestEntity.Data(application.imei,name_password.get(LOGIN_PARAMETER_USERNAME_KEY),name_password.get(LOGIN_PARAMETER_PASSWORD_KEY));
        LoginRequestEntity entity = new LoginRequestEntity("0002",data);
        StringRequest request = new StringRequest(Request.Method.POST, CommonDataObject.MAIN_URL + LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("login",response);
                try {
                    JSONObject object = new JSONObject(response);
                    String code = object.getString("code");
                    Toast.makeText(LoginTableActivity.this,object.getString("msg"), Toast.LENGTH_SHORT).show();
                    if("0000".equals(code)){
                        //储存信息
                        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil(LoginTableActivity.this,SharedPreferenceUtil.ACCOUNT);
                        sharedPreferenceUtil.putString(new String[]{
                                SharedPreferenceUtil.STATE_SESSION_ID,
                                SharedPreferenceUtil.STATE_USER_ID
                        },new String[]{
                                object.getJSONObject("data").getString("sessionId"),
                                object.getJSONObject("data").getString("userId")
                        });
                        if(!AccountData.isSaved(LoginTableActivity.this,entity.getData().getPhoneNumber())){
                            AccountData.putAccount(LoginTableActivity.this,entity.getData().getPhoneNumber());
                        }

                        EventBus.getDefault().post(new LoginEvent());

                        LoginTableActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginTableActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("opjson",application.gson.toJson(entity));
                Log.i("loginjson",application.gson.toJson(entity));
                return params;
            }
        };

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(request);
        mRequestQueue.start();
    }

//    private void doLogin(String platformName, String id) {
//        Map<String, String> params = new HashMap<>();
//        params.put(platformName, id);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    RequestUtil.requestURLWithParameter(RequestUtil.RequestMethod.POST, URLUtil.getURLStr(LOGIN_URL, LoginTableActivity.this), params, LOGIN_SUCCESS_CODE, new StreamUtil.StreamListener() {
//                        @Override
//                        public void onProgressUpdate(float progress) throws IOException {
//
//                        }
//
//                        @Override
//                        public boolean getOutState() {
//                            return false;
//                        }
//
//                        @Override
//                        public void onStreamReadFinished(byte[] bytes) {
//
//                            String res = new String(bytes);
//                            Log.e("login", res);
//                            try {
//                                //todo 对返回的json处理，得出登录失败或者成功的代码，并存token
//                                JSONObject json_res = new JSONObject(res);
//                                //通过解析json得到登录状态，分别进行处理
//                                //通过
//                                //todo 第三方登录成功
//                                SharedPreferenceUtil preferenceUtil = new SharedPreferenceUtil(LoginTableActivity.this, SharedPreferenceUtil.ACCOUNT);
//                                //2.返回登录成功信息
//                                //保存登录信息
//                                Set<String> keys = params.keySet();
//                                String[] data = new String[2];
//                                for (String key : keys) {
//                                    data[0] = key;
//                                    data[1] = params.get(key);
//                                }
//
//                                if (data.length != 0)
//                                    preferenceUtil.putString(new String[]{SharedPreferenceUtil.STATE_PLATFORM, SharedPreferenceUtil.STATE_PLATFORM_ID},
//                                            data
//                                    );
//                                setResult(RESULT_OK);
//                                finish();
//                                //不通过
//                            } catch (JSONException e) {
//                                Log.e("Login", "not a json type");
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                } catch (IOException e) {
//                    Log.e("login", "request login failed");
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //处理注册信息
        switch (requestCode) {
            case REGISTER_CODE:
                if (resultCode == RESULT_OK){
                    setResult(RESULT_OK);
                    finish();
                }
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

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //showUser成功返回 todo 返回必要信息给服务器进行默认注册，或者要求输入密码都行
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        //showUser返回失败
        platform.removeAccount();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        //取消登录
    }
}
