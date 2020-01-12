package org.hunter.loanservice.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hunter.loanservice.exception.LoanServiceException;
import org.hunter.loanservice.model.LoanDetails;
import org.hunter.loanservice.service.LoanDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class LoanServiceController {

    @Autowired
    private LoanDetailService loanDetailService;

    @GetMapping(path = "/loan-values", produces = "application/json")
    public Map<Integer, List<LoanDetails>> getLoanValues(@RequestParam(required = true) String maxPaymentAmount,
            @RequestParam(required = false) Integer term) {

        try {
            return loanDetailService.getLoanDetails(new BigDecimal(maxPaymentAmount), term);
        }
        catch (LoanServiceException exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exc.getMessage());
        }
        catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred");
        }
    }
}
