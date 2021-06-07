package com.redhat.demos.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.redhat.demos.model.DecisionRequest;

import org.kie.api.builder.KieScanner;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RulesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionService.class);
    @Autowired 
    private ApplicationContext applicationContext;
    
    @KSession("stateful-session")
    private KieSession kieSession;

    // @KSession("stateful-session")
    // @KReleaseId( groupId = "com.redhat.demos", artifactId = "business-assets-kjar", version = "1.0")
    // private KieSession kieSession2;

    public void scanLatestKieBase() {
        KieScanner releaseIdScanner = applicationContext.getBean("named-session#scanner", KieScanner.class);
        releaseIdScanner.scanNow();        
    }

    public List<Object> fireRules(DecisionRequest request) {
        LOGGER.info("KieSession: " + kieSession.getIdentifier());

        kieSession.insert("go");

        // final long executionStart = System.currentTimeMillis();
        LOGGER.info("=================================== START {} ===================================", "DROOLS EXECUTION RULES");
        final int rulesFired = kieSession.fireAllRules();
        // LOGGER.info("end execution of {} rules, fired in {} ms", "Pre verification check", rulesFired, (System.currentTimeMillis() - executionStart));
        LOGGER.info("==================================== END {} ====================================", "DROOLS EXECUTION RULES");
        List<Object> facts = this.extractFacts();
        kieSession.dispose();

        return facts;
    }

    private List<Object> extractFacts() {
        LOGGER.info("Obtaining all facts from KieSession");

        @SuppressWarnings("unchecked")
        Collection<Object> result = (Collection<Object>) kieSession.getObjects();

        LOGGER.info("Facts Collection : {}", result);

        List<Object> facts = null;
        if (!result.isEmpty()) {
            LOGGER.debug("{} Facts obtained from KieSession", result.size());
            facts = Arrays.asList(result.toArray());
        }

        return facts;
    }
}