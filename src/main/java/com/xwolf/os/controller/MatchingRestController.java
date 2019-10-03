package com.xwolf.os.controller;

import com.xwolf.os.service.FuzzyMatchingEngineSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String chanel1(@RequestBody Map fieldMap) {
        return fuzzyMatchingEngineSvc.match("fusion_fill", fieldMap);

    }

    @PostMapping("chanel_fusion_execution")
    public String chanel2(@RequestBody Map fieldMap) {
        return fuzzyMatchingEngineSvc.match("fusion_execution", fieldMap);
    }
}
