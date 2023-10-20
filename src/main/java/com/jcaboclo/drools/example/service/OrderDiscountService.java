package com.jcaboclo.drools.example.service;

import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jcaboclo.drools.example.model.OrderDiscount;
import com.jcaboclo.drools.example.model.OrderRequest;

import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;

import org.kie.internal.io.ResourceFactory;

import static org.kie.api.KieServices.*;


@Service
public class OrderDiscountService {
    private static final String RULES_ORDER_DISCOUNT_XLS = "rules/planilhas/customer-rules.xlsx";
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/arquivosdrl/customer-discount.drl";
    @Autowired
    private KieContainer kieContainer;

    public OrderDiscount getDiscount(OrderRequest orderRequest) {

        KieServices kieServices = Factory.get();
        OrderDiscount orderDiscount = new OrderDiscount();

        String ruleFile = RULES_CUSTOMER_RULES_DRL;
        if (orderRequest.getFileRuleType().equals("XLSX")) {
            ruleFile = RULES_ORDER_DISCOUNT_XLS;
        } else if (orderRequest.getFileRuleType().equals("DRL")) {
            ruleFile = RULES_CUSTOMER_RULES_DRL;
        };

        KieSession kieSession = getKieSession(ruleFile);
        KieScanner kieScanner = kieServices.newKieScanner(kieContainer);
        kieScanner.start(1000l);
        kieSession.setGlobal("orderDiscount", orderDiscount);
        kieSession.insert(orderRequest);
        kieSession.fireAllRules();
        kieSession.dispose();
        return orderDiscount;
    }
    public KieSession getKieSession(String rdlfile){
        KieFileSystem kieFileSystem = getkiefilesystem(rdlfile);
        KieBuilder kiebuilder = KieServices.get().newKieBuilder(kieFileSystem);
        kiebuilder.buildAll();
        KieModule kiemodule = kiebuilder.getKieModule();
        KieContainer kiecontainer = KieServices.get().newKieContainer(kiemodule.getReleaseId());;
        KieSession kiesession = kiecontainer.newKieSession();
        return kiesession ;
    }

    private KieFileSystem getkiefilesystem(String rdlfile) {
        KieFileSystem kiefilesystem = get().newKieFileSystem();
        kiefilesystem.write(ResourceFactory.newClassPathResource(rdlfile));
        return kiefilesystem;
    }
}
