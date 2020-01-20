package org.hunter.loanservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hunter.loanservice.config.AppProperties;
import org.hunter.loanservice.exception.LoanServiceException;
import org.hunter.loanservice.model.LoanDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanDetailService {

    @Autowired
    private AppProperties appProperties;

    public Map<Integer, List<LoanDetails>> getLoanDetails(BigDecimal monthlyPayment, Integer term)
            throws LoanServiceException {

        if (term != null && term != 30 && term != 15) {
            throw new LoanServiceException("Term of " + term + " not supported!");
        }

        Map<Integer, List<LoanDetails>> termsToRates = new HashMap<Integer, List<LoanDetails>>();
        Set<BigDecimal> rates = null;
        List<Integer> terms = new ArrayList<Integer>();

        if (term == null) {
            terms.add(30);
            terms.add(15);
        }
        else {
            terms.add(term);
        }

        for (Integer thisTerm : terms) {
            rates = appProperties.getFixedRates(thisTerm);
            List<LoanDetails> loanDetails = new ArrayList<LoanDetails>();

            for (BigDecimal rate : rates) {
                loanDetails.add(getLoanDetails(monthlyPayment, rate, thisTerm));
            }

            termsToRates.put(thisTerm, loanDetails);
        }

        return termsToRates;
    }

    private LoanDetails getLoanDetails(BigDecimal monthlyPayment, BigDecimal annualRate, Integer term) {
        return new LoanDetails(
                calculateLoanAmount(monthlyPayment, annualRate, term).multiply(BigDecimal.valueOf(100)).toBigInteger(),
                annualRate.multiply(BigDecimal.valueOf(100)).setScale(0).intValue(), term);
    }

    private BigDecimal calculateLoanAmount(BigDecimal monthlyPayment, BigDecimal annualRate, Integer term) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 4,
                BigDecimal.ROUND_UP);
        Integer numberPayments = term * 12;
        BigDecimal rateToPower = BigDecimal.ONE.add(monthlyRate).pow(numberPayments).setScale(4, BigDecimal.ROUND_UP);
        return monthlyPayment.multiply(rateToPower.subtract(BigDecimal.ONE)).divide(monthlyRate.multiply(rateToPower),
                2, BigDecimal.ROUND_DOWN);
    }

}
