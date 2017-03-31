package com.alicp.es.test;

import com.alicp.es.tool.service.parser.LogAgentService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void testLocalSearch() {

        logAgentService.readLog();
        try {
            Thread.sleep(1000*30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
