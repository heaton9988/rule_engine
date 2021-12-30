package com.zzj.rule.engine.server;

import com.zzj.rule.engine.server.entity.Rule;
import com.zzj.rule.engine.server.mapper.RuleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = RuleEngineServerApplication.class)
@RunWith(SpringRunner.class)
public class RuleTest {
    @Resource
    RuleMapper ruleMapper;

    @Test
    public void a() {
        List<Rule> rules = ruleMapper.selectList(null);
        System.out.println(123);
    }
}