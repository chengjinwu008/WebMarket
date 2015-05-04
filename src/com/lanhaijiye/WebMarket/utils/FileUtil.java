package com.lanhaijiye.WebMarket.utils;

import java.io.*;

/**
 * Created by Administrator on 2015/4/29.
 */
public class FileUtil {
    public interface FileUtilListener{
        void extractFinished();
    }

    public static void extractFile(InputStream in,String path,FileUtilListener listener) throws IOException {
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
        StreamUtil.readStreamToStream(in, out);
        out.close();
        listener.extractFinished();
    }
}
