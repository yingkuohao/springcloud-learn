package com.learn.springcloud.zuul;

import com.learn.springcloud.zuul.consts.ZuulConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/7/18
 * Time: 下午3:12
 * CopyRight: taobao
 * Descrption:
 */
@RestController
public class HelloController {

    private static Logger log = LoggerFactory.getLogger(HelloController.class);


    @RequestMapping("/index")
    @ResponseBody
    public Object index() {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running index method!]]]]]]]]]]]]]]");

        return "index";
    }

    @RequestMapping("/home")
    public Object home() {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running home method!]]]]]]]]]]]]]]");
        return "home";
    }


    @RequestMapping("/errorpage")
     public Object errorpage() {
         log.info("ZuulConsts.error!!!");
         return "error";
     }

    @RequestMapping("/black/test")
    public Object black() {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running black method!]]]]]]]]]]]]]]");
        return "home";
    }

    @RequestMapping("/white/test")
    public Object white() {
        log.info("[[[[[[[[[[[[[[[[[[[[[[[running black method!]]]]]]]]]]]]]]");
        return "home";
    }

    @RequestMapping("/pv")
    public Object pv() {
        log.info("ZuulConsts.pvMap=" + ZuulConsts.pvMap.toString());
        return "pv";
    }

    @RequestMapping("/failover")
      public Object failover() {
          log.info("failover execute!" );
          return "failover";
  }
}
