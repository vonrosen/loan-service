package org.hunter.loanservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.hunter.loanservice.model.LoanDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoanServiceController {

    @GetMapping(path="/loan-values", produces="application/json")
    public List<LoanDetails> getLoanValues(@RequestParam(required=true) String maxPaymentAmount, @RequestParam(required=true) Integer term) {

        LoanDetails ld = new LoanDetails(1, 1, 1);
        List<LoanDetails> l = new ArrayList<LoanDetails>();
        l.add(ld);

        return l;
    }
}
