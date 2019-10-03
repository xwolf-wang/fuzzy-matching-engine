package com.xwolf.os.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 10:11 AM
 **/
@Data
public class MatchField {
    public String leftField;
    public String rightField;
    public String matchingType;

    public MatchField(String leftField, String rightField, String matchingType) {
        this.leftField = leftField;
        this.rightField = rightField;
        this.matchingType = matchingType;
    }

    public MatchField()
    {

    }
}
