package com.xwolf.os.matching;

import static me.xdrop.fuzzywuzzy.FuzzySearch.*;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:06 PM
 **/
public class AverageFuzzyMatchingStratergy {
    public static AverageFuzzyMatchingResult match(String input, String other) {
        return new AverageFuzzyMatchingResult.Builder()
                .setSimpleRatio(ratio(input, other))
                .setPartialRatio(partialRatio(input, other))
                .setTokenSortPartialRatio(tokenSortPartialRatio(input, other))
                .setTokenSortRatio(tokenSortRatio(input, other))
                .setTokenSetRatio(tokenSetRatio(input, other))
                .setTokenSetPartialRatio(tokenSetPartialRatio(input, other))
                .setTokenWeightedRatio(weightedRatio(input, other))
                .build();
    }
}
