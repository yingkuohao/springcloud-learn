package com.alicp.logapp.log.service;

import com.alicp.logapp.log.jvm.JvmCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/23
 * Time: 下午1:41
 * CopyRight: taobao
 * Descrption:
 */
@Component
public class SheculerService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Provider provider;
    @Autowired
    JVMTestService jvmTestService;

    @PostConstruct
    public void initTask() {
        startJvmTask();
    }

    private static final ScheduledExecutorService minExecutorService = Executors.newScheduledThreadPool(2);

    public void startJvmTask() {
        logger.info("---startJvmTask scheduleAtFixedRate success!---");
        minExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                try {
                    jvmTestService.testMinorGC();
                    jvmTestService.testOldEmpty();
                    jvmTestService.testPretenureSize();
                    jvmTestService.testFullGC();
                    logger.info("execute once!");
                }catch (Exception e) {
                    logger.error("e,",e);
                    System.gc();
                }
            }

        }, 5L, 30L, TimeUnit.SECONDS);
    }


}
