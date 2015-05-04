package com.lanhaijiye.WebMarket.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.utils.FileUtil;
import com.lanhaijiye.WebMarket.utils.NetWorkUtils;
import com.lanhaijiye.WebMarket.utils.PackageUtil;
import com.lanhaijiye.WebMarket.utils.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UpdateService extends IntentService {
    public static final String name = "temp_service";
    public static final String UPDATE_CHECK = "com.lanhaijiye.WebMarket.ACTION_CHECK_UPDATE";
    public static final String UPDATE_DOWNLOAD_INSTALL = "com.lanhaijiye.WebMarket.ACTION_DOWNLOAD&INSTALL_UPDATE";
    public static final String CHECK_URL = "http://www.zyjj.com/mobile/version_check";
    public static final String DOWNLOAD_URL = "http://www.zyjj.com/mobile/apk/";
    public static final String UPDATE_INFO_URL = "http://www.zyjj.com/mobile/apk/version_info";
    public static final String TEMP_FILE_NAME = "hehe.apk";
    private static final String UPDATE_TEMP_PATH = Environment.getDownloadCacheDirectory().getPath() + File.separator + TEMP_FILE_NAME;
    private static final String PACKAGE_TYPE = "application/vnd.android.package-archive";

    private Handler mHandler = new Handler();

    public UpdateService(String name) {
        super(name);
    }

    public UpdateService() {
        this(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case UPDATE_CHECK:
                try {
                    doCheck();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("升级服务", "网络请求关闭出现错误");
                }
                break;
            case UPDATE_DOWNLOAD_INSTALL:
                try {
                    doDownloadAndInstall();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("升级服务", "网络请求关闭出现错误");
                }
        }
    }

    //下载安装
    private void doDownloadAndInstall() throws IOException {
        NetWorkUtils connection = new NetWorkUtils(DOWNLOAD_URL);
        try {
            InputStream in = connection.readUrlASStream(null, 206);
            FileUtil.extractFile(in, UPDATE_TEMP_PATH, new FileUtil.FileUtilListener() {
                @Override
                public void extractFinished() {
                    //下载完毕，进行安装了
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(UPDATE_TEMP_PATH)), PACKAGE_TYPE);
                    startActivity(intent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    //检查更新
    private void doCheck() throws PackageManager.NameNotFoundException, IOException {
        NetWorkUtils connection = new NetWorkUtils(CHECK_URL);
        InputStream stream = null;
        try {
            stream = connection.readUrlASStream(null, 200);
            String version_code = StreamUtil.readStream(stream);
            String curr_code = PackageUtil.getAppVersion(getApplicationContext());

            if (Integer.valueOf(version_code) > Integer.valueOf(curr_code)) {
                //需要更新
                applyUpdate(version_code);
            } else {
                //不需要更新
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.no_updates), Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        } catch (IOException e) {
            //todo 网络连接失败
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(UpdateService.this, getString(R.string.net_err_for_update), Toast.LENGTH_SHORT).show();
                }
            });
        } finally {
            connection.close();
        }
    }

    //向用户申请更新
    private void applyUpdate(String version_code) throws IOException {
        NetWorkUtils connection = new NetWorkUtils(UPDATE_INFO_URL);
        String info = StreamUtil.readStream(connection.readUrlASStream(null, 200));

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseActivity.SHOW_UPDATE_CONFIRM_ORDER);
                //todo 把信息封装到intent里面
                intent.putExtra(UPDATE_INFO_URL, info);//放入info
                intent.putExtra(CHECK_URL, version_code);//放入版本号
                sendBroadcast(intent);
            }
        });
        connection.close();
    }
}
