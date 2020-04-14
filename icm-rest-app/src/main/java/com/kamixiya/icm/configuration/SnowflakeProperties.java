package com.kamixiya.icm.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**.
 * SnowflakeProperties
 *
 * @author Zhu Jie
 * @date 2020/4/12
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "zj.kamixiya.snowflake")
class SnowflakeProperties {
    /**
     * 机房ID
     */
    private Integer idc;

    /**
     * 机器ID
     */
    private Integer machine;
}
