package com.kamixiya.icm.service.common.service.crypto;

import com.kamixiya.icm.core.advice.EncryptProperties;
import com.kamixiya.icm.core.crypto.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * RsaServiceImpl
 *
 * @author Zhu Jie
 * @date 2020/4/5
 */
@Service("rsaService")
public class RsaServiceImpl implements RsaService {

    private final EncryptProperties encryptProperties;

    @Autowired
    public RsaServiceImpl(EncryptProperties encryptProperties) {
        this.encryptProperties = encryptProperties;
    }

    @Override
    @Cacheable(cacheNames = "RsaKey", key = "#clientId")
    public Map<String, Key> getRsaKeyMap(String clientId) throws NoSuchAlgorithmException {
        return RsaUtil.initKey();
    }

    @Override
    public String getAesKey(String clientId) {
        return encryptProperties.getKey();
    }
}
