package com.alicp.logapp.log.endpoint.metric;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午2:45
 * CopyRight: taobao
 * Descrption:    http://localhost:7002/health
 */

@Component
public class MyHealth implements HealthIndicator {
    @Override
    public Health health() {
        return new Health.Builder()
                .withDetail("tair", "timeout") // some logic check tair
                .withDetail("tfs", "ok") // some logic check tfs
                .status("500")
                .down()
                .build();
    }
}