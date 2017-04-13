import com.alicp.es.test.ESTestBoot;
import com.alicp.es.tool.service.AlertHandler;
import com.alicp.es.tool.service.AlertService;
import com.alicp.es.tool.service.BaseQO;
import com.alicp.es.tool.service.ESService;
import com.alicp.es.tool.service.parser.dao.model.LogAgentConfigDO;
import com.alicp.middleware.log.BizLog;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 17/2/15
 * Time: 下午3:27
 * CopyRight: taobao
 * Descrption:
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ComponentScan("com.alicp.es.tool.service")
//@ContextConfiguration(locations = {"classpath*:/common-aop.xml"})
public class TestEsService extends ESTestBoot {

        @Autowired
    ESService esService;
    @Autowired
    AlertService alertService;
    @Autowired
    BizLog log;

    @Test
    public void testlog() {

        log.instance().log("key","111").log("value",123) .build();

    }
        @Test
    public void testLocalSearch() {
        //搜索数据
        String index = "blog";
        String type = "article";
        String id = "1";
        BaseQO baseQO = new BaseQO(index, type, id);
        Map<String, Object> result = esService.testSearch(baseQO);
        System.out.println("result=" + result.toString());
    }

    @Test
    public void testDailySearch() {
        //搜索数据
        String index = "metricbeat-2017.03.13";
        String type = "metricsets";
        String id = "AVpZUBsEnC74huSk8ATl";
        BaseQO baseQO = new BaseQO(index, type, id);
        Map<String, Object> result = esService.testSearch(baseQO);
        System.out.println("result=" + result.toString());
    }

    @Test
    public void testAlert() {
        //搜索数据
        String index = "metricbeat-2017.03.13";
        String type = "metricsets";
        String id = "AVrGKxRYy5ecult9a0qa";
        BaseQO baseQO = new BaseQO(index, type, id);
        SearchHits hits = esService.alertBoolQuery(baseQO);

        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
        }
    }

    @Autowired
    AlertHandler failCountAlert;

    @Test
    public void testAlertRule() {
//        alertService.collect();
        //搜索数据
        String index = "metricbeat-2017.03.13";
        String type = "metricsets";
//        String id = "AVrGKxRYy5ecult9a0qa";
        BaseQO baseQO = new BaseQO(index, type);
        baseQO.setSize(20);
        Map<String, Object> queryRules = new HashMap<>();
        queryRules.put("metricset.name","load");
        queryRules.put("metricset.module","system");
        esService.alertBoolQuery(baseQO, queryRules, failCountAlert);

    }


}
