package com.alicp.es.tool.service;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/13
 * Time: 下午5:22
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class FailCountAlert implements AlertHandler {
    @Override
    public void doAlert() {
        System.out.println("alert");
    }

    @Override
    public boolean rule(Map<String, Object> resources) {
        System.out.println("map:" + resources.toString());
        Object systemMap = resources.get("system");

        Object loadMap = ((Map)systemMap).get("load");
        Object load1 = ((Map)loadMap).get("1");
        if (load1 != null) {
            Double f = (Double) load1;
            if (f.compareTo(1.0d) > 0) {
                System.out.println("---waring!!!!--");
                return true;
            }
        }
        return false;
    }
}
