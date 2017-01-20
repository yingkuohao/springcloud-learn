package com.alicp.logapp.log.conroller;

import com.alibaba.fastjson.JSONObject;
import com.alicp.logapp.log.common.LogUtil;
import com.alicp.logapp.log.service.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/18
 * Time: 上午9:38
 * CopyRight: taobao
 * Descrption:
 */
@Controller
@RequestMapping("/log")
public class TestRpcController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Provider provider;

    private boolean isLoop = true;

    @RequestMapping("/stop")
    @ResponseBody
    public String stop() {
        isLoop = false;
        return "stop";
    }

    @RequestMapping("/start")
    @ResponseBody
    public String start() {
        isLoop = true;
        return "start";
    }

    @RequestMapping("/testlog")
    public String testlo() {
        testConsumer();
        return "success";
    }


    @RequestMapping("/testbiz")
    public String testbiz() {
        Map map = LogUtil.getBaseLog();
        map.put("bizId", 123);
        map.put("bizInfo", "下单成功");
        logger.info(JSONObject.toJSONString(map));
        return "testbiz";
    }

    public void testConsumer() {
        Method[] methods = Provider.class.getMethods();

        while (isLoop) {
            Random random = new Random();
            int i = random.nextInt(4);
            String methodName = "method" + i;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Method m : methods) {
                if (methodName.equals(m.getName())) {
                    try {
                        logger.info(LogUtil.logPrefix().append(this.getClass().getSimpleName()).append("|").append("testConsumer").append("|").append("begin").append("|").append(System.nanoTime()).toString());
                        m.invoke(provider);
                        logger.info(LogUtil.logPrefix().append(this.getClass().getSimpleName()).append("|").append("testConsumer").append("|").append("end").append("|").append(System.nanoTime()).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
