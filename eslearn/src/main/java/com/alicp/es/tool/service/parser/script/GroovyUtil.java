package com.alicp.es.tool.service.parser.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/31
 * Time: 下午1:36
 * CopyRight: taobao
 * Descrption:
 */

public class GroovyUtil {
    private static Logger log = LoggerFactory.getLogger(GroovyUtil.class);

    public static Object parse(String path, String inputLine) {
        // TODO Auto-generated method stub
        Object result = null;
        try {
            ClassLoader parent = ClassLoader.getSystemClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parent);
            Class groovyClass = null;
            groovyClass = loader.parseClass(new File(path));

            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            //yingkhtodo:desc:最好制定一个接口,约定好方法和参数
            result = groovyObject.invokeMethod("parse", inputLine);

            System.out.println("res=" + result);
        } catch (Exception e) {
            log.error("groovy parse error,e={}", e);
        }
        return result;
    }

    public static void main(String[] args) {
        String path = "/Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy";
        String inputLine = "test";
        Object object = GroovyUtil.parse(path, inputLine);
        System.out.println("object=" + object);
    }
}
