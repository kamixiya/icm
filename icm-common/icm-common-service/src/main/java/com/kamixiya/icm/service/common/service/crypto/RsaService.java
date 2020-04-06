package com.kamixiya.icm.service.common.service.crypto;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * RsaService
 *
 * @author Zhu Jie
 * @date 2020/4/5
 */
public interface RsaService {

    /**
     * 取Rsa密钥
     *
     * @param clientId 客户端ID
     * @return 密钥
     * @throws NoSuchAlgorithmException 无此加密算法
     */
    Map<String, Key> getRsaKeyMap(String clientId) throws NoSuchAlgorithmException;

    /**
     * 取Aes加密key
     *
     * @param clientId 客户端ID
     * @return 加密key
     */
    String getAesKey(String clientId);
}
