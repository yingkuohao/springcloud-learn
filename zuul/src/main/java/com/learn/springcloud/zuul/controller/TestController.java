package com.learn.springcloud.zuul.controller;

import com.learn.springcloud.zuul.consts.ZuulConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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
    public Object index(@RequestParam("name") String name) {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running index method!]]]]]]]]]]]]]]");

        return name;
    }


}
