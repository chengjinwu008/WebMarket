package com.lanhaijiye.WebMarket.utils;

import android.util.Log;
import com.lanhaijiye.WebMarket.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/4/29.
 */
public class NetWorkUtils {
    private String url_str ;
    private HttpURLConnection connection;
    private BufferedInputStream stream;
    public static final int TIME_OUT = 5000;
    private int length;

    public void close() throws IOException {
        if(connection!=null){
            connection.disconnect();
        }
        if(stream!=null)
        stream.close();
    }

    public NetWorkUtils(String url_str) {
        this.url_str = url_str;
    }

    public InputStream readUrlASStream(Map<String,String> params,int result_code) throws IOException {
        if(url_str==null)
            return null;
        URL url = new URL(url_str);
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(TIME_OUT);
        if(params!=null){
            Set<Map.Entry<String,String>> set = params.entrySet();
            for(Map.Entry<String,String> kv:set){
                connection.addRequestProperty(kv.getKey(),kv.getValue());
            }
        }
        connection.setDoInput(true);
        connection.connect();
        length =  connection.getContentLength();
        Log.i( "result_code", String.valueOf(connection.getResponseCode()));
        if(connection.getResponseCode()==result_code){
            stream = new BufferedInputStream(connection.getInputStream());
            return stream;
        }
        throw new IOException();
    }

    public int getContentLength(){
        return length;
    }
}
