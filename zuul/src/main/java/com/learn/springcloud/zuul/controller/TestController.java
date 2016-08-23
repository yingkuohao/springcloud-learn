package com.learn.springcloud.zuul.controller;

import com.learn.springcloud.zuul.consts.ZuulConsts;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/7/18
 * Time: 下午3:12
 * CopyRight: taobao
 * Descrption:
 */
@RestController
@RequestMapping("/springboot")
public class TestController {

    private static Logger log = LoggerFactory.getLogger(TestController.class);


    @RequestMapping(value = "/sayHello/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Object index(@PathVariable("name") String name) {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running index method!]]]]]]]]]]]]]]");

        return name;
    }

    @RequestMapping(value = "/sayUser", method = RequestMethod.GET)
    @ResponseBody
    public String sayUser(String name) {
        System.out.println("userName;" + name);
        return "hello";
    }

    @RequestMapping("/response")
    public Object home() {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running response method!]]]]]]]]]]]]]]");
        RequestContext ctx = RequestContext.getCurrentContext();
           HttpServletRequest request = ctx.getRequest();
           HttpServletResponse response = ctx.getResponse();
        return  response;
    }
}
