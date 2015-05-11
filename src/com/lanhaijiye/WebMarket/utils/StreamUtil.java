package com.lanhaijiye.WebMarket.utils;

import android.util.Log;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by Administrator on 2015/4/27.
 */
public class StreamUtil {

    static final int buffer_size = 8192;

    public interface StreamListener {
        /**
         * 以百分比的形式返回进度
         *
         * @param progress
         */
        void onProgressUpdate(float progress) throws IOException;

        /**
         * true继续传输，false则中断传输
         * @return
         */
        boolean getOutState();

        void onStreamReadFinished(byte[] bytes);
    }

    /**
     * 读取流并以字符串的形式返回
     *
     * @param stream    需要读取的流
     * @param maxLength 流的长度（仅用于计算进度）
     * @param listener  进度监听
     * @return
     * @throws IOException
     */
    public static String readStream(InputStream stream, int maxLength, StreamListener listener) throws IOException {
        byte[] bytes = new byte[buffer_size];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        int progress = 0;
        while ((len = stream.read(bytes)) != -1) {
            out.write(bytes, 0, len);
            progress += len;
            if (listener != null)
                listener.onProgressUpdate(progress / maxLength * 100);
        }
        out.flush();
        String res = out.toString();
        out.close();
        bytes = null;

        if(listener!=null)
            listener.onStreamReadFinished(res.getBytes());

        return res;
    }

    public static void readStreamToStream(InputStream in, OutputStream out, int maxLength, StreamListener listener) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[buffer_size];
        int len;
        float progress = 0;
//        Log.i("maxLength",maxLength+"");
        while ((len = in.read(bytes)) != -1) {
            if(listener!=null)
                if(!listener.getOutState())
                    break;
            out.write(bytes, 0, len);
            byteArrayOutputStream.write(bytes,0,len);
            progress += len;
//            Log.i("process",progress / maxLength * 100+"");
            if (listener != null)
                listener.onProgressUpdate(progress / maxLength * 100);
        }
        out.flush();
        bytes = null;
        if(listener!=null)
            listener.onStreamReadFinished(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
    }

    /**
     * 读取流并以字节数组的形式返回
     *
     * @param stream    需要读取的流
     * @param maxLength 流的长度（仅用于计算进度）
     * @param listener  进度监听
     * @return 字节数组
     * @throws IOException
     */
    public static byte[] readStreamToBytes(InputStream stream, int maxLength, StreamListener listener) throws IOException {
        byte[] bytes = new byte[buffer_size];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        int progress = 0;
        while ((len = stream.read(bytes)) != -1) {
            out.write(bytes, 0, len);
            progress += len;
            if (listener != null)
                listener.onProgressUpdate(progress / maxLength * 100);
        }
        out.flush();
        String res = out.toString();
        out.close();
        bytes = null;
        if(listener!=null)
            listener.onStreamReadFinished(res.getBytes());
        return res.getBytes();
    }
}
