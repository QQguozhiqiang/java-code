package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName QuickController
 * @Description TODO
 * @Author gzq
 * @Date 2020/6/11 22:06
 * @Version 1.0
 */
@Controller
public class QuickController {
    @RequestMapping("/quick")
    @ResponseBody
    public String quick(){
        return "hello springboot dev";
    }
}
