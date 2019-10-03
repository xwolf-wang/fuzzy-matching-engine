package com.xwolf.os.controller;

import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.service.RuleConfigSvc;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 10:17 AM
 **/
@RestController
@RequestMapping("/rule")
public class RuleConfigRestController {

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    @PostMapping("/save")
    public String saveRule(@RequestBody MatchRule rule){
        ruleConfigSvc.saveRule(rule);
        return "success";
    }

    @PostMapping("/findAll")
    public List<MatchRule> findAllRules(){
        return ruleConfigSvc.findAll();
    }


}
