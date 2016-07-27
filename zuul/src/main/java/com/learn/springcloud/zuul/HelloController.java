package com.learn.springcloud.zuul;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/index")
       public Object index() {
           return "index";
       }

       @RequestMapping("/home")
       public Object home() {
           return "home";
       }
}
