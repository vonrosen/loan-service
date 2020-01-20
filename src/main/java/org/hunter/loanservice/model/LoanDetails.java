package org.hunter.loanservice.model;

import java.math.BigInteger;

public class LoanDetails {

    private BigInteger loanAmountCents;
    private Integer termMonths;
    private Integer rateTimesHundred;

    public LoanDetails(BigInteger loanAmountCents, Integer rateTimesHundred, Integer termMonths) {
        this.loanAmountCents = loanAmountCents;
        this.termMonths = termMonths;
        this.rateTimesHundred = rateTimesHundred;
    }


    public BigInteger getLoanAmountCents() {
        return loanAmountCents;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public Integer getRateTimesHundred() {
        return rateTimesHundred;
    }

}
