package com.kamixiya.icm.controller.common.token;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * TokenController
 *
 * @author Zhu Jie
 * @date 2020/3/9
 */
@Api(tags = {"令牌管理"})
@RestController
@RequestMapping("/api/token")
@Validated
public class TokenController {

    @ApiOperation(value = "使用用户账号和密码生成访问令牌")
    @PostMapping("")
    public String generateToken() {
        return "123";
    }

}
