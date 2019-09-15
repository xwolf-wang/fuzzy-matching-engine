package com.xwolf.os.matching;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:00 PM
 **/
public class AverageFuzzyMatchingResult {

    private static final int MATCH_RATIO_THRESHOLD = 65;

    private final int TOTAL_NUM_SEARCH_RESULT_RATIOS = 7;
    private final int simpleRatio, partialRatio,
            tokenSortPartialRatio, tokenSortRatio,
            tokenSetRatio, tokenSetPartialRatio, tokenWeightedRatio;

    public boolean isSimilarEnough(){
        return average() >= MATCH_RATIO_THRESHOLD;
    }

    public double average() {
        return total() / TOTAL_NUM_SEARCH_RESULT_RATIOS;
    }

    private int total() {
        return simpleRatio + partialRatio + tokenSortPartialRatio + tokenSortRatio + tokenSetRatio + tokenSetPartialRatio + tokenWeightedRatio;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("simpleRatio", simpleRatio)
                .append("partialRatio", partialRatio)
                .append("tokenSortPartialRatio", tokenSortPartialRatio)
                .append("tokenSortRatio", tokenSortRatio)
                .append("tokenSetRatio", tokenSetRatio)
                .append("tokenSetPartialRatio", tokenSetPartialRatio)
                .append("tokenWeightedRatio", tokenWeightedRatio)
                .toString();
    }

    public static class Builder {

        private int simpleRatio, partialRatio,
                tokenSortPartialRatio, tokenSortRatio,
                tokenSetRatio, tokenSetPartialRatio, tokenWeightedRatio;

        public Builder setSimpleRatio(final int simpleRatio) {
            this.simpleRatio = simpleRatio;
            return this;
        }

        public Builder setPartialRatio(final int partialRatio) {
            this.partialRatio = partialRatio;
            return this;
        }

        public Builder setTokenSortPartialRatio(final int tokenSortPartialRatio) {
            this.tokenSortPartialRatio = tokenSortPartialRatio;
            return this;
        }

        public Builder setTokenSortRatio(final int tokenSortRatio) {
            this.tokenSortRatio = tokenSortRatio;
            return this;
        }

        public Builder setTokenSetRatio(final int tokenSetRatio) {
            this.tokenSetRatio = tokenSetRatio;
            return this;
        }

        public Builder setTokenSetPartialRatio(final int tokenSetPartialRatio) {
            this.tokenSetPartialRatio = tokenSetPartialRatio;
            return this;
        }

        public Builder setTokenWeightedRatio(final int tokenWeightedRatio) {
            this.tokenWeightedRatio = tokenWeightedRatio;
            return this;
        }

        public AverageFuzzyMatchingResult build() {
            return new AverageFuzzyMatchingResult(this);
        }
    }

    private AverageFuzzyMatchingResult() {
        this(new Builder());
    }

    private AverageFuzzyMatchingResult(final Builder builder) {
        this.simpleRatio = builder.simpleRatio;
        this.partialRatio = builder.partialRatio;
        this.tokenSortPartialRatio = builder.tokenSortPartialRatio;
        this.tokenSortRatio = builder.tokenSortRatio;
        this.tokenSetRatio = builder.tokenSetRatio;
        this.tokenSetPartialRatio = builder.tokenSetPartialRatio;
        this.tokenWeightedRatio = builder.tokenWeightedRatio;
    }
}
