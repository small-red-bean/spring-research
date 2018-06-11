package com.redbean.springresearch.encrypt;


import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public final class RSACoder {
    private static final String ENCRYPTION_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "PublicKey";
    private static final String PRIVATE_KEY = "PrivateKey";
    private static final int KEY_SIZE = 512;
    private static final Map<String, Object> keyPairMap = new HashMap();

    public static void initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        keyPairMap.put(PUBLIC_KEY, publicKey);
        keyPairMap.put(PRIVATE_KEY, privateKey);
    }

    private static byte[] encryptOrdecryptByPrivateKey(byte[] data, String key, int mode) throws Exception {
        byte[] keyBytes = BASE64.decryptBASE64(key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, privateKey);

        return cipher.doFinal(data);
    }

    private static byte[] encryptOrdecryptByPublicKey(byte[] data, String key, int mode) throws Exception {
        byte[] keyBytes = BASE64.decryptBASE64(key);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 获取公钥
     * @return
     * @throws Exception
     */
    public static String getPublicKey() throws Exception {
        Key key = (Key)keyPairMap.get(PUBLIC_KEY);
        return BASE64.encryptBASE64(key.getEncoded());
    }

    /**
     * 获取私钥
     * @return
     * @throws Exception
     */
    public static String getPrivateKey() throws Exception {
        Key key = (Key) keyPairMap.get(PRIVATE_KEY);
        return BASE64.encryptBASE64(key.getEncoded());
    }

    /**
     * 私钥加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        return encryptOrdecryptByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 私钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {
        return encryptOrdecryptByPrivateKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 公钥加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
        return encryptOrdecryptByPublicKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 公钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        return encryptOrdecryptByPublicKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 用私钥对信息生成数字签名
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = BASE64.decryptBASE64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return BASE64.encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = BASE64.decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // ENCRYPTION_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(BASE64.decryptBASE64(sign));
    }
}
