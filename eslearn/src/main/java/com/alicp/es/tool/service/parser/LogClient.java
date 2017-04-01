package com.alicp.es.tool.service.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by IntelliJ IDEA.
 * User:         chengjing
 * Date:         17/3/29
 * Time:         上午11:        49
 * CopyRight:         taobao
 * Descrption:
 */

public class LogClient {
    public static void main(String[] args) {
        //1.拼装需求字段
        LogTemplate logTemplate = new LogTemplate();
        logTemplate.setAppName("test");
        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/";
        String outPutName = "regex_biz.log";
        logTemplate.setLogPath(path);
        logTemplate.setLogPattern("");
        logTemplate.setLogtName("test.log");

        LogFeature logFeature = new LogFeature();
        logFeature.setBizType("order");
        logFeature.setFeatureId("<12:0091:SITE_40506360> OXi::openAPI::sendRequest");
       /* logField.setName("code");
        logField.setType("String");*/
        TreeMap<String, FieldPattern> patternMap = new TreeMap<String, FieldPattern>();
        FieldPattern fieldPattern = new FieldPattern();
        fieldPattern.setFieldName("code");
        fieldPattern.setType(String.class);
        patternMap.put("code", fieldPattern);
        patternMap.put("message", new FieldPattern("message", Boolean.class));
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


        FileReadUtil fileReader = new FileReadUtil();
        try {
            //2.解析文件,只保留目标需求字段中的log片段
            BlockingQueue<HashMap<String, StringBuilder>> logQueue = FileReadUtil.readFile(logTemplate);
            //3. 遍历queue,正则解析每一个key
            logQueue.forEach(n ->
            {
                n.forEach((k, v) -> {
                    //yingkhtodo:        desc:        解析
                    System.out.println("----k=" + k + ",----v=" + v);
                    LogFeature logFeatureCur = logTemplate.getLogFeatureMap().get(k);

                    Integer patternType = logFeatureCur.getPatternType();
                    JSONObject jsonObject = new JSONObject();
                    //正则解析value
                    if (patternType == 1) {   //如果是1,直接按正则解析
                        TreeMap<String, FieldPattern> patternMap1 = logFeatureCur.getPatternMap();
                        FieldPattern fieldPatternCur = patternMap1.firstEntry().getValue();
                        String simpleResult = RegexUtil.parseByPattern(v.toString(), fieldPatternCur.getPattern());
                        jsonObject.put(fieldPatternCur.getFieldName(), simpleResult);
                    } else if (patternType == 2) {  //如果是2,则
                        Set<String> keySet = logFeatureCur.getPatternMap().keySet();
                        Map<String, String> pareResult = RegexUtil.parseText(v.toString(), keySet);
                        jsonObject = JSONObject.parseObject(JSON.toJSONString(pareResult));
                    }
                    System.out.println("----result=" + jsonObject);
                    try {               //写入文件
                        FileReadUtil.writeFile(path + outPutName, jsonObject.toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

