package com.xwolf.os.domain;

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
    private String leftTradeType;
    private String leftPrimaryKey;
    private String rightTradeType;
    private String rightPrimaryKey;
    private Integer cutoffRatio;
    private List<MatchField> matchFields = new ArrayList<>();

}
