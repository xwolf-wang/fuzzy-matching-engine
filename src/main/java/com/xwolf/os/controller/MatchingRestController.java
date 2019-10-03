package com.xwolf.os.controller;

import com.xwolf.os.db.RuleEntity;
import com.xwolf.os.db.RuleRepository;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.FuzzyMatchingEngineSvc;
import com.xwolf.os.service.TradeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-09-12 11:06 PM
 **/
@RestController
public class MatchingRestController {

    @Autowired
    FuzzyMatchingEngineSvc fuzzyMatchingEngineSvc;

    @PostMapping("chanel_fusion_fill")
    public String chanel1(@RequestBody Map fieldMap){
        return fuzzyMatchingEngineSvc.match("fusion_fill",fieldMap);

    }

    @PostMapping("chanel_fusion_execution")
    public String chanel2(@RequestBody Map fieldMap){
        return fuzzyMatchingEngineSvc.match("fusion_execution",fieldMap);
    }
}
