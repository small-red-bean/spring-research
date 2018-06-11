package com.redbean.springresearch.util;


import com.alibaba.fastjson.JSON;
import com.redbean.springresearch.encrypt.RSACoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

public final class CommunicationUtil {
    private static final Logger LOGGER = LogManager.getLogger(CommunicationUtil.class);

    public static <T> T readJson(HttpServletRequest request, Class<T> clazz) throws Exception {
        StringBuilder builder = new StringBuilder();
        InputStream inStream = request.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            builder.append(new String(buffer, 0, len));
        }
        inStream.close();

        inStream.close();
        String params = builder.toString();
        LOGGER.info("|" + SessionHolder.getSessionId() + "|密文|REQUEST|" + params);
        //解密
        String isEncrypt = request.getParameter("isEncrypt"); // 是否加密(0:不加密)
        String deCrypt = decrypt(isEncrypt, params);
        LOGGER.info("|" + SessionHolder.getSessionId() + "|明文|REQUEST|" + deCrypt);
        return JSON.parseObject(deCrypt,clazz);
    }

    public static String decrypt(String isEncrypt, String string) throws Exception {
        if ("0".equals(isEncrypt)) { // 不加密
            return string;
        }
        byte[] b = RSACoder.encryptByPrivateKey(string.getBytes("UTF-8"), Constants.privateKey);
        return new String(b, "UTF-8");
    }
}
