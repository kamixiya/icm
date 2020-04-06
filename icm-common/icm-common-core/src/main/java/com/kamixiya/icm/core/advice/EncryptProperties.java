package com.kamixiya.icm.core.advice;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * EncryptProperties 加密配置信息，从application.yml中读取
 *
 * @author Zhu Jie
 * @date 2020/4/5
 */
//@ConfigurationProperties(prefix = "matech.framework.api.encrypt")
@Getter
@Setter
@Component
public class EncryptProperties {

    /**
     * AES加密KEY, 16个字符
     */
    private String key = "!2#4%6&8(0AbCdEf";

    /**
     * 数据字符集编码
     */
    private String charset = "UTF-8";

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    private boolean enabled = false;

}
