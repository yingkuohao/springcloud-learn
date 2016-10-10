import com.springcloud.tddl.dao.TerminalMapper;
import com.springcloud.tddl.dao.model.TerminalDO;
import com.springcloud.tddl.sharding.MultipleDataSource;
import com.springcloud.tddl.sharding.dao.DynamicDataSourceHolder;
import com.springcloud.tddl.sharding.dao.UserTicketIndex;
import com.springcloud.tddl.sharding.dao.UserTicketIndexDao;
import com.taobao.tddl.group.dbselector.AbstractDBSelector;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: chengjing
 * Date: 16/9/6
 * Time: 下午5:49
 * CopyRight: taobao
 * Descrption:
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:/spring-dao.xml"})
public class TestTerminalMapper extends AlicpAccountTestBoot {

    @Autowired
    TerminalMapper terminalMapper;

 /*   @Autowired
    private DataSource dataSource;
    @Autowired
    private DataSource primaryDataSource;
    @Autowired
    private DataSource secondaryDataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Autowired
    MultipleDataSource multipleDataSource;*/
    @Autowired
    private UserTicketIndexDao userTicketIndexDao;

    @Test
    public void getUserTicketIndexTest() {
        DynamicDataSourceHolder.setDataSource("dataSource1");
        testUser();
//        MultipleDataSource multipleDataSource=new MultipleDataSource();
//
//        Map<Object, Object> dataSourceMaps = new HashMap<Object, Object>();
//        dataSourceMaps.put("dataSource1",dataSource);
//        dataSourceMaps.put("dataSource2",primaryDataSource);
//        dataSourceMaps.put("dataSource3",secondaryDataSource);
//        multipleDataSource.setTargetDataSources(dataSourceMaps);
//
//        multipleDataSource.setDefaultTargetDataSource(dataSource);

//        multipleDataSource.setDataSourceKey("dataSource3");
        DynamicDataSourceHolder.setDataSource("dataSource3");
        UserTicketIndex userTicketIndex = userTicketIndexDao.getUserTicketIndex(123l);
        System.out.println(userTicketIndex.getLotterySn());
    }

    @Test
    public void testUser() {
        Long id = 1l;
        TerminalDO terminalDo = terminalMapper.getTerminalDO(id);
        System.out.println("terminalDo=" + terminalDo.toString());
    }

    @Test
    public void getAllTerminalDO() {
        List<TerminalDO> terminalDo = terminalMapper.getAllTerminalDO();
        System.out.println("terminalDo=" + terminalDo.toString());
    }

    @Test
    public void testInsert() {
        TerminalDO terminalDo = new TerminalDO();
        Integer storeId = 9527;
        terminalDo.setCenterStoreId(storeId);
        terminalDo.setStoreId(123l);
        terminalDo.setBusinessStatus(1);
        terminalDo.setCenterTerminalNo(132l);
        terminalDo.setManageStatus(1);
        terminalMapper.insert(terminalDo);


        TerminalDO terminalDoResult = terminalMapper.getTerminalByCenterStoreId(storeId);
        Assert.assertEquals(terminalDo.getCenterTerminalNo(), terminalDoResult.getCenterTerminalNo());
        System.out.println("terminalDo=" + terminalDo.toString());
    }


    @Test
    public void testInsertBAtch() {
        for (int i = 0; i < 8; i++) {


            TerminalDO terminalDo = new TerminalDO();
            Integer storeId = 1301000029;
            terminalDo.setCenterStoreId(1301000029);
            terminalDo.setStoreId(123l);
            terminalDo.setBusinessStatus(1);
            terminalDo.setCenterTerminalNo(1301000029103l + i);
            terminalDo.setManageStatus(1);
            terminalDo.setSn(860709021443915l + i + "");
            terminalMapper.insert(terminalDo);


//            TerminalDO terminalDoResult = terminalMapper.getTerminalByCenterStoreId(storeId);
//            Assert.assertEquals(terminalDo.getCenterTerminalNo(), terminalDoResult.getCenterTerminalNo());
            System.out.println("terminalDo=" + terminalDo.toString());
        }
    }

    @Test
    public void getStoreByCenterStoreId() {
        Integer storeId = 9527;
        TerminalDO terminalDoResult = terminalMapper.getTerminalByCenterStoreId(storeId);
        System.out.println("terminalDo=" + terminalDoResult.toString());
    }
}
