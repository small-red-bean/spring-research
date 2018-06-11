package com.redbean.springresearch.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Compressor {
    private static final Logger LOGGER = LogManager.getLogger(Compressor.class);

    /**
     * 压缩数据
     * @param data
     * @return
     */
    public static byte[] compress(byte[] data) {
        byte[] output = new byte[0];
        Deflater compresser = new Deflater();
        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            LOGGER.error("error", e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                LOGGER.error("error", e);
            }
        }
        compresser.end();
        return output;
    }

    /**
     * 解压缩数据
     * @param ca
     * @return
     * @throws Exception
     */
    public static byte[] infCompress(byte[] ca) throws Exception {
        byte[] result=new byte[2014];
        Inflater inf=new Inflater();
        inf.setInput(ca);
        int infLen = inf.inflate(result);
        inf.end();
        return Arrays.copyOf(result, infLen);
    }
}
