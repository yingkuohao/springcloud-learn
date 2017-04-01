package com.alicp.es.tool.service.parser;

import com.alibaba.fastjson.JSONObject;
import com.alicp.es.tool.service.parser.config.LogAgentConfig;
import com.alicp.es.tool.service.parser.config.LogPathConfig;
import com.alicp.es.tool.service.parser.script.GroovyUtil;
import com.alicp.es.tool.service.util.LocalHostUtil;
import com.alicp.middleware.log.BizLog;
import com.google.common.collect.ImmutableSet;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/31
 * Time: 上午10:53
 * CopyRight: taobao
 * Descrption:
 */
@Component
@EnableScheduling
public class LogAgentService {
    private static Logger log = LoggerFactory.getLogger(LogAgentService.class);
    String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/";
    String outPutName = "regex_biz.log";

    String appName = "lottery";
    String bizName = "xingyunsaiche";
    @Autowired
    BizLog bizLog;


    //初始化定时任务,每10秒执行一次,每一个log一个线程池
    @PostConstruct
    private void init() {
        String ip = LocalHostUtil.getHostAddress();
        //根据ip获取文件路径信息
        LogAgentConfig logAgentConfig1 = getLogAgentConfigByIp(appName, bizName, ip);

        List<LogPathConfig> logPathConfigs = logAgentConfig1.getLogPathConfigList();

        logPathConfigs.forEach(n -> {
            String pattern = "";
            if (!StringUtils.isEmpty(n.getPattern())) {
                //yingkhtodo:desc:如果有后缀,则需要解析下格式
            }
            String logPath = n.getInputPath() + pattern;
            String scriptPath = n.getScriptPath();
            startScanTask(logPath, scriptPath);      //不同的path,不同的定时任务
        });

    }

    //启动扫描任务
    private void startScanTask(String logPath, String scriptPath) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                log.info("startScanTask--" + LocalDateTime.now());
                logParsing(logPath, scriptPath);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }, 1, 10, TimeUnit.SECONDS);
    }

    int i = 0;



    //日志解析
    private void logParsing(String logPath, String scriptPath) {
        List<String> lines = null;
        try {
            //yingkhtodo:desc:需要记住文件位点.
            //2.解析文件,输入:line,script
            lines = FileReadUtil.readByLines(logPath);            //yingkhtodo:desc:记住位点去读
//            lines = Files.readAllLines(Paths.get(logPath), StandardCharsets.UTF_8);
            //yingkhtodo:desc:暂时注释
            lines.forEach(line -> {
                //按行读取文件,调用script解析
                Object formatResult = GroovyUtil.parse(scriptPath, line);
                //yingkhtodo:desc:转换为json
               log.info("formatResult=" + formatResult);
                JSONObject jsonObject = (JSONObject) formatResult;
                bizLog.instance().log(jsonObject).build();
                //输出到目标文件
            });

        } catch (Exception e) {
            log.error("readlog error,path={},e={}", logPath, e);
        }
    }


    public void readLog() {

        //2.解析文件,输入:line,script
        String ip = LocalHostUtil.getHostAddress();
        //根据ip获取文件路径信息
        LogAgentConfig logAgentConfig1 = getLogAgentConfigByIp(appName, bizName, ip);

        List<LogPathConfig> logPathConfigs = logAgentConfig1.getLogPathConfigList();

        logPathConfigs.forEach(n -> {
            //遍历日志文件,每一个日志开一个线程
            new Thread(() -> {
                String pattern = "";

                if (!StringUtils.isEmpty(n.getPattern())) {
                    //yingkhtodo:desc:如果有后缀,则需要解析下格式
                }
                String logPath = n.getInputPath() + pattern;
                List<String> lines = null;
                try {
                    lines = Files.readAllLines(Paths.get(logPath), StandardCharsets.UTF_8);
                    lines.forEach(line -> {
                        //按行读取文件,调用script解析
                        String scriptPath = n.getScriptPath();
                        Object formatResult = GroovyUtil.parse(scriptPath, line);
                        //yingkhtodo:desc:转换为json
                        System.out.println("formatResult=" + formatResult);
                        JSONObject jsonObject = (JSONObject) formatResult;
                        bizLog.instance().log(jsonObject).build();
                        //输出到目标文件
                    });


                } catch (IOException e) {
                    log.error("readlog error,path={},e={}", logPath, e);
                }

            }).start();

        });

        //3. 输出到文件
    }


    private LogAgentConfig getLogAgentConfigByIp(String appName, String bizName, String ip) {
        //yingkhtodo:desc:根据ip读数据库.要有一个录入功能
        //1.读文件,来自配置项
        LogAgentConfig logAgentConfig = new LogAgentConfig();
        logAgentConfig.setIps(ImmutableSet.of("127.0.0.1"));
        logAgentConfig.setAppName(appName);
        logAgentConfig.setBizName(bizName);
        //yingkhtodo:desc:基础参数
        LogPathConfig logPathConfig = new LogPathConfig();
        logPathConfig.setInputPath("/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log");
        logPathConfig.setOutPutName("regex_biz.log");
        String scriptPath = "/Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy";

        logPathConfig.setScriptPath(scriptPath);
        List<LogPathConfig> list = new ArrayList<LogPathConfig>();
        list.add(logPathConfig);
        logAgentConfig.setLogPathConfigList(list);
        return logAgentConfig;
    }
}
