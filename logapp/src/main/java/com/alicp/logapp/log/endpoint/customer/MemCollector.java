package com.alicp.logapp.log.endpoint.customer;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/24
 * Time: 下午3:02
 * CopyRight: taobao
 * Descrption:
 */

public class MemCollector {
    private int maxSize = 5;
    private List<MemStatus> status;

    public MemCollector(List<MemStatus> status) {
        this.status = status;
    }

    @Scheduled(cron = "0/5 * *  * * ? ")
    public void collect() {
        Runtime runtime = Runtime.getRuntime();
        Long maxMemory = runtime.maxMemory();
        Long totalMemory = runtime.totalMemory();
        Map<String, Object> memoryMap = new HashMap<String, Object>(2, 1);
        Date date = Calendar.getInstance().getTime();
        memoryMap.put("maxMemory", maxMemory);
        memoryMap.put("totalMemory", totalMemory);
        if (status.size() > maxSize) {
            status.remove(0);
            status.add(new MemStatus(date, memoryMap));
        } else {
            status.add(new MemStatus(date, memoryMap));
        }
    }
}
