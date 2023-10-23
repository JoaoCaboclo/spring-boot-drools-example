package com.jcaboclo.drools.example.service;

import com.jcaboclo.drools.example.config.DroolsConfig;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcaboclo.drools.example.model.LoanApplicant;
import com.jcaboclo.drools.example.model.LoanRate;

@Service
public class LoanRateDroolsService {

    private static final String RULES_CUSTOMER_LOAN_RATE_RULES_DRL = "rules/arquivosdrl/loan_rate.drl";
    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private DroolsConfig droolsConfig;

    public LoanRate getRate(LoanApplicant loanApplicantRequest) {
        LoanRate loanRate = new LoanRate();
        KieSession kieSession = droolsConfig.getKieSession(RULES_CUSTOMER_LOAN_RATE_RULES_DRL);
        kieSession.setGlobal("loanRate", loanRate);
        kieSession.insert(loanApplicantRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return loanRate;
    }
}

