package com.lanhaijiye.WebMarket.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.cjq.DownLoader.utils.Downloader;
import com.cjq.DownLoader.utils.ProgressLogUtil;
import com.lanhaijiye.WebMarket.R;
import com.lanhaijiye.WebMarket.activities.BaseActivity;
import com.lanhaijiye.WebMarket.utils.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by Administrator on 2015/4/29.
 */
public class UpdateService extends IntentService {
    public static final String name = "temp_service";
    private static final int NOTIFICATION_ID = 0x654;
    public static final String UPDATE_CHECK = "com.lanhaijiye.WebMarket.ACTION_CHECK_UPDATE";
    public static final String UPDATE_DOWNLOAD_INSTALL = "com.lanhaijiye.WebMarket.ACTION_DOWNLOAD_INSTALL_UPDATE";
    public static final String STOP_DOWNLOAD = "com.lanhaijiye.WebMarket.ACTION_STOP_DOWNLOAD";
    public static final String PAUSE_DOWNLOAD = "com.lanhaijiye.WebMarket.ACTION_PAUSE_DOWNLOAD";
    public static final String CHECK_URL = "http://www.zyjj.com/mobile/version_check";
    public static final String DOWNLOAD_URL = /*"http://www.zyjj.com/mobile/apk/"*/"http://bcscdn.baidu.com/netdisk/BaiduYun_7.8.1.apk";
    public static final String UPDATE_INFO_URL = "http://www.zyjj.com/mobile/apk/version_info";
    private static final String UPDATE_TEMP_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    private static final String PACKAGE_TYPE = "application/vnd.android.package-archive";
    private static final int DOWNLOAD_THREAD_COUNT = 3;

    private Handler mHandler = new Handler();
    private BroadcastReceiver reviver;
    private Notification notification;
    private NotificationManager manager;
    private Downloader downloader;
//    //true表示没有停止，false表示下载中断了
//    private boolean downloadFlag = true;//单线程下载使用

    public UpdateService(String name) {
        super(name);
    }

    public UpdateService() {
        this(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) UpdateService.this.getSystemService(NOTIFICATION_SERVICE);
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
                    Log.e(getString(R.string.update_service), getString(R.string.net_err));
                }
                break;
            case UPDATE_DOWNLOAD_INSTALL:
                Log.i(getString(R.string.handle_request), getString(R.string.handle_download_request));
                try {
                    doDownloadAndInstall();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(getString(R.string.update_service), getString(R.string.net_err));
                }
        }
    }

    //下载安装
    private void doDownloadAndInstall() throws IOException {
//            单线程下载
//        NetWorkUtils connection = new NetWorkUtils(DOWNLOAD_URL);
//        try {
//            InputStream in = connection.readUrlASStream(null, 200);
//            FileUtil.extractFile(in, UPDATE_TEMP_PATH, new FileUtil.FileUtilListener() {
//                @Override
//                public void extractFinished() {
//                    //下载完毕，进行安装了
//                    Log.i(getString(R.string.download), getString(R.string.download_done));
//                    manager.cancel(NOTIFICATION_ID);
//                    UpdateService.this.unregisterReceiver(reviver);
//                    reviver = null;
//                    //todo 安装更新
//                    if (downloadFlag) {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(new File(UPDATE_TEMP_PATH)), PACKAGE_TYPE);
//                        startActivity(intent);
//                    } else {
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(UpdateService.this,getString(R.string.download_stoped), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        //删除临时文件
//                        new File(UPDATE_TEMP_PATH).delete();
//                    }
//                }
//
//                @Override
//                public void onProgressUpdated(float progress) throws IOException {
//                    //下载进度更新
//                    if (notification == null) {
//                        notification = new Notification(R.drawable.ic_launcher, "下载", System.currentTimeMillis());
//                        notification.flags = Notification.FLAG_ONGOING_EVENT;
//
//                        reviver = new BroadcastReceiver() {
//                            @Override
//                            public void onReceive(Context context, Intent intent) {
//                                //todo 处理结束下载请求
//                                downloadFlag = false;
//                                NotificationBarUtil.collapseStatusBar(UpdateService.this.getApplicationContext());
//                            }
//                        };
//                    }
//                    IntentFilter filter = new IntentFilter(STOP_DOWNLOAD);
//                    UpdateService.this.registerReceiver(reviver, filter);
//                    Intent intent = new Intent(STOP_DOWNLOAD);
//                    PendingIntent pendingIntent = PendingIntent.getBroadcast(UpdateService.this, 0, intent, 0);
//                    notification.contentView = new RemoteViews(UpdateService.this.getPackageName(), R.layout.download_notification_layout);
//                    notification.contentView.setOnClickPendingIntent(R.id.download_notification_stop_btn, pendingIntent);
//                    notification.contentView.setTextViewText(R.id.download_process_hint, progress + "");
//                    manager.notify(NOTIFICATION_ID, notification);
//                }
//
//                @Override
//                public boolean getOutState() {
//                    return downloadFlag;
//                }
//
//            }, connection.getContentLength());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            connection.close();
//        }
        //多线程下载
        downloader = new Downloader(DOWNLOAD_URL, UPDATE_TEMP_PATH, new ProgressLogUtil(this), DOWNLOAD_THREAD_COUNT, new Downloader.DownloaderListener() {
            @Override
            public void onPrepareFinished(Downloader downloader) {
                try {
                    downloader.download();
                    notification.contentView.setTextViewText(R.id.text, getString(R.string.downloading));

                    manager.notify(NOTIFICATION_ID, notification);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDownloading(int downloaded_size) {
                //todo 通知栏样式修改

                if(reviver==null){
                    reviver = new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            //处理结束下载请求
                            switch (intent.getAction()) {
                                case STOP_DOWNLOAD:
                                    downloader.stop();
                                    NotificationBarUtil.collapseStatusBar(UpdateService.this.getApplicationContext());
                                    break;
                                case PAUSE_DOWNLOAD:
                                    if (downloader.isPaused())
                                        downloader.downloadPrepare();
                                    else
                                        downloader.pause();
                                    break;
                            }

                        }
                    };
                    IntentFilter filter = new IntentFilter(STOP_DOWNLOAD);
                    filter.addAction(PAUSE_DOWNLOAD);
                    UpdateService.this.registerReceiver(reviver, filter);
                }

                Intent intent = new Intent(STOP_DOWNLOAD);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(UpdateService.this, 0, intent, 0);
                Intent intent1 = new Intent(PAUSE_DOWNLOAD);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(UpdateService.this, 0, intent1, 0);

                notification.contentView = new RemoteViews(UpdateService.this.getPackageName(), R.layout.download_notification_layout);
                notification.contentView.setOnClickPendingIntent(R.id.download_notification_stop_btn, pendingIntent);
                notification.contentView.setOnClickPendingIntent(R.id.download_notification_pause_btn, pendingIntent1);
                notification.contentView.setTextViewText(R.id.text, getString(R.string.downloading));
                notification.contentView.setTextViewText(R.id.download_process_hint, downloaded_size + "/" + downloader.getFileLength());
                notification.contentView.setProgressBar(R.id.progress, downloader.getFileLength(), downloaded_size, false);

                manager.notify(NOTIFICATION_ID, notification);
            }

            @Override
            public void onDownloadFinished() {
//                下载完毕，进行安装了
                //需要判断downloader的状态 如果是暂停，则不需要安装，并且通知也不需要收回
                if(downloader.isPaused()&&!downloader.isStoped()){

                }else {
                    Log.i(getString(R.string.download), getString(R.string.download_done));
                    manager.cancel(NOTIFICATION_ID);
                    UpdateService.this.unregisterReceiver(reviver);
                    reviver = null;
                    //安装更新
                    if (!downloader.isStoped()) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(downloader.getFile()), PACKAGE_TYPE);
                        startActivity(intent);
                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(UpdateService.this, getString(R.string.download_stoped), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //删除临时文件
                        downloader.getFile().delete();
                    }
                }
            }
        });
        if (notification == null) {
            notification = new Notification(R.drawable.ic_launcher, "下载", System.currentTimeMillis());
            notification.flags = Notification.FLAG_ONGOING_EVENT;

            notification.contentView = new RemoteViews(UpdateService.this.getPackageName(), R.layout.download_notification_layout);
            notification.contentView.setTextViewText(R.id.text, getString(R.string.prepare_download));
            manager.notify(NOTIFICATION_ID, notification);
        }
        downloader.downloadPrepare();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reviver != null) {
            unregisterReceiver(reviver);
            reviver = null;
        }
    }

    //检查更新
    private void doCheck() throws PackageManager.NameNotFoundException, IOException {
        NetWorkUtils connection = new NetWorkUtils(CHECK_URL);
        InputStream stream = null;
        try {
            stream = connection.readUrlASStream(null, 200);
            String version_code = StreamUtil.readStream(stream, connection.getContentLength(), null);
            String curr_code = PackageUtil.getAppVersion(getApplicationContext());

            if (Integer.valueOf(version_code) > Integer.valueOf(curr_code)) {
//        需要更新
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
//            //网络连接失败
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
        String info = StreamUtil.readStream(connection.readUrlASStream(null, 200), connection.getContentLength(), null);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BaseActivity.SHOW_UPDATE_CONFIRM_ORDER);
                //把信息封装到intent里面
                intent.putExtra(UPDATE_INFO_URL, info);//放入info
                intent.putExtra(CHECK_URL, version_code);//放入版本号
                sendBroadcast(intent);
            }
        });
        connection.close();
    }
}
