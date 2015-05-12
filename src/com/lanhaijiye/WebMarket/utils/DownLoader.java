package com.lanhaijiye.WebMarket.utils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by android on 2015/5/12.
 */
public class DownLoader {

    public static void MultiThreadDownload(String url_str,File file) throws IOException {
        URL url = new URL(url_str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.getContentLength();
    }
}
