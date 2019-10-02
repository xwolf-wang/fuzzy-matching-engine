package com.xwolf.os.matching;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:35 AM
 **/
@Component
public class MandatoryMatchingLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<Trade> process(Trade trade, List<Trade> candidateList) {
        return null;
    }
}
