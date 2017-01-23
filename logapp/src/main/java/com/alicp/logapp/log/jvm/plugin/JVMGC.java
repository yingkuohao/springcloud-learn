package com.alicp.logapp.log.jvm.plugin;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ListIterator;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/22
 * Time: 下午3:28
 * CopyRight: taobao
 * Descrption:
 */

public class JVMGC  implements JVMGCMBean {

    private static JVMGC instance = new JVMGC();

    public static JVMGC getInstance() {
        return instance;
    }

    private GarbageCollectorMXBean fullGC;
    private GarbageCollectorMXBean youngGC;

    private long                   lastYoungGCCollectionCount = -1;
    private long                   lastYoungGCCollectionTime  = -1;
    private long                   lastFullGCCollectionCount  = -1;
    private long                   lastFullGCCollectionTime   = -1;

    // private GCProvider gcProvider = null;

    private JVMGC(){
        // gcProvider = GCProvider.createGCProvider();
        for (ListIterator<GarbageCollectorMXBean> iter = ManagementFactory.getGarbageCollectorMXBeans().listIterator(); iter.hasNext();) {
            GarbageCollectorMXBean item = iter.next();
            if ("ConcurrentMarkSweep".equals(item.getName()) //
                || "MarkSweepCompact".equals(item.getName()) //
                || "PS MarkSweep".equals(item.getName()) //
                || "G1 Old Generation".equals(item.getName()) //
                || "Garbage collection optimized for short pausetimes Old Collector".equals(item.getName()) //
                || "Garbage collection optimized for throughput Old Collector".equals(item.getName()) //
                || "Garbage collection optimized for deterministic pausetimes Old Collector".equals(item.getName()) //
            ) {
                fullGC = item;
            } else if ("ParNew".equals(item.getName()) //
                       || "Copy".equals(item.getName()) //
                       || "PS Scavenge".equals(item.getName()) //
                       || "G1 Young Generation".equals(item.getName()) //
                       || "Garbage collection optimized for short pausetimes Young Collector".equals(item.getName()) //
                       || "Garbage collection optimized for throughput Young Collector".equals(item.getName()) //
                       || "Garbage collection optimized for deterministic pausetimes Young Collector".equals(item.getName()) //
            ) {
                youngGC = item;
            }
        }
    }

    @Override
    public long getYoungGCCollectionCount() {
        if (youngGC == null) {
            return 0;
        }
        return youngGC.getCollectionCount();
    }

    @Override
    public long getYoungGCCollectionTime() {
        if (youngGC == null) {
            return 0;
        }
        return youngGC.getCollectionTime();
    }

    @Override
    public long getFullGCCollectionCount() {
        if (fullGC == null) {
            return 0;
        }
        return fullGC.getCollectionCount();
    }

    @Override
    public long getFullGCCollectionTime() {
        if (fullGC == null) {
            return 0;
        }
        return fullGC.getCollectionTime();
    }

    @Override
    public long getSpanYoungGCCollectionCount() {
        long current = getYoungGCCollectionCount();
        if (lastYoungGCCollectionCount == -1) {
            lastYoungGCCollectionCount = current;
            return 0;
        } else {
            long reslut = current - lastYoungGCCollectionCount;
            lastYoungGCCollectionCount = current;
            return reslut;
        }
    }

    @Override
    public long getSpanYoungGCCollectionTime() {
        long current = getYoungGCCollectionTime();
        if (lastYoungGCCollectionTime == -1) {
            lastYoungGCCollectionTime = current;
            return 0;
        } else {
            long reslut = current - lastYoungGCCollectionTime;
            lastYoungGCCollectionTime = current;
            return reslut;
        }
    }

    @Override
    public long getSpanFullGCCollectionCount() {
        long current = getFullGCCollectionCount();
        if (lastFullGCCollectionCount == -1) {
            lastFullGCCollectionCount = current;
            return 0;
        } else {
            long reslut = current - lastFullGCCollectionCount;
            lastFullGCCollectionCount = current;
            return reslut;
        }
    }

    @Override
    public long getSpanFullGCCollectionTime() {
        long current = getFullGCCollectionTime();
        if (lastFullGCCollectionTime == -1) {
            lastFullGCCollectionTime = current;
            return 0;
        } else {
            long reslut = current - lastFullGCCollectionTime;
            lastFullGCCollectionTime = current;
            return reslut;
        }
    }

}

