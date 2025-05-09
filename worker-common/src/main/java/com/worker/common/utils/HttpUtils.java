package com.worker.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *  @author
 * @date: 2024/11/6 13:44
 */
@Slf4j
public class HttpUtils {
    private static String getResponseWithTimeout(Request q) {
        String ret = null;

        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient client = httpBuilder.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        try {
            Response s = client.newCall(q).execute();
            ret = s.body().string();
            s.close();
        } catch (SocketTimeoutException e) {
            log.info("get result timeout");
        } catch (IOException e) {
            log.info("get result error " + e.getMessage());
        }

        return ret;
    }

    public static String sendPostLink(String url, HashMap<String, String> headers, String link){
        RequestBody body;

        if (link.isEmpty()) {
            log.info("The send link is empty.");
            return null;
        } else {
            url=url+"&audio_address="+link;
        }

        Headers.Builder hb = new Headers.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                hb.add(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .headers(hb.build())
                .build();

        return getResponseWithTimeout(request);
    }

    public static String sendPostData(String url, HashMap<String, String> headers, byte[] data) {
        RequestBody body;

        if (data.length == 0) {
            log.info("The send data is empty.");
            return null;
        } else {
            body = RequestBody.create(MediaType.parse("application/octet-stream"), data);
        }

        Headers.Builder hb = new Headers.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                hb.add(entry.getKey(), entry.getValue());
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .headers(hb.build())
                .post(body)
                .build();

        return getResponseWithTimeout(request);
    }
}
