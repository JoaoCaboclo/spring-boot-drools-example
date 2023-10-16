package com.jcaboclo.drools.example.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcaboclo.drools.example.model.LoanApplicant;
import com.jcaboclo.drools.example.model.LoanRate;

@Service
public class LoanRateDroolsService {

    @Autowired
    private KieContainer kieContainer;

    public LoanRate getRate(LoanApplicant loanApplicantRequest) {
        LoanRate loanRate = new LoanRate();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("loanRate", loanRate);
        kieSession.insert(loanApplicantRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return loanRate;
    }
}

