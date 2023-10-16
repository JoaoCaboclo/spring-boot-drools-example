package com.jcaboclo.drools.example.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {
    private static final String RULES_CUSTOMER_RULES_DRL = "rules/customer-discount.drl";
    private static final String RULES_CUSTOMER_LOAN_RATE_RULES_DRL = "rules/loan_rate.drl";
    private static final KieServices kieServices = KieServices.Factory.get();

    // Define the properties for your ReleaseId
    String groupId = "com.jcaboclo";
    String artifactId = "spring-boot-drools-example";
    String version = "0.0.1-SNAPSHOT";
    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_LOAN_RATE_RULES_DRL));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        KieScanner  kieScanner    = kieServices.newKieScanner(kieContainer);
        ReleaseId releaseId = kieServices.newReleaseId(groupId, artifactId, version);
        // Print the ReleaseId
        System.out.println("ReleaseId: " + releaseId.toString());
     //   kieScanner.start(1000);
        return kieContainer;
    }
}
