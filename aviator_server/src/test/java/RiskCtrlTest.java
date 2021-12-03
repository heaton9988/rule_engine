import com.zzj.rule.server.AviatorRuleServerApplication;
import com.zzj.rule.server.entity.RiskCtrlRuleHeader;
import com.zzj.rule.server.mapper.RiskCtrlRuleHeaderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = AviatorRuleServerApplication.class)
@RunWith(SpringRunner.class)
public class RiskCtrlTest {
    @Resource
    RiskCtrlRuleHeaderMapper riskCtrlRuleHeaderMapper;

    @Test
    public void a() {
        List<RiskCtrlRuleHeader> riskCtrlRuleHeaders = riskCtrlRuleHeaderMapper.selectList(null);
        System.out.println(123);
    }

    @Test
    public void testSingleForm() {
//        long tenantId = 0L;
//        Result<DetectAndSubmitResp> loan = riskCtrlController.detectAndSubmit(7021444684066521382L, "LOAN");
//        System.out.println();
    }
}