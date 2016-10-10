package com.springcloud.tddl.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/10/9
 * Time: 下午4:07
 * CopyRight: taobao
 * Descrption:多数据源配置,   参考:http://www.cnblogs.com/davidwang456/p/4318303.html
 * 其实就是配置一个datasourcemap,利用spring的扩展点来动态选择,不过TDDL有问题,因为TDDl在选择数据源之前就做了一层拦截,会校验有没有对应的
 * rule,校验通过后才会找数据源,打开连接.
 */

//@Component
public class MultipleDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSource primaryDataSource;
    @Autowired
    private DataSource secondaryDataSource;

    @PostConstruct
    public void init() {
        Map<Object, Object> dataSourceMaps = new HashMap<Object, Object>();
        dataSourceMaps.put("dataSource1", dataSource);
        dataSourceMaps.put("dataSource2", primaryDataSource);
        dataSourceMaps.put("dataSource3", secondaryDataSource);
        this.setTargetDataSources(dataSourceMaps);
        this.setDefaultTargetDataSource(dataSource);
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }

}
