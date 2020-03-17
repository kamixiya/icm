package com.kamixiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SpringBoot入口
 *
 * @author Zhu Jie
 * @date 2020/3/6
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
@EnableCaching
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
