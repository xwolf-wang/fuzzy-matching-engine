package com.xwolf.os.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 10:03 AM
 **/
@Data
public class MatchRule {
    private String ruleName;
    private String leftTradeType;
    private String leftTradeKey;
    private String rightTradeType;
    private String rightTradeKey;
    private Integer cutoffRatio;
    private Double avgPrecision;
    private List<MatchField> matchFields = new ArrayList<>();

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
