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
    public String leftField;
    public String rightField;
}
