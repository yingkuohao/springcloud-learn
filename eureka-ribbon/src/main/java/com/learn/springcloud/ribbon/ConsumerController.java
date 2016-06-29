package com.learn.springcloud.ribbon;
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
     RestTemplate restTemplate;

     @RequestMapping(value = "/add", method = RequestMethod.GET)
     public String add() {
         return restTemplate.getForEntity("http://cloud-simple-service/add?a=10&b=20", String.class).getBody();
     }

}
