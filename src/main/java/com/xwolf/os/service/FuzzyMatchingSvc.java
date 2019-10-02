package com.xwolf.os.service;

import com.xwolf.os.db.RuleRepository;
import com.xwolf.os.db.TradeEntity;
import com.xwolf.os.db.TradeRepository;
import com.xwolf.os.domain.*;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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






    public MatchResponse match(Trade trade){

        //get the matching rule based on trade type

        //populate primary key and save
        tradeSvc.save(trade);

        MatchResponse response = matchingSvc.process(trade);

        return response;
    }


}
