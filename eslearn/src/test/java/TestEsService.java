import com.alicp.es.test.ESTestBoot;
import com.alicp.es.tool.service.BaseQO;
import com.alicp.es.tool.service.ESService;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        String index = "metricbeat-2017.02.20";
        String type = "metricsets";
        String id = "AVpZUBsEnC74huSk8ATl";
        BaseQO baseQO = new BaseQO(index, type, id);
        Map<String, Object> result = esService.testSearch(baseQO);
        System.out.println("result=" + result.toString());
    }

    @Test
    public void testAlert() {
        //搜索数据
        String index = "metricbeat-2017.02.20";
        String type = "metricsets";
        String id = "AVpZUBsEnC74huSk8ATl";
        BaseQO baseQO = new BaseQO(index, type, id);
        SearchHits hits = esService.alertBoolQuery(baseQO);

        for (int i = 0; i < hits.getHits().length; i++) {
            System.out.println(hits.getHits()[i].getSourceAsString());
        }
    }
}
