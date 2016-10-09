import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan("com.springcloud.tddl")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AlicpAccountTestBoot.class)
@SpringBootApplication
@EnableTransactionManagement
@WebIntegrationTest("server.port:7002")
//@ImportResource("classpath*:/alicpprize-mybatis.xml")
public class AlicpAccountTestBoot {
	
}
