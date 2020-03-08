package com.kamixiya.icm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 欢迎页面控制器，此控制器只是展示如何使用thymeleaf模板技术。
 *
 * @author Zhu Jie
 * @date 2020/03/07
 */
@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome(Model model){
        model.addAttribute("swagger", "swagger-ui.html");
        return "welcome";
    }
}
