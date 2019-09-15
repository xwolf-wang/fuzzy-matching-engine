package com.xwolf.os.domain;

import lombok.Data;

/**
 * @author ming
 * @Description:
 * @create 2019-09-14 10:16 AM
 **/
@Data
public class MatchResponse {
    public Trade requestTrade;
    public Trade matchedTrade;
    public int ratio;
}
