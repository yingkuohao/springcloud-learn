package com.alicp.es.tool.service.parser;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/28
 * Time: 下午2:05
 * CopyRight: taobao
 * Descrption:
 */

public class FileReader {

    public static String KEY_SEND_REQUEST = "OXi::openAPI::sendRequest:";

    public static void main(String[] args) {


        String s = "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <code>001</code>";
        Pattern wp = Pattern.compile("(?is)(?<=code)[^<]+(?=code)");
        Matcher m = wp.matcher(s);
        while (m.find()) {
            String group = m.group();
            System.out.println("group=" + group);
        }

        FileReader fileReader = new FileReader();

        try {
            readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void readFile() throws IOException {
        // Java 8例子
//        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/middleware.log.20170206_16";
        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log";
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        Map<String, StringBuilder> lineBuilderMap = new HashMap<String, StringBuilder>();
        for (String line : lines) {
            if (line.contains(KEY_SEND_REQUEST)) {
                StringBuilder lineSb = lineBuilderMap.get(KEY_SEND_REQUEST);
                if (lineSb == null) {
                    lineSb = new StringBuilder();
                    lineBuilderMap.put(KEY_SEND_REQUEST, lineSb);
                }
                lineSb.append(line).append("\n");
            } else {

                sb.append(line);
            }
            System.out.println(line);
        }
        String fromFile = sb.toString();
        String request = lineBuilderMap.get(KEY_SEND_REQUEST).toString();
        System.out.println(request);

        Pattern wp = Pattern.compile(Tag_code, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = wp.matcher(request);
        String result = m.replaceAll("");
        System.out.println("result:" + result);
    }

    private final static String Tag_code = "<code>\\s+</code>";
    private final static String regxpForImgTag = "<\\s*code\\s+([^>]*)\\s*>";
    private final static String tag_code = "<\\\\s*code\\\\s.*?href\\\\s*=\\\\s*[^>]*\\\\s*>\\\\s*(.*?)\\\\s*<\\\\s*/\\\\s*code\\\\s*>";

    public static BlockingQueue<HashMap> readFile(LogTemplate logTemplate) throws IOException {
        // Java 8例子
//        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/middleware.log.20170206_16";
        String fileName = logTemplate.getLogPath() + logTemplate.getLogtName(); //yingkhtodo:desc:确认下pattern怎么匹配
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        Map<String, StringBuilder> pointerMap = new HashMap<String, StringBuilder>();
        BlockingQueue<HashMap> logQueue = new LinkedBlockingDeque<>();

        Map<String, LogFeature> logFields = logTemplate.getLogFeatureMap();
        String fieldPointer = null;
        for (String line : lines) {
//            System.out.println(line);
            for (Map.Entry<String, LogFeature> logFeatureEntry : logFields.entrySet()) {
//                String featureId = logFeatureEntry.getKey();
                LogFeature logFeature = logFeatureEntry.getValue();
                String logPrefix = logFeature.getFeatureId();
                if (line.contains(logPrefix)) {
                    if (fieldPointer != null && !fieldPointer.equals(logPrefix)) {
                        //如果指针变化,说明日志指向了新的业务,应该把现有解析好的日志刷到队列.  并清空上一个field的key
                        try {
                            logQueue.put(new HashMap(pointerMap));  //yingkhtodo:desc:map的拷贝,找一个高效api
                            pointerMap.remove(fieldPointer);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    fieldPointer = logPrefix;// 指针指向当前字段的前缀
                    StringBuilder lineSb = pointerMap.get(fieldPointer);
                    if (lineSb == null) {
                        lineSb = new StringBuilder();
                        pointerMap.put(fieldPointer, lineSb);
                    }
                    lineSb.append(line).append("\n");
                    pointerMap.put(fieldPointer, lineSb);
                    break;//不考虑一行涵盖两种前缀的情况.匹配一种处理完,直接处理下一行
                } else {
//                    sb.append(line);
                }

            }
        }
        //处理最后一行
        if (!CollectionUtils.isEmpty(pointerMap)) {
            try {
                logQueue.put(new HashMap(pointerMap));  //yingkhtodo:desc:map的拷贝,找一个高效api
                pointerMap.clear();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        logQueue.forEach(System.out::println);
        pointerMap = null;      //置空

        return logQueue;
    }

}
