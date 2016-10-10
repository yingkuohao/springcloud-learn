package com.springcloud.tddl.sharding.dao;

import com.springcloud.tddl.sharding.MultipleDataSource;
import org.springframework.util.Assert;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/10/10
 * Time: 上午8:55
 * CopyRight: taobao
 * Descrption:
 */

public class DynamicDataSourceHolder {
    //线程本地环境
      private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
      //设置数据源
      public static void setDataSource(String customerType) {
          dataSources.set(customerType);
      }
      //获取数据源
      public static String getDataSource() {
          return (String) dataSources.get();
      }
      //清除数据源
      public static void clearDataSource() {
          dataSources.remove();
      }
}
