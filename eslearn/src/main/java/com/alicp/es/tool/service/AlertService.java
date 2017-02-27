package com.alicp.es.tool.service;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/21
 * Time: 下午3:36
 * CopyRight: taobao
 * Descrption:
 */

@Component
public class AlertService {

    @Autowired
    ESService esService;

    //每5秒调用一次metrics,记录到log
    @Scheduled(cron = "0/15 * *  * * ? ")
    public void collect() {            //yingkhtodo:desc:时间做成配置,自身的监控
        //1.捕捉本地的jvm情况

//        J_App_Name:"game-inceptor-web"  AND  J_IP : $ip AND J_Log_Type:"bizInfo"  AND bizType:$bizType AND  B_Operate_Type:"V_TotalTime" AND gameId:$gameId

        String index = "metricbeat-2017.02.20";
        String type = "metricsets";
        BaseQO baseQO = new BaseQO(index, type);
        //报警条件
        Map<String, Object> queryRules = new HashMap<>();
        queryRules.put("J_App_Name", "game-inceptor-web");
        queryRules.put("J_Log_Type", "bizInfo");
        queryRules.put("bizType", "inceptor");
        SearchHits hits = esService.alertBoolQuery(baseQO, queryRules);

        for (int i = 0; i < hits.getHits().length; i++) {
            SearchHit searchHitFields = hits.getHits()[i];
            //报警逻辑
            if (matchRule(searchHitFields)) {
                doAction();
            }

            System.out.println(searchHitFields.getSourceAsString());
        }
    }

    private void doAction() {
        //send Msg
    }

    private boolean matchRule(SearchHit searchHitFields) {
        Map<String, Object> resources = searchHitFields.getSource();
        if (resources != null && !resources.isEmpty()) {
            //yingkhtodo:desc:               y
        }
        return true;
    }


}
