package com.alicp.logapp.log.service;

import com.alicp.logapp.log.common.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/18
 * Time: 上午9:38
 * CopyRight: taobao
 * Descrption:
 */

@Service
public class Provider {

    private static final Logger logger = LoggerFactory.getLogger(Provider.class);

    Random random = new Random();

    public void method1() {
        logger.info(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method1").append("|").append("execute").toString());
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            logger.error(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method1").append("|").append("timeout").toString());
            e.printStackTrace();
        }
    }


    public void method2() {
        logger.info(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method2").append("|").append("execute").toString());
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            logger.error(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method1").append("|").append("timeout").toString());
            e.printStackTrace();
        }
    }

    public void method3() {
        logger.info(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method3").append("|").append("execute").toString());
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            logger.error(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append("method1").append("|").append("timeout").toString());
            e.printStackTrace();
        }
    }
}
