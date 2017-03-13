package com.alicp.es.tool.service;

import org.elasticsearch.search.SearchHit;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/3/13
 * Time: 下午5:14
 * CopyRight: taobao
 * Descrption:
 */

public interface AlertHandler {

    void doAlert();

    boolean rule(Map<String, Object> resources);

    public default boolean execute(SearchHit searchHitFields) {
        Map<String, Object> resources = searchHitFields.getSource();
        if (resources != null && !resources.isEmpty()) {
            //yingkhtodo:desc:
            if (rule(resources)) {
                doAlert();
            }
        }
        return true;
    }
}
