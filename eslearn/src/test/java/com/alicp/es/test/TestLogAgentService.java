package com.alicp.es.test;

import com.alibaba.fastjson.JSONObject;
import com.alicp.es.tool.service.parser.FileReadUtil;
import com.alicp.es.tool.service.parser.LogAgentService;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
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
//        logAgentService.readLog();
        try {
//            String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test2.log";
            String path = "/Users/chengjing/logs/alicplog/test2.log";
            startWriteTest();
//            testWrite();
            Thread.sleep(1000 * 30)
            ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int i = 0;

    @Test
    public void testWrite() {
        String path = "/Users/chengjing/logs/alicplog/test2.log";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test1", "aa");
        String s = jsonObject.toJSONString();
        try {
            FileReadUtil.writeFile(path, s + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWriteTest() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {

                String path = "/Users/chengjing/logs/alicplog/test2.log";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("test1", "aa");
                jsonObject.put("test2", i++);
                String s = jsonObject.toJSONString();
                FileReadUtil.writeFile(path, s+"\n");
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
