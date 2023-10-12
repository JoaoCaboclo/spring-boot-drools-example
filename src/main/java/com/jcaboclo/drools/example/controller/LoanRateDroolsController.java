package com.jcaboclo.drools.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcaboclo.drools.example.model.LoanApplicant;
import com.jcaboclo.drools.example.model.LoanRate;
import com.jcaboclo.drools.example.service.LoanRateDroolsService;

@RestController()
@RequestMapping("/bankservice")
public class LoanRateDroolsController {

    @Autowired
    private LoanRateDroolsService loanRateDroolsService;

    @PostMapping("/getrate")
    public ResponseEntity<LoanRate> getRate(@RequestBody LoanApplicant request){
        LoanRate rate = loanRateDroolsService.getRate(request);
        return new ResponseEntity<>(rate, HttpStatus.OK);
    }
}
