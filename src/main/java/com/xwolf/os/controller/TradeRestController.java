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
    public Trade viewBychannel(@RequestParam String channelName, @RequestParam String primaryKey) {
        return tradeSvc.findTradesByTradeTypeAndKey(channelName, primaryKey);
    }

    @GetMapping("/viewAll")
    public List<Trade> viewAll() {
        return tradeSvc.getAllTrades();
    }

    @PostMapping("/deleteBychannelNameAndKey")
    public Trade delete(@RequestParam String channelName, @RequestParam String primaryKey) {
        return tradeSvc.delete(channelName, primaryKey);
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

    @PostMapping("/upload/{channelName}")
    public String upload(@PathVariable String channelName,
                         @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        if (file == null) {
            return "please select proper file!";
        }
        try {
            List<Map<String, String>> mapList = CsvUtil.readObjectsFromMultiFile(file);
            tradeSvc.save(channelName, mapList);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }


}
