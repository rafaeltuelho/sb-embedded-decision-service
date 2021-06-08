package com.redhat.demos;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-context.xml"})
public class DecisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecisionApplication.class, args);
    }

    // @Bean
    // public KieContainer kieContainer() {
    //     return KieServices.Factory.get().getKieClasspathContainer();
    // }

    // @Bean
    // public KieSession kieSession() {
    //     KieServices kieServices = KieServices.Factory.get();
    //     KieContainer kContainer = kieServices.getKieClasspathContainer();
    //     KieSession kieSession = kContainer.newKieSession(); //default stateful kSession
    //     // StatelessKieSession statelessKieSession = kContainer.newStatelessKieSession("KSession2_2");

    //     return kieSession;
    // }
}