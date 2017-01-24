package com.alicp.logapp.log.endpoint.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:04
 * CopyRight: taobao
 * Descrption:
 */
@Configuration
public class EndPointAutoConfig {
    private List<MemStatus> status = new ArrayList<MemStatus>();

    @Bean
    public MyEndPoint myEndPoint() {
        return new MyEndPoint(status);
    }

    @Bean
    public MemCollector memCollector() {
        return new MemCollector(status);
    }
}
