package com.lanhaijiye.WebMarket.utils;

import java.io.*;

/**
 * Created by Administrator on 2015/4/29.
 */
public class FileUtil {
    public interface FileUtilListener{
        void extractFinished();
        void onProgressUpdated(float progress) throws IOException;
        boolean getOutState();
    }

    public static void extractFile(InputStream in,String path,FileUtilListener listener,int maxSize) throws IOException {
        File file =new File(path);
        if(file.exists()){
            //检查文件存在
            file.delete();
        }else{
            //检查目录存在
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
        }
        //创建文件流
        FileOutputStream out = new FileOutputStream(file);
        StreamUtil.readStreamToStream(in, out, maxSize, new StreamUtil.StreamListener() {
            @Override
            public void onProgressUpdate(float progress) throws IOException {
                if(listener!=null)
                    listener.onProgressUpdated(progress);
            }

            @Override
            public boolean getOutState() {
                if(listener!=null)
                    return listener.getOutState();
                return true;
            }

            @Override
            public void onStreamReadFinished(byte[] bytes) {

            }
        });
        out.close();
        if(listener!=null)
        listener.extractFinished();
    }
}
