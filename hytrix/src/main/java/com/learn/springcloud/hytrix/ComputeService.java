package com.learn.springcloud.hytrix;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/6/28
 * Time: 下午4:23
 * CopyRight: taobao
 * Descrption:
 */


@Service
public class ComputeService {
    @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "addServiceFallback")
        public String addService() {
            return restTemplate.getForEntity("http://cloud-simple-service/add?a=10&b=20", String.class).getBody();
        }

        public String addServiceFallback() {
            return "error";
        }

}
