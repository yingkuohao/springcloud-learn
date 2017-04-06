package com.alicp.es.tool.service.parser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alicp.es.tool.service.parser.dao.mapper.LogAgentConfigMapper;
import com.alicp.es.tool.service.parser.dao.mapper.LogPathConfigMapper;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
import com.alicp.es.tool.service.parser.script.GroovyUtil;
import com.alicp.es.tool.service.util.LocalHostUtil;
import com.alicp.middleware.log.BizLog;
import com.google.common.collect.ImmutableSet;
import org.elasticsearch.threadpool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
    String inputFile = "/Users/chengjing/logs/alicplog/testOut.log";
    String outPutName = "regex_biz.log";

    String appName = "lottery";
    String bizName = "xingyunsaiche";
    @Autowired
    BizLog bizLog;

    private static Map<String, Integer> offSetMap = new HashMap<String, Integer>();

    private Map<String, BlockingQueue<String>> resourceMap = new HashMap<String, BlockingQueue<String>>();

    @PostConstruct
    private void initNew() {
        logScanStart();
        initParseConsumer();
    }


    //定时任务:每10秒读一次log,记录偏移量,下一次从偏移开始读
    private void logScanStart() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                log.info("startScanTask--" + LocalDateTime.now());
                initLogProducer();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }, 1, 10, TimeUnit.SECONDS);

    }

    private void initLogProducer() {

        try {
            String offKey = inputFile;
            if (offSetMap.get(offKey) == null) {
                offSetMap.put(offKey, 0);
            }
            List<String> lines = FileReadUtil.readByLines(inputFile, offSetMap);
            lines.forEach(line -> {
                //mapreduce:
                if (!StringUtils.isEmpty(line)) {

                    JSONObject jsonObject = JSONObject.parseObject(line);
                    log.info("---inputLinse=", jsonObject.toJSONString());
                    JSONObject fields = (JSONObject) jsonObject.get("fields");
                    String appName = fields.getString("app_name");
                    String ip = fields.getString("source_ip");
                    String logName = fields.getString("log_name");
                    String key = appName + ip + logName;         //应用+ip+日志名称绝对唯一的key ,这里要和在系统中配置的一样,否则会和线程对不上
                    BlockingQueue<String> resourceQueue = resourceMap.get(key);
                    if (resourceQueue == null) {
                        resourceQueue = new LinkedBlockingQueue<String>();
                        resourceMap.put(ip, resourceQueue);
                    }

                    try {
                        //得到队列,把当前行分发到目标队列.
                        resourceQueue.put(line);
                        resourceMap.put(key, resourceQueue);
                    } catch (InterruptedException e) {
//                        e.printStackTrace();
                        log.error("initLogProducer error,e={}",e);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("resourceMap.size={},keys={}", resourceMap.size(), resourceMap.keySet());
    }

    public void initParseConsumer() {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<LogAgentConfigDO> logAgentConfigDOList = agentConfigMapper.getAllLogAgentConfigDO();
        logAgentConfigDOList.forEach(logAgentConfigDO -> {
            List<LogPathConfigDO> logPathConfigDOList = logPathConfigMapper.getLogPathByAgentId(logAgentConfigDO.getId());
            String ips = logAgentConfigDO.getIps();
            String[] ipArr = ips.split(",");
            for (String curIp : ipArr) {
                //每个服务,每个ip一个线程;
                logPathConfigDOList.forEach(n -> {//yingkhtodo:desc:具体如何按文件读取,生成线程有待细化
                    String scriptPath = n.getScriptPath();
//                                startScanTask(n);      //不同的path,不同的定时任务
                    String inputPath = n.getInputPath();
                    String shortName = inputPath.substring(inputPath.lastIndexOf("/")+1, inputPath.length());
                    String key = logAgentConfigDO.getAppName() + curIp + shortName;     //注意之类要和filebeat重配置的一样,否则取不到

                    BlockingQueue<String> resourceQueue = resourceMap.get(key);
                    if (resourceQueue == null) {
                        log.error("can't find the queue,key={}", key);
                    } else {
                        threadPool.submit(new PareseCallable(resourceQueue, scriptPath, bizLog));
                    }

                });
            }

        });
    }


    //初始化定时任务,每10秒执行一次,每一个log一个线程池
//    @PostConstruct
    private void init() {
        String ip = LocalHostUtil.getHostAddress();
 /*       List<LogAgentConfigDO> logAgentConfigDOList = agentConfigMapper.getAllLogAgentConfigDO();
        logAgentConfigDOList.forEach(logAgentConfigDO -> {
            List<LogPathConfigDO> logPathConfigDOList = logPathConfigMapper.getLogPathByAgentId(logAgentConfigDO.getId());
            String ips = logAgentConfigDO.getIps();
            String[] ipArr = ips.split(",");
            for (String curIp : ipArr) {
                //每个服务,每个ip一个线程;
                logPathConfigDOList.forEach(n -> {//yingkhtodo:desc:具体如何按文件读取,生成线程有待细化
                         String scriptPath = n.getScriptPath();
                         startScanTask(n);      //不同的path,不同的定时任务
                     });
            }

        });*/

        //根据ip获取文件路径信息
        LogAgentConfigDO logAgentConfigDO1 = getLogAgentConfigByIp(appName, bizName, ip);

        List<LogPathConfigDO> logPathConfigDOs = logAgentConfigDO1.getLogPathConfigDOList();

        logPathConfigDOs.forEach(n -> {

            String scriptPath = n.getScriptPath();
            startScanTask(n);      //不同的path,不同的定时任务
        });

    }

    //启动扫描任务
    private void startScanTask(LogPathConfigDO logPathConfigDO) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                log.info("startScanTask--" + LocalDateTime.now());
                //定时扫描改变后缀
                String pattern = "";
                if (!StringUtils.isEmpty(logPathConfigDO.getPattern())) {            //"yyyyMMdd_HH"
                    //yingkhtodo:desc:如果有后缀,则需要解析下格式 ,后面的时间变了,文件的起始指针也要变,否则会有问题
                    pattern = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(logPathConfigDO.getPattern()));
                }
                String logPath = logPathConfigDO.getInputPath() + pattern;
                //拼装文件的起始偏移量key,如果文件变了,偏移量要设为0,
                String offKey = LocalHostUtil.getHostAddress() + logPath;
                if (offSetMap.get(offKey) == null) {
                    offSetMap.put(offKey, 0);
                }
                logParsing(logPath, logPathConfigDO.getScriptPath());
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
            lines = FileReadUtil.readByLines(logPath, offSetMap);            //yingkhtodo:desc:记住位点去读
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
        LogAgentConfigDO logAgentConfigDO1 = getLogAgentConfigByIp(appName, bizName, ip);

        List<LogPathConfigDO> logPathConfigDOs = logAgentConfigDO1.getLogPathConfigDOList();

        logPathConfigDOs.forEach(n -> {
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

    @Autowired
    LogAgentConfigMapper agentConfigMapper;
    @Autowired
    LogPathConfigMapper logPathConfigMapper;

    private LogAgentConfigDO getLogAgentConfigByIp(String appName, String bizName, String ip) {

        LogAgentConfigDO logAgentConfigDO = agentConfigMapper.getAgentByApp(bizName, appName);
        if (logAgentConfigDO == null) {
            log.error("can't find the config,plese check ,bizName={},appName{}", bizName, appName);
            return null;
        }
        List<LogPathConfigDO> storeDOResult = logPathConfigMapper.getLogPathByAgentId(logAgentConfigDO.getId());
        logAgentConfigDO.setLogPathConfigDOList(storeDOResult);
        return logAgentConfigDO;

/*        //yingkhtodo:desc:根据ip读数据库.要有一个录入功能
        //1.读文件,来自配置项
        LogAgentConfigDO logAgentConfigDO = new LogAgentConfigDO();
        logAgentConfigDO.setIps("127.0.0.1");
        logAgentConfigDO.setAppName(appName);
        logAgentConfigDO.setBizName(bizName);
        //yingkhtodo:desc:基础参数
        LogPathConfigDO logPathConfigDO = new LogPathConfigDO();
        logPathConfigDO.setInputPath("/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log");
        String scriptPath = "/Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy";

        logPathConfigDO.setScriptPath(scriptPath);
        List<LogPathConfigDO> list = new ArrayList<LogPathConfigDO>();
        list.add(logPathConfigDO);
        logAgentConfigDO.setLogPathConfigDOList(list);
        return logAgentConfigDO;*/
    }
}
