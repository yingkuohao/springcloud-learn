package com.alicp.logapp.log.conroller;

import com.alicp.logapp.log.service.JVMTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/18
 * Time: 上午9:38
 * CopyRight: taobao
 * Descrption:
 */
@Controller
@RequestMapping("/jvm")
public class TestJVMController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JVMTestService jvmTestService;

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

    //http://localhost:7001/jvm/testJvm
    @RequestMapping("/testJvm")
    @ResponseBody
    public String testlo() {
        jvmTestService.testMinorGC();
        return "success";

    }

    // http://localhost:7001/jvm/testOldAlloc
    @RequestMapping("/testOldAlloc")
    @ResponseBody
    public String testPretenureSize() {
        jvmTestService.testPretenureSize();
        return "success";
    }

    // http://localhost:7001/jvm/testFullGC1
    @RequestMapping("/testFullGC1")
    @ResponseBody
    public String testFullGC() {
        jvmTestService.testFullGC();
        return "success";
    }

    // http://localhost:7001/jvm/testFullGC2
    @RequestMapping("/testFullGC2")
    @ResponseBody
    public String testOldEmpty() {
        //测试堆区使用情况,大对象直接进入年老代,占用堆内存
        jvmTestService.testOldEmpty();
        return "success";
    }

}