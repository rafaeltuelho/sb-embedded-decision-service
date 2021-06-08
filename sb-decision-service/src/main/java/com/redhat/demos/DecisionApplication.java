package com.redhat.demos;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-context.xml"})
public class DecisionApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DecisionApplication.class, args);
    }

    // @Bean
    // public KieContainer kieContainer() {
    //     return KieServices.Factory.get().getKieClasspathContainer();
    // }

}