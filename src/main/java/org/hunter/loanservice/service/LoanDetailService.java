package org.hunter.loanservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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

    public List<LoanDetails> getLoanDetails(BigDecimal monthlyPayment, Integer term)
            throws LoanServiceException {
        Set<BigDecimal> rates = null;

        if (term == 30) {
            rates = appProperties.getFixed30YearRates();
        }
        else if (term == 15) {
            rates = appProperties.getFixed15YearRates();
        }
        else {
            throw new LoanServiceException("Term of " + term + " not supported!");
        }

        List<LoanDetails> loanDetails = new ArrayList<LoanDetails>();

        for (BigDecimal rate : rates) {
            loanDetails.add(getLoanDetails(monthlyPayment, rate, term));
        }

        return loanDetails;
    }

    public LoanDetails getLoanDetails(BigDecimal monthlyPayment, BigDecimal annualRate, Integer term) {
        return new LoanDetails(
                calculateLoanAmount(monthlyPayment, annualRate, term).multiply(BigDecimal.valueOf(100)).intValue(),
                annualRate.multiply(BigDecimal.valueOf(100)).setScale(0).intValue(), term);
    }

    public BigDecimal calculateLoanAmount(BigDecimal monthlyPayment, BigDecimal annualRate, Integer term) {
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(12), 4,
                BigDecimal.ROUND_UP);
        Integer numberPayments = term * 12;
        BigDecimal rateToPower = BigDecimal.ONE.add(monthlyRate).pow(numberPayments).setScale(4, BigDecimal.ROUND_UP);
        return monthlyPayment.multiply(rateToPower.subtract(BigDecimal.ONE)).divide(monthlyRate.multiply(rateToPower),
                2, BigDecimal.ROUND_DOWN);
    }

}
