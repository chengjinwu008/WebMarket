package com.lanhaijiye.WebMarket.utils;

import java.io.*;

/**
 * Created by Administrator on 2015/4/27.
 */
public class StreamUtil {

    static final int buffer_size = 8192;

    public static String readStream(InputStream stream) throws IOException {
        byte[] bytes = new byte[buffer_size];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        while((len = stream.read(bytes))!=-1){
            out.write(bytes,0,len);
        }
        out.flush();
        String res = out.toString();
        out.close();
        bytes=null;
        return res;
    }

    public static void readStreamToStream(InputStream in,OutputStream out) throws IOException {
        byte[] bytes = new byte[buffer_size];
        int len;
        while ((len = in.read(bytes))!=-1){
            out.write(bytes,0,len);
        }
        out.flush();
        bytes = null;
    }
}
