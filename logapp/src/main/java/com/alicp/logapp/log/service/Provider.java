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
        execute("method1");
    }


    public void method2() {
        String method2 = "method2";
        execute(method2);
    }

    private void execute(String method2) {
        long begin = System.nanoTime();
        String logprefix = LogUtil.logPrefix().append(this.getClass().getName()).append("|").append(method2).append("|").toString();
        logger.info(logprefix + "execute"+"|");
        try {
            int millis = random.nextInt(100);
            //小于50打印正常log,否则打印errorlog,用于统计正常,错误数量
            if (millis > 50) {
                logger.error(logprefix + "error"+"|");
            }
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error(LogUtil.logPrefix().append(this.getClass().getName()).append("|").append(method2).append("|").append("timeout").toString());
            e.printStackTrace();
        }
        logger.info(logprefix + "totalTime"+"|" + (System.nanoTime() - begin)+"|");
    }

    public void method3() {
        execute("method3");
    }

}
