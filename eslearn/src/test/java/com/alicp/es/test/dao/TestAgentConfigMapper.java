package com.alicp.es.test.dao;

import com.alicp.es.test.ESTestBoot;
import com.alicp.es.tool.service.parser.dao.mapper.LogAgentConfigMapper;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:49
 * CopyRight: taobao
 * Descrption:
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/alicpprize-mybatis.xml"})
public class TestAgentConfigMapper extends ESTestBoot {

    @Autowired
    LogAgentConfigMapper agentConfigMapper;
    //    @Autowired
//    StoreDAO storeDAO;
    String appName = "lottery";
    String bizName = "xingyunsaiche";

    @Test
    public void testUser() {
        Integer id = 1;
        LogAgentConfigDO storeDO = agentConfigMapper.getLogAgentConfigDO(id);
        LogAgentConfigDO storeDO2 = agentConfigMapper.getAgentByApp(bizName,appName);
        System.out.println("storeDO2="+storeDO2);
    }

    @Test
    public void testInsert() {

        LogAgentConfigDO storeDO = new LogAgentConfigDO();
        LogAgentConfigDO logAgentConfigDO = new LogAgentConfigDO();
        logAgentConfigDO.setIps("127.0.0.1");
        logAgentConfigDO.setAppName(appName);
        logAgentConfigDO.setBizName(bizName);
        //yingkhtodo:desc:基础参数
        LogPathConfigDO logPathConfigDO = new LogPathConfigDO();
        logPathConfigDO.setInputPath("/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log");
        String scriptPath = "/Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy";

        logPathConfigDO.setScriptPath(scriptPath);
        List<LogPathConfigDO> list = new ArrayList<LogPathConfigDO>();
        list.add(logPathConfigDO);
        logAgentConfigDO.setLogPathConfigDOList(list);
        int i = agentConfigMapper.insert(logAgentConfigDO);


        LogAgentConfigDO storeDOResult = agentConfigMapper.getLogAgentConfigDO(i);
        System.out.println("storeDOResult=" + storeDOResult);
    }


}
