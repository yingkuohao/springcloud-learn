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
        jsonObject.put("test1", "中文");
        jsonObject.put("test2", i++);
        String s = jsonObject.toJSONString();
        String multi= "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:     <response>\n" +
                "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:         <returnStatus>\n" +
                "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <code>001</code>\n" +
                "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <message>success</message>\n" ;

        String javaMulti="Exception in thread \"main\" java.lang.NullPointerException\n" +
                "        at com.example.myproject.Book.getTitle(Book.java:16)\n" +
                "[        at com.example.myproject.Author.getBookTitles(Author.java:25)\n" +
                "[        at com.example.myproject.Bootstrap.main(Bootstrap.java:14)\n"+
                "I am the end";
        try {
            FileReadUtil.writeFile(path, s + "\n"+javaMulti);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWriteTest() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {

        /*        String path = "/Users/chengjing/logs/alicplog/test2.log";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("test1", "aa");
                jsonObject.put("test2", i++);
                String s = jsonObject.toJSONString();
                FileReadUtil.writeFile(path, s+"\n");*/
                testWrite();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
