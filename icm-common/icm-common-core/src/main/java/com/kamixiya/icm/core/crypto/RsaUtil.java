package com.kamixiya.icm.core.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RsaUtil RSA非对称加密工具类
 *
 * @author Zhu Jie
 * @date 2020/4/5
 */
public class RsaUtil {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "PUBLIC_KEY";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";

    private RsaUtil() {
    }

    private static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    private static String encryptBASE64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 初始化RSA密钥
     *
     * @return 公钥和私钥
     * @throws NoSuchAlgorithmException 没有RSA算法
     */
    public static Map<String, Key> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        Map<String, Key> keyMap = new HashMap<>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
        return keyMap;
    }


    /**
     * 用私钥对数据签名
     *
     * @param data       数据
     * @param privateKey 私钥
     * @return 签名
     * @throws NoSuchAlgorithmException 无此加密算法
     * @throws InvalidKeySpecException  无效的Key定义
     * @throws InvalidKeyException      无效的Key
     * @throws SignatureException       签名异常
     */
    public static String sign(byte[] data, String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);
        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        return encryptBASE64(signature.sign());
    }

    /**
     * 验证数字签名
     *
     * @param data      数据
     * @param publicKey 公钥
     * @param sign      签名
     * @return 数字签名是否通过
     * @throws NoSuchAlgorithmException 无RAS加密算法
     * @throws InvalidKeyException      无效的Key
     * @throws InvalidKeySpecException  无效的key定义
     * @throws SignatureException       签名出错
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, SignatureException {
        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);
        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 使用私钥解密
     *
     * @param data 数据
     * @param key  私钥
     * @return 解密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }


    /**
     * 使用私钥解密
     *
     * @param data base64编辑的加密数据
     * @param key  私钥
     * @return 解密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static String decryptByPrivateKey(String data, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
        return new String(decryptByPrivateKey(decryptBASE64(data), key));
    }


    /**
     * 使用公钥解密
     *
     * @param data 数据
     * @param key  公钥
     * @return 解密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用公钥解密
     *
     * @param data 数据
     * @param key  公钥
     * @return 解密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static String decryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return new String(decryptByPrivateKey(decryptBASE64(data), key));
    }

    /**
     * 使用公钥加密
     *
     * @param data 数据
     * @param key  公钥
     * @return 加密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static byte[] encryptByPublicKey(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 使用公钥加密
     *
     * @param data 数据
     * @param key  公钥
     * @return 加密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static String encryptByPublicKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encryptBASE64(encryptByPublicKey(data.getBytes(), key));
    }

    /**
     * 用私钥加密
     *
     * @param data 数据
     * @param key  私钥
     * @return 加密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 用私钥加密
     *
     * @param data 数据
     * @param key  私钥
     * @return 加密后数据
     * @throws NoSuchAlgorithmException  无此算法
     * @throws InvalidKeySpecException   无效的Key定义
     * @throws NoSuchPaddingException    无此填充
     * @throws InvalidKeyException       无效的Key
     * @throws BadPaddingException       错误的填充
     * @throws IllegalBlockSizeException 不合法的块大小
     */
    public static String encryptByPrivateKey(String data, String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        return encryptBASE64(encryptByPrivateKey(data.getBytes(), key));
    }

    /**
     * 从key map中取私钥
     *
     * @param keyMap 密钥
     * @return 私钥的base64字符串
     */
    public static String getPrivateKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取公钥
     *
     * @param keyMap 密钥
     * @return 公钥的base64字符串
     */
    public static String getPublicKey(Map<String, Key> keyMap) {
        Key key = keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

}
