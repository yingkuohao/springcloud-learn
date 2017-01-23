package com.alicp.logapp.log.jvm;

import com.alicp.logapp.log.jvm.util.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:24
 * CopyRight: taobao
 * Descrption:
 */

public class CollectManager {

    private static Log LOG = LogFactory.getLog(CollectManager.class);

    private static final ScheduledExecutorService minExecutorService = Executors.newScheduledThreadPool(1);


    public synchronized static void startCollect() {
        LOG.info("---startCollect scheduleAtFixedRate success!---");
        minExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                JvmCollector.doCollect();
             /*   ExceptionCollector.doCollect();
                SpringMethodCollector.doCollect();
                WebUrlCollector.doCollect();
                WebIPCollector.doCollect();
                WebProfileCollector.doCollect();

                // 如果不是用druid,可能会报warning
                try {
                    SqlCollector.doCollect();
                } catch (Exception e) {
                    LOG.warn(e.getMessage());
                }*/

                LOG.info("Collect:" + new Date());
            }

        }, 5L, 60L, TimeUnit.SECONDS);
    }

    public synchronized static void geMsg() {

        minExecutorService.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                List<Map<String, Object>> gcList = JvmCollector.getJvmGCModelList();
//                System.out.println("gclist=" + JsonUtils.toJsonString(gcList));
                LOG.info("GC info : " + new Date() + "\n");
                LOG.info(JsonUtils.toJsonString(gcList));
            }

        }, 5L, 60L, TimeUnit.SECONDS);
    }
}
