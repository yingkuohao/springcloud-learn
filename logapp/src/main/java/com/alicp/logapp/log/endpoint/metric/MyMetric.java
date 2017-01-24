package com.alicp.logapp.log.endpoint.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午2:33
 * CopyRight: taobao
 * Descrption:       http://localhost:7002/metric
 */
@Component
public class MyMetric {
    private final CounterService counterService;
    private final GaugeService gaugeService;

    @Autowired
    public MyMetric(CounterService counterService, GaugeService gaugeService) {
        this.counterService = counterService;
        this.gaugeService = gaugeService;
    }

    public void exampleCounterMethod() {
        //spring内置了两个service,counterService可以做简单的累加累减,来统计Pv,gaugeService可以存放简单的double值,数据都在内存中.
        this.counterService.increment("login.count");
        // reset each minute
    }

    public void exampleGaugeMethod() {
        this.gaugeService.submit("cache.hit", 80.0);
    }
}
