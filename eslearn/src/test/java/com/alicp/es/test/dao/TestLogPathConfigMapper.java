package com.alicp.es.test.dao;

import com.alicp.es.test.ESTestBoot;
import com.alicp.es.tool.service.parser.dao.mapper.LogAgentConfigMapper;
import com.alicp.es.tool.service.parser.dao.mapper.LogPathConfigMapper;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.es.tool.service.parser.dao.model.LogPathConfigDO;
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
public class TestLogPathConfigMapper extends ESTestBoot {

    @Autowired
    LogPathConfigMapper logPathConfigMapper;
//    @Autowired
//    StoreDAO storeDAO;


    @Test
    public void testInsert() {
        //yingkhtodo:desc:基础参数
        LogPathConfigDO logPathConfigDO = new LogPathConfigDO();
        int agentId = 1;
        logPathConfigDO.setAgentId(agentId);
//        logPathConfigDO.setPattern("yyyyMMdd_HH");
        logPathConfigDO.setInputPath("/Users/chengjing/Downloads/HNLRG_sample_logs/app01/test.log");
        String scriptPath = "/Users/chengjing/Project/selfLearn/master/springcloud-learn/eslearn/src/main/java/com/alicp/es/tool/service/parser/script/TestGroovy.groovy";

        logPathConfigDO.setScriptPath(scriptPath);
        int i = logPathConfigMapper.insert(logPathConfigDO);


        LogPathConfigDO pathDo = logPathConfigMapper.getLogPathById(i);
        List<LogPathConfigDO> storeDOResult = logPathConfigMapper.getLogPathByAgentId(agentId);
        System.out.println("storeDOResult=" + storeDOResult);
    }


}
