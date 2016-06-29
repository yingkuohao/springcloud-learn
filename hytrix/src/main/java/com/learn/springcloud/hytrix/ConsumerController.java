package com.learn.springcloud.hytrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/6/27
 * Time: 下午5:59
 * CopyRight: taobao
 * Descrption:
 */


@RestController
public class ConsumerController {
    @Autowired
       private ComputeService computeService;

       @RequestMapping(value = "/add", method = RequestMethod.GET)
       public String add() {
           return computeService.addService();
       }
}
