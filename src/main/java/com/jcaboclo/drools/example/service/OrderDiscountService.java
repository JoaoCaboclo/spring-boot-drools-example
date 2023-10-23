package com.jcaboclo.drools.example.service;

import com.jcaboclo.drools.example.config.DroolsConfig;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcaboclo.drools.example.model.OrderDiscount;
import com.jcaboclo.drools.example.model.OrderRequest;

@Service
public class OrderDiscountService {
    private static final String RULES_ORDER_DISCOUNT_XLS = "rules/planilhas/customer-rules.xlsx";
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/arquivosdrl/customer-discount.drl";
    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private DroolsConfig droolsConfig;

    public OrderDiscount getDiscount(OrderRequest orderRequest) {

        KieServices kieServices = KieServices.Factory.get();
        OrderDiscount orderDiscount = new OrderDiscount();

        String fileRulesName = RULES_CUSTOMER_RULES_DRL;
        if (orderRequest.getFileRuleType().equals("XLSX")) {
            fileRulesName = RULES_ORDER_DISCOUNT_XLS;
        } else if (orderRequest.getFileRuleType().equals("DRL")) {
            fileRulesName = RULES_CUSTOMER_RULES_DRL;
        };

        KieSession kieSession = droolsConfig.getKieSession(fileRulesName);
        KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
        kieSession.setGlobal("orderDiscount", orderDiscount);
        kieSession.insert(orderRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return orderDiscount;

    }

}
