package com.xwolf.os.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 8:38 AM
 **/
@Data
public class FuzzyTrade {
    private Trade trade;
    private ExtractedResult fuzzyMatchInfo;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
