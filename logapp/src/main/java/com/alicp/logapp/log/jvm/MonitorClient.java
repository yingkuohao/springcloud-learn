package com.alicp.logapp.log.jvm;

import com.alicp.logapp.log.jvm.util.JMXUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午4:50
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class MonitorClient {
    protected static Log logger = LogFactory.getLog(MonitorClient.class);

    @PostConstruct
    public void initJvmMonitor() {
        try {
            //注册MBean
            JMXUtils.regMbean();
            //开启定时任务
            CollectManager.startCollect();
//            CollectManager.geMsg();
//        List<Map<String, Object>> gcList = JvmCollector.getJvmGCModelList();
//        System.out.println("gclist=" + JsonUtils.toJsonString(gcList));
//            Thread.sleep(Long.MAX_VALUE);
            logger.info("---initJvmMonitor success!---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
