package com.alicp.es.test;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ComponentScan("com.alicp.es")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ESTestBoot.class)
@SpringBootApplication
@WebIntegrationTest("server.port:7004")
//@EnableRedisHttpSession
public class ESTestBoot {
	
}
