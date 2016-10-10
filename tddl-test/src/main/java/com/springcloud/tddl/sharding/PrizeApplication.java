package com.springcloud.tddl.sharding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


//@Configuration
public class PrizeApplication {
	
	@Autowired
    private TddlSettings tddlSettings;
	
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(PrizeApplication.class);
        app.run(args);
    }

    @Bean(name="primaryDataSource")
    @Primary
    public DataSource primaryDataSource() {       //yingkhtodo:desc:数据源启动有问题,与prize库单表有冲突,暂时不生成bean
        if(tddlSettings == null || tddlSettings.getPrimary() == null){
    		return null;
    	}
    	return TddlDataSourceBuilder.create().app(tddlSettings.getPrimary()).build();
    }

    @Bean(name="secondaryDataSource")
    public DataSource secondaryDataSource() {
    	if(tddlSettings == null || tddlSettings.getSecondary() == null){
    		return null;
    	}
    	return TddlDataSourceBuilder.create().app(tddlSettings.getSecondary()).build();
    }

    
}
