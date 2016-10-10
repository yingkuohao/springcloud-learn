package com.springcloud.tddl.sharding;

import com.taobao.tddl.client.jdbc.TDataSource;

import java.util.HashMap;
import java.util.Map;

public class TddlDataSourceBuilder {

	private TDataSource target;
    private Map<String, Object> properties = new HashMap<String, Object>();
    

    private TddlDataSourceBuilder() {
        target = new TDataSource();
    }

    public static TddlDataSourceBuilder create() {
        return new TddlDataSourceBuilder();
    }

    public TDataSource build() {
        if (properties.containsKey("app")) {
            target.setAppName((String) properties.get("app"));     
        }
        if (properties.containsKey("sharding")) {
            target.setSharding((Boolean) properties.get("sharding"));
        }
        if (properties.containsKey("ruleFilePath")) {
            target.setAppRuleFile((String) properties.get("ruleFilePath"));
        }
        if(target.getAppName() != null){
        	target.init();
        }
        return target;
    }

    public TddlDataSourceBuilder app(String appName) {
        this.properties.put("app", appName);
        return this;
    }

    public TddlDataSourceBuilder sharding(boolean sharding) {
        this.properties.put("sharding", sharding);
        return this;
    }

    public TddlDataSourceBuilder ruleFilePath(String ruleFilePath) {
        this.properties.put("ruleFilePath", ruleFilePath);
        return this;
    }
}
