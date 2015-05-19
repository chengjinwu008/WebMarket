package com.lanhaijiye.WebMarket.utils;

import android.content.Context;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.lanhaijiye.WebMarket.R;

/**
 * Created by android on 2015/5/19.
 */
public class ShareUtil {
    public static void showShare(Context context,String text,String imagePath) {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //�ر�sso��Ȩ
        oks.disableSSOWhenAuthorize();

// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
        oks.setTitle(context.getString(R.string.share));
        // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
        oks.setTitleUrl("http://sharesdk.cn");
        // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
        oks.setText(text);
        // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
        if(imagePath!=null)
        oks.setImagePath(imagePath);//ȷ��SDcard������ڴ���ͼƬ
        // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
        oks.setUrl("http://sharesdk.cn");
        // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
        oks.setComment("���ǲ��������ı�");
        // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
        oks.setSiteUrl("http://sharesdk.cn");

// ��������GUI
        oks.show(context);
    }
}
