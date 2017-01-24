package com.alicp.logapp.log.conroller;

import com.alicp.logapp.log.endpoint.metric.MyMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/endpoint")
public class TestEndPointController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    MyMetric myMetric;

    @RequestMapping("/counter")
    @ResponseBody
    public String exampleCounterMethod() {
        //spring内置了
        myMetric.exampleCounterMethod();
        return "counter";
    }

    @RequestMapping("/gauge")
    @ResponseBody
    public String exampleGaugeMethod() {
        myMetric.exampleGaugeMethod();
        return "gauge";
    }
}
