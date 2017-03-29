package com.alicp.es.tool.service.parser;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/29
 * Time: 上午11:49
 * CopyRight: taobao
 * Descrption:
 */

public class LogClient {
    public static void main(String[] args) {
        //1.拼装需求字段
        LogTemplate logTemplate = new LogTemplate();
        logTemplate.setAppName("test");
        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/";
        logTemplate.setLogPath(path);
        logTemplate.setLogPattern("");
        logTemplate.setLogtName("test.log");

        LogFeature logFeature = new LogFeature();
        logFeature.setBizType("order");
        logFeature.setFeatureId("<12:0091:SITE_40506360> OXi::openAPI::sendRequest:");
       /* logField.setName("code");
        logField.setType("String");*/
        TreeMap<String, FieldPattern> patternMap = new TreeMap<String, FieldPattern>();
        FieldPattern fieldPattern = new FieldPattern();
        fieldPattern.setFieldName("code");
        fieldPattern.setType(String.class);
        patternMap.put("code", fieldPattern);
        logFeature.setPatternMap(patternMap);
        logFeature.setPatternType(2);

        LogFeature logFeature2 = new LogFeature();
        logFeature2.setBizType("order");
        logFeature2.setFeatureId("SLAC::session::set_data");
        TreeMap<String, FieldPattern> patternMap2 = new TreeMap<String, FieldPattern>();
        FieldPattern fieldPattern2 = new FieldPattern();
        fieldPattern2.setFieldName("TOKEN");
        fieldPattern2.setType(String.class);
        fieldPattern2.setPattern("TERM_LOGIN_TOKEN\\s.*");
        patternMap2.put("TOKEN", fieldPattern2);
        logFeature2.setPatternMap(patternMap2);
        logFeature2.setPatternType(1);

        //构造logFeatureMap
        Map<String, LogFeature> logFeatureHashMap = new HashMap<String, LogFeature>();
        logFeatureHashMap.put(logFeature.getFeatureId(), logFeature);
        logFeatureHashMap.put(logFeature2.getFeatureId(), logFeature2);
        logTemplate.setLogFeatureMap(logFeatureHashMap);


        FileReader fileReader = new FileReader();
        try {
            //2.解析文件,只保留目标需求字段中的log片段
            BlockingQueue<HashMap> logQueue = FileReader.readFile(logTemplate);
            //3. 遍历queue,正则解析每一个key
            logQueue.forEach(n ->
            {
                n.forEach((k, v) -> {

                    System.out.println("k=" + k +",v=" + v);

                });

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
