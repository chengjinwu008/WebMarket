package com.lanhaijiye.WebMarket.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by CJQ on 2015/5/9.
 */
public class RequestUtil {

    public enum RequestMethod {
        POST, GET
    }

    //发送请求返回字节数组
    public static byte[] requestURLWithParameter(RequestMethod method, String url, Map<String, String> parameters, int succeedCode, StreamUtil.StreamListener listener) throws IOException {
        byte[] res = null;
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> item : parameters.entrySet()) {
            builder.append(item.getKey()).append("=").append(item.getValue()).append("&");
        }
        HttpURLConnection connection = null;
        switch (method) {
            case POST:
                //发送的是post请求
                URL urlAddress = new URL(url);
                connection = (HttpURLConnection) urlAddress.openConnection();
                connection.setRequestMethod(method.toString());
                connection.setConnectTimeout(NetWorkUtils.TIME_OUT);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
                BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(builder.toString().getBytes());
                out.flush();
                out.close();
                connection.connect();
                break;
            case GET:
                url += "?" + builder.toString();
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod(method.toString());
                connection.setConnectTimeout(NetWorkUtils.TIME_OUT);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();
                break;
        }
        if (connection.getResponseCode() == succeedCode) {
            InputStream in = new BufferedInputStream(connection.getInputStream());
            res = StreamUtil.readStreamToBytes(in, connection.getContentLength(), listener);
            in.close();
        }
        connection.disconnect();
        return res;
    }

}
