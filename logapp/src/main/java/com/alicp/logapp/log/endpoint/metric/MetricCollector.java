package com.alicp.logapp.log.endpoint.metric;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午4:10
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class MetricCollector {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private MetricsEndpoint delegate;

    //每5秒调用一次metrics,记录到log
    @Scheduled(cron = "0/5 * *  * * ? ")
    public void collect() {
        //1.捕捉本地的jvm情况
//        Map map = delegate.invoke();
//        logger.info(JSONObject.toJSONString(map));
        //2. 捕捉远程的jvm情况
        getRemoteMetrics();
    }


    public void getRemoteMetrics() {
        try {
            //采用Spring自带的http请求
            URI uri = new URI("http://alicpaccount.daily.taobao.net:7002/metrics");
            SimpleClientHttpRequestFactory schr = new SimpleClientHttpRequestFactory();
            ClientHttpRequest chr = schr.createRequest(uri, HttpMethod.GET);
            //chr.getBody().write(param.getBytes());//body中设置请求参数
            ClientHttpResponse res = chr.execute();
            InputStream is = res.getBody(); //获得返回数据,注意这里是个流
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String str = "";
            while ((str = br.readLine()) != null) {
                System.out.println(str);//获得页面内容或返回内容
            }
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
