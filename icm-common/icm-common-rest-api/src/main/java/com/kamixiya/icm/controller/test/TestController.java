package com.kamixiya.icm.controller.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author Zhu Jie
 * @date 2020/3/14
 */
@Api(tags = {"测试token"})
@RestController
@RequestMapping("/api/test")
@Validated
public class TestController {

    @ApiOperation(value = "测试登录拦截")
    @PostMapping("")
    public String test() {
        return "没token看不到我哦";
    }
}
