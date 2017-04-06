package com.alicp.es.tool.service.parser;

import com.alibaba.fastjson.JSONObject;
import com.alicp.es.tool.service.parser.script.GroovyUtil;
import com.alicp.middleware.log.BizLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/4/6
 * Time: 下午3:36
 * CopyRight: taobao
 * Descrption:
 */

public class PareseCallable implements Callable {
    private static Logger log = LoggerFactory.getLogger(PareseCallable.class);
    private BlockingQueue<String> blockingQueue;
    private String scriptPath;
    private BizLog bizLog;


    public PareseCallable(BlockingQueue<String> blockingQueue, String scriptPath, BizLog bizLog) {
        this.blockingQueue = blockingQueue;
        this.scriptPath = scriptPath;
        this.bizLog = bizLog;
    }

    @Override
    public Object call() throws Exception {
        while (true) {
            String line = blockingQueue.take();
            //按行读取文件,调用script解析
            Object formatResult = GroovyUtil.parse(scriptPath, line);
            //yingkhtodo:desc:转换为json
            log.info("-----formatResult=" + formatResult);
            JSONObject jsonObject = (JSONObject) formatResult;
            bizLog.instance().log(jsonObject).build();
        }
    }
}
