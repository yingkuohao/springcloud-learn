package com.alicp.logapp.log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/18
 * Time: 上午9:28
 * CopyRight: taobao
 * Descrption:
 */

public class LogTask implements  Runnable {

    private static final Logger logger = LoggerFactory.getLogger(LogTask.class);

    @Override
    public void run() {

       while(true) {
                 logger.info("logapp ");
       }
    }
}
