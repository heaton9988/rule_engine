package com.zzj.rule.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AviatorRuleServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AviatorRuleServerApplication.class, args);
    }


    @RequestMapping("/a")
    public String a() {
        return "aa s 1212gg";
    }
}