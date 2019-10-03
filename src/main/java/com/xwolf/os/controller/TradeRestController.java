package com.xwolf.os.controller;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.TradeSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 8:30 PM
 **/
@RestController
@RequestMapping("/trade")
public class TradeRestController {
    @Autowired
    TradeSvc tradeSvc;

    @GetMapping("/view")
    public List<Trade> viewTrades(@RequestParam String chanelName, @RequestParam String primaryKey){
        return tradeSvc.findTradesByTradeTypeAndKey(chanelName,primaryKey);
    }

    @PostMapping("/deleteByChanelNameAndKey")
    public Trade delete(@RequestParam String chanelName, @RequestParam String primaryKey){
        return tradeSvc.delete(chanelName,primaryKey);
    }

    @PostMapping("/deleteByUUID")
    public Trade deleteByUUID(@RequestParam String uuid){
        return tradeSvc.delete(uuid);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(){
        tradeSvc.deletAll();
        return "success";
    }

}
