package com.zzj.rule.engine.server.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 风险控制规则-头表 前端控制器
 * </p>
 *
 * @author heaton9988
 * @since 2021-12-02
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @RequestMapping("list")
    public String list(){
        return "abc";
    }
}
