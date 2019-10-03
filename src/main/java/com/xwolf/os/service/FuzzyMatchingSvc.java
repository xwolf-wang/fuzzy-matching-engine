package com.xwolf.os.service;

import com.xwolf.os.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:14 PM
 **/
@Service
public class FuzzyMatchingSvc {


    @Autowired
    private TradeSvc tradeSvc;

    @Autowired
    private MatchingSvc matchingSvc;






    public List<List<FuzzyTrade>> match(Trade trade){

        //get the matching rule based on trade type

        //populate primary key and save
        tradeSvc.save(trade);

        List<List<FuzzyTrade>> response = matchingSvc.process(trade);

        return response;
    }


}
