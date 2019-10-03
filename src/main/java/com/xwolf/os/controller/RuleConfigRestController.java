package com.xwolf.os.controller;

import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.service.RuleConfigSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 10:17 AM
 **/
@RestController
public class RuleConfigRestController {

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    @PostMapping("/saveRule")
    public String saveRule(@RequestBody MatchRule rule){
        ruleConfigSvc.saveRule(rule);
        return "success";
    }

    @PostMapping("/findAllRules")
    public List<MatchRule> findAllRules(){

        return ruleConfigSvc.findAll();
    }
}
