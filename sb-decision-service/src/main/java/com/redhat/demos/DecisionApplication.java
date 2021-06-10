package com.redhat.demos;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.scanner.KieModuleMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:spring-context.xml"})
@Configuration
public class DecisionApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DecisionApplication.class, args);
    }

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = kieServices();
        KieContainer kContainer = ks.newKieContainer(businessAssetsKJar(ks));
        // Let's verify that all the resources are loaded correctly
        Results results = kContainer.verify();
        results.getMessages().stream().forEach((message) -> {
            LOGGER.info(">> Message ( {} ): {}", message.getLevel(), message.getText());
        });
        // If there is an Error we need to stop and correct it
        boolean hasError = results.hasMessages(Message.Level.ERROR);
        LOGGER.info("Any Error : {}", hasError);
        if (hasError) {
            throw new UnsupportedOperationException();
        }
        // Here we make sure that all the KieBases and KieSessions
        // that we are expecting are loaded.
        kContainer.getKieBaseNames().stream().map((kieBase) -> {
            LOGGER.info(">> Loading KieBase: {}", kieBase);
            return kieBase;
        }).forEach((kieBase) -> {
            kContainer.getKieSessionNamesInKieBase(kieBase).stream().forEach((kieSession) -> {
                LOGGER.info("\t >> Containing KieSession: {}", kieSession);
            });
        });
 
        // KieModuleMetaData kieModuleMetaData = KieModuleMetaData.Factory.newKieModuleMetaData(businessAssetsKJar(ks));
 
        // kieModuleMetaData.getPackages().stream().map((pkg) -> {
        //     LOGGER.debug(" >> Package Loaded:  {}", pkg);
        //     return pkg;
        // }).forEach((pkg) -> {
        //     kieModuleMetaData.getRuleNamesInPackage(pkg).stream().forEach((rule) -> {
        //         LOGGER.info("\t >> Contain Rule:  {}", rule);
        //     });
        // });
 
        return kContainer;
    }
 
    private ReleaseId businessAssetsKJar(KieServices ks) {
        return ks.newReleaseId("com.redhat.demos", "business-assets-kjar", "1.0.0-SNAPSHOT");
    }
 
    private KieServices kieServices() {
        return KieServices.Factory.get();
    }   

}