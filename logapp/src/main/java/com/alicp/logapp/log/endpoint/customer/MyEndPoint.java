package com.alicp.logapp.log.endpoint.customer;

import org.springframework.boot.actuate.endpoint.Endpoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:03
 * CopyRight: taobao
 * Descrption:
 */

public class MyEndPoint implements Endpoint {
     private List<MemStatus> status;
     public MyEndPoint(List<MemStatus> status) {
         this.status = status;
     }
     public String getId() {
         return "my";
     }
     public boolean isEnabled() {
         return true;
     }
     public boolean isSensitive() {
         return false;
     }
     public Object invoke() {
         if (status == null || status.isEmpty()) {
             return "hello world";
         }
         Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
         for (MemStatus memStatus : status) {
             for (Map.Entry<String, Object> entry : memStatus.getStatus().entrySet()) {
                 List<Map<String, Object>> collectList = result.get(entry.getKey());
                 if (collectList == null) {
                     collectList = new LinkedList<Map<String, Object>>();
                     result.put(entry.getKey(), collectList);
                 }
                 Map<String, Object> soloCollect = new HashMap<String, Object>();
                 soloCollect.put("date", memStatus.getDate());
                 soloCollect.put(entry.getKey(), entry.getValue());
                 collectList.add(soloCollect);
             }
         }
         return result;
     }
 }
