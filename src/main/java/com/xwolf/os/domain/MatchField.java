package com.xwolf.os.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 10:11 AM
 **/
@Data
@AllArgsConstructor
public class MatchField {

    public static final String MANDATORY = "mandatory";
    public static final String MANDATORY_AGGREGATE = "mandatory_aggregate";
    public static final String FUZZY = "fuzzy";
    public static final String AVG = "avg";

    public String leftField;
    public String rightField;
    public String matchingType;

}
