package com.xwolf.os.controller;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.FuzzyMatchingEngineSvc;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/channel/{channelName}")
    public String channel1(@ApiParam(defaultValue = "fusion_fill",required=true) @PathVariable String channelName,
                          @RequestBody Map fieldMap) {
        return fuzzyMatchingEngineSvc.match(channelName, fieldMap);

    }

    @GetMapping("/view/{channelName}/{primaryKey}")
    public String view(@PathVariable String channelName,
                          @PathVariable String primaryKey) {
        return fuzzyMatchingEngineSvc.view(channelName, primaryKey);

    }


}
