package com.alicp.logapp.log.jvm.plugin;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/1/23
 * Time: 上午9:54
 * CopyRight: taobao
 * Descrption:
 */

public interface JVMMemoryMBean {
    // Heap
    long getHeapMemoryCommitted();

    long getHeapMemoryInit();

    long getHeapMemoryMax();

    long getHeapMemoryUsed();

    // NonHeap
    long getNonHeapMemoryCommitted();

    long getNonHeapMemoryInit();

    long getNonHeapMemoryMax();

    long getNonHeapMemoryUsed();

    // PermGen
    long getPermGenCommitted();

    long getPermGenInit();

    long getPermGenMax();

    long getPermGenUsed();

    // OldGen
    long getOldGenCommitted();

    long getOldGenInit();

    long getOldGenMax();

    long getOldGenUsed();

    // EdenSpace
    long getEdenSpaceCommitted();

    long getEdenSpaceInit();

    long getEdenSpaceMax();

    long getEdenSpaceUsed();

    // Survivor
    long getSurvivorCommitted();

    long getSurvivorInit();

    long getSurvivorMax();

    long getSurvivorUsed();

}

