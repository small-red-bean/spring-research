package com.redbean.springresearch.http;


import com.redbean.springresearch.encrypt.RSACoder;
import com.redbean.springresearch.util.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class HttpServer {
    private static final int CONNECT_TIME_OUT = 10 * 1000;
    private static final int SOCKET_TIME_OUT = 30 * 1000;
    private static final int REQUEST_TIME_OUT = 1 * 1000;
    private static final String CHARSET = "UTF-8";
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;

    static {
        if(httpClient == null) {
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(10);
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(5);
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
            requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(REQUEST_TIME_OUT)
                    .setConnectTimeout(CONNECT_TIME_OUT)
                    .setSocketTimeout(SOCKET_TIME_OUT).build();
            httpClient = httpClientBuilder.build();
        }
    }

    /**
     * 针对json参数的传输
     * @param url 请求地址
     * @param jsonData json格式数据
     * @param encrypt 加密
     * @return
     */
    public static String doPost(String url, String jsonData, boolean encrypt) {
        HttpPost httpPost = null;
        String result = null;
        HttpEntity httpEntity;

        try {
            httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setConfig(requestConfig);

            if(encrypt) {
                //数据加密
                byte[] data = (jsonData == null ? null : jsonData.getBytes(CHARSET));
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(RSACoder.encryptByPrivateKey(data, Constants.privateKey));
                byteArrayEntity.setContentEncoding(CHARSET);
                httpEntity = byteArrayEntity;
            } else {
                StringEntity stringEntity = new StringEntity(jsonData, Charset.forName(CHARSET));
                stringEntity.setContentEncoding(CHARSET);
                httpEntity = stringEntity;
            }

            httpPost.setEntity(httpEntity);
            HttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();

            if(status == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                result = dump(responseEntity, encrypt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(httpPost != null) {
                httpPost.releaseConnection();
            }
        }
        return result;
    }

    private static String dump(HttpEntity entity, boolean encrypt) throws Exception {
        InputStream in = null;
        try {
            int length = (int)entity.getContentLength();
            in = entity.getContent();
            byte[] data = new byte[length];
            in.read(data, 0, length);

            if(encrypt) {
                //数据解密
                return new String(RSACoder.decryptByPrivateKey(data, Constants.privateKey), CHARSET);
            }

            return new String(data, CHARSET);
        } finally {
            in.close();
        }
    }
}
