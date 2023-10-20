package com.jcaboclo.drools.example.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/arquivosdrl/customer-discount.drl";
    private static final String RULES_CUSTOMER_LOAN_RATE_RULES_DRL = "rules/arquivosdrl/loan_rate.drl";
    private static final String RULES_ORDER_DISCOUNT_XLS = "rules/planilhas/customer-rules.xlsx";
    private static final KieServices kieServices = KieServices.Factory.get();

    // Define the properties for your ReleaseId
    String groupId = "com.jcaboclo";
    String artifactId = "spring-boot-drools-example";
    String version = "0.0.1-SNAPSHOT";

    /*
       String groupId = "org.default";
       String artifactId = "artifact";
       String version = "1.0.0";
    */

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_LOAN_RATE_RULES_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_ORDER_DISCOUNT_XLS));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        //ReleaseId releaseId = kieServices.newReleaseId(groupId, artifactId, version);
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
    }
}
