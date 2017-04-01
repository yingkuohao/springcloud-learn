package com.alicp.es.tool.service.parser;

import com.alicp.es.tool.service.util.LocalHostUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
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

public class FileReadUtil {
    private static Logger log = LoggerFactory.getLogger(FileReadUtil.class);
    public static String KEY_SEND_REQUEST = "OXi::openAPI::sendRequest:";

    public static void main(String[] args) {

        // 指定读取的行号
        int lineNumber = 12;
        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log";
        // 读取文件
        File sourceFile = new
                File(path);
        // 读取指定的行
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取文件的内容的总行数
        String s = "02/06-16:59:59.578 [08] <12:0091:SITE_40506360> OXi::openAPI::sendRequest:             <code>001</code>";
        Pattern wp = Pattern.compile("(?is)(?<=code)[^<]+(?=code)");
        Matcher m = wp.matcher(s);
        while (m.find()) {
            String group = m.group();
            System.out.println("group=" + group);
        }

        FileReadUtil fileReader = new FileReadUtil();

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

    public static BlockingQueue<HashMap<String, StringBuilder>> readFile(LogTemplate logTemplate) throws IOException {
        // Java 8例子
//        String path = "/Users/chengjing/Downloads/HNLRG_sample_logs/app01/middleware.log.20170206_16";
        String fileName = logTemplate.getLogPath() + logTemplate.getLogtName(); //yingkhtodo:desc:确认下pattern怎么匹配
        List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        Map<String, StringBuilder> pointerMap = new HashMap<String, StringBuilder>();
        BlockingQueue<HashMap<String, StringBuilder>> logQueue = new LinkedBlockingDeque<>();

        Map<String, LogFeature> logFields = logTemplate.getLogFeatureMap();
        String fieldPointer = null;
        for (String line : lines) {
            for (Map.Entry<String, LogFeature> logFeatureEntry : logFields.entrySet()) {
                LogFeature logFeature = logFeatureEntry.getValue();
                String logPrefix = logFeature.getFeatureId();
                if (line.contains(logPrefix)) {
                    if (fieldPointer != null && !fieldPointer.equals(logPrefix)) {
                        //如果指针变化,说明日志指向了新的业务,应该把现有解析好的日志刷到队列.  并清空上一个field的key
                        try {
                            logQueue.put(new HashMap<String, StringBuilder>(pointerMap));  //yingkhtodo:desc:map的拷贝,找一个高效api
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
                    lineSb.append(line);
                    pointerMap.put(fieldPointer, lineSb);
                    break;//不考虑一行涵盖两种前缀的情况.匹配一种处理完,直接处理下一行
                } else {
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


        return logQueue;
    }

    public static void writeFile(String path, List<String> line) throws IOException {
        Files.write(Paths.get(path), line,
                Charset.defaultCharset(), StandardOpenOption.APPEND);
    }

    public static void writeFile(String path, String line) throws IOException {

        Path path1 = Paths.get(path);
        if (!path1.toFile().exists()) {
            Files.createFile(path1);
        }
        Files.write(path1, line.getBytes(),
                StandardOpenOption.APPEND);
    }


    private static long chars = 0;//chars指的是字符数

    /**
     * @param fileName
     * @param offSetMap
     * @return
     */
    public static List<String> readByLines(String fileName, Map<String, Integer> offSetMap) {
        //大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
        List<String> list = new ArrayList<String>();
        //一次session的访问记录集合

        //java提供的一个可以分页读取文件的类,此类的实例支持对随机访问文件的读取和写入
        RandomAccessFile rf = null;

        String tempString = null;
        try {

            //初始化RandomAccessFile，参数一个为文件路径，一个为权限设置，这点与Linux类似，r为读，w为写
            rf = new RandomAccessFile(fileName, "rw");

            //设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作
            String offKey= LocalHostUtil.getHostAddress()+fileName;
            int lastSize = offSetMap.get(offKey);
            if(lastSize==0) {
                chars =0; //如果文件的起始位置变成了0,偏移量也要跟着变
            }
            rf.seek(chars);
            //获取文件的行数
            int fileSize = getFileLineCount(fileName);
            //yingkhtodo:desc:这里要控制一下,客户端的文件名会变,的话这个起始指针得变
            for (int i = lastSize; i < fileSize; i++) {//从上一次读取结束时的文件行数到本次读取文件时的总行数中间的这个差数就是循环次数
                //一行一行读取
                tempString = rf.readLine();
                //文件中文乱码处理
                tempString = tempString.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                tempString = tempString.replaceAll("\\+", "%2B");
                tempString = java.net.URLDecoder.decode(tempString, "GB2312");
                list.add(tempString);

            }
            log.info("---readbyLine lastSize={},totalSize={},fileSize={}", lastSize, fileSize,list.size());
            chars = rf.getFilePointer();
            //返回此文件中的当前偏移量。
            offSetMap.put(offKey, fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rf != null) {
                try {
                    rf.close();
                } catch (IOException e1) {
                }
            }
        }
        log.info("---list={}",list.toString());
        return list;
    }



    /**
     * 获取总行数
     * @param filename
     * @return
     */
    public static int getFileLineCount(String filename) {
        int cnt = 0;
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(filename));
            @SuppressWarnings("unused")
            String lineRead = "";
            while ((lineRead = reader.readLine()) != null) {
            }
            cnt = reader.getLineNumber();
        } catch (Exception ex) {
            cnt = -1;
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }
}
