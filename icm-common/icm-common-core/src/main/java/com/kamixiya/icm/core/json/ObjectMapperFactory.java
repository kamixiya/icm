package com.kamixiya.icm.core.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Json工厂类，获取缺省的ObjectMapper
 *
 * @author Zhu Jie
 * @date 2020/4/7
 */
public class ObjectMapperFactory {

    private ObjectMapperFactory() {
    }

    /**
     * 用于创建动态过滤的ObjectMapper, 这样就可以在controller上使用@Json来动态过滤属性
     *
     * @return app缺省配置的objectMapper
     */
    public static ObjectMapper getDefaultObjectMapper() {
        DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ObjectMapper objectMapper = new ObjectMapper()
                // 注册Hibernate Session
                .registerModule(new Hibernate5Module())
                // 设置输出包含的属性
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                // 设置禁止重复数据
                .configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
                // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                // 设置输入时忽略JSON字符串中存在而Java对象的枚举没有的属性
                .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
                // 设置输入对象为空时不直接抛出错误
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                // 日期格式化
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(dft)
                .setTimeZone(TimeZone.getTimeZone("GMT+8"))
                // 对象属性动态过滤
                .addMixIn(Object.class, DynamicFilterMixIn.class)
                .setFilterProvider(new DynamicFilterProvider())
                // 由于ID长度过大，需转成字符串处理
                .registerModule(new SimpleModule()
                        .addSerializer(Long.class, ToStringSerializer.instance)
                        .addSerializer(Long.TYPE, ToStringSerializer.instance));
        objectMapper.setConfig(objectMapper.getDeserializationConfig().with(
                new ObjectMapperDateFormatExtend(dft)));
        return objectMapper;
    }
}