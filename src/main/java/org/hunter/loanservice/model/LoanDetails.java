package org.hunter.loanservice.model;

public class LoanDetails {

    private Integer loanAmountCents;
    private Integer termMonths;
    private Integer rateTimesHundred;

    public LoanDetails(Integer loanAmountCents, Integer rateTimesHundred, Integer termMonths) {
        this.loanAmountCents = loanAmountCents;
        this.termMonths = termMonths;
        this.rateTimesHundred = rateTimesHundred;
    }


    public Integer getLoanAmountCents() {
        return loanAmountCents;
    }

    public Integer getTermMonths() {
        return termMonths;
    }

    public Integer getRateTimesHundred() {
        return rateTimesHundred;
    }

}
