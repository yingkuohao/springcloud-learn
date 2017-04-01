package com.alicp.es.test;

import com.alicp.es.tool.service.parser.FileReadUtil;
import com.alicp.es.tool.service.parser.LogAgentService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/31
 * Time: 下午2:52
 * CopyRight: taobao
 * Descrption:
 */

public class TestLogAgentService extends ESTestBoot {
    @Autowired
    LogAgentService logAgentService;
    private static Logger log = LoggerFactory.getLogger(TestLogAgentService.class);

    @Test
    public void testLocalSearch() {

        logAgentService.readLog();
        try {
            String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log";
            startWriteTest(path);
            Thread.sleep(1000 * 30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int i = 0;

    //test:写入
    private void startWriteTest(String logPath) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                String s = String.valueOf(i++) + "\n";
                FileReadUtil.writeFile(logPath, s);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
