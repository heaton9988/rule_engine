package com.zzj.rule.engine.server;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class MyBatisPlusGeneratorTest {
    @Test
    public void generate() {
        System.out.println(123);
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
//        String projectPath = System.getProperty("user.dir");
        String projectPath = "/Users/xingchuan/generatedCode/expense/src/main/java/generate";
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor("heaton9988");
        globalConfig.setOpen(true);
        globalConfig.setServiceName("%sService");
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/rule_engine?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
        dataSourceConfig.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("123456");
        dataSourceConfig.setTypeConvert(new CustomTypeConvert());

        autoGenerator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
//        pc.setModuleName("tax");
        pc.setParent("com.zzj.rule.engine.server");
        pc.setEntity("entity");
        pc.setMapper("mapper");

        autoGenerator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/com/zzj/rule/server"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自定义继承的Entity类全称，带包名
//        strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        // 生成 @RestController控制器
//        strategy.setRestControllerStyle(true);
        // 自定义继承的Controller类全称，带包名
//        strategy.setSuperControllerClass("com.ea.cam.core.base.controller.BaseController");
        // 需要包含的表名，允许正则表达式（与exclude二选一配置）
//        strategy.setInclude("risk_ctrl_rule_header", "risk_ctrl_rule_detail", "risk_ctrl_trigger_assign_rule");
        strategy.setInclude("t_rule");
        // 自定义基础的Entity类，公共字段
//        strategy.setSuperEntityColumns("id");
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 表前缀
        strategy.setTablePrefix("t_");
        autoGenerator.setStrategy(strategy);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

    /**
     * 自定义数据类转换
     */
    static class CustomTypeConvert extends MySqlTypeConvert {
        @Override
        public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
            String t = fieldType.toLowerCase();
            if (t.contains("tinyint(1)")) {
                return DbColumnType.INTEGER;
            }
            return super.processTypeConvert(globalConfig, fieldType);
        }
    }
}