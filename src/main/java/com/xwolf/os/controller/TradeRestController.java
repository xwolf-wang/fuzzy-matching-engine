package com.xwolf.os.controller;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.TradeSvc;
import com.xwolf.os.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public Trade viewByChanel(@RequestParam String chanelName, @RequestParam String primaryKey) {
        return tradeSvc.findTradesByTradeTypeAndKey(chanelName, primaryKey);
    }

    @GetMapping("/viewAll")
    public List<Trade> viewAll() {
        return tradeSvc.getAllTrades();
    }

    @PostMapping("/deleteByChanelNameAndKey")
    public Trade delete(@RequestParam String chanelName, @RequestParam String primaryKey) {
        return tradeSvc.delete(chanelName, primaryKey);
    }

    @PostMapping("/deleteByUUID")
    public Trade deleteByUUID(@RequestParam String uuid) {
        return tradeSvc.delete(uuid);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        tradeSvc.deletAll();
        return "success";
    }

    @PostMapping("/upload/{chanelName}")
    public String upload(@PathVariable String chanelName,
                         @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        if (file == null) {
            return "please select proper file!";
        }
        try {
            List<Map<String, String>> mapList = CsvUtil.readObjectsFromMultiFile(file);
            tradeSvc.save(chanelName, mapList);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }


}
