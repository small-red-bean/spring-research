package com.redbean.springresearch.util;


import com.alibaba.fastjson.JSON;
import com.redbean.springresearch.encrypt.RSACoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class CommunicationUtil {
    public static <T> T readJson(HttpServletRequest request, Class<T> clazz) throws Exception{
        int len = request.getContentLength();
        byte[] data = new byte[len];
        InputStream in = null;
        try
        {
            in = request.getInputStream();
            in.read(data, 0, len);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in != null) {
                in.close();
            }
        }
        return JSON.parseObject(new String(RSACoder.decryptByPublicKey(data, Constants.publicKey), "UTF-8"),clazz);
    }

    public static void responseClient(HttpServletResponse response, String rd) throws Exception {
        response.setHeader("Pragma", "no-cache");
        response.setContentType("text/plain;charset=UTF-8");
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(RSACoder.encryptByPublicKey(rd.getBytes("UTF-8"), Constants.publicKey));
        outputStream.close();
    }
}
