package com.xwolf.os.controller;

import com.xwolf.os.db.RuleEntity;
import com.xwolf.os.db.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ming
 * @Description:
 * @create 2019-09-12 11:06 PM
 **/
@RestController
public class FuzzyRestController {

    @Autowired
    RuleRepository repository;

    @GetMapping("/printmessage")
    public String printMessage(){
        return "hello";
    }

    @PostMapping("insertRule")
    public void insertRule(@RequestBody RuleEntity ruleEntity){
        System.out.println(ruleEntity);
        repository.save(ruleEntity);
    }
}
