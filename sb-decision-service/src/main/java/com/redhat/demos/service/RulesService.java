package com.redhat.demos.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.redhat.demos.model.DecisionRequest;
import com.redhat.demos.model.DecisionResponse;

import org.kie.api.builder.KieScanner;
import org.kie.api.cdi.KContainer;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.command.Command;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;
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

    @KSession("stateless-session")
    private StatelessKieSession kieStatelessSession;   

    // @KContainer
    // @KReleaseId( groupId = "com.redhat.demos", artifactId = "business-assets-kjar", version = "1.0.0-SNAPSHOT")
    // This gets injected by the Spring DI in cse you choose not to use the kie-spring annotations.
    private final KieContainer kContainer;

    // @KSession("stateful-session")
    // @KReleaseId( groupId = "com.redhat.demos", artifactId = "business-assets-kjar", version = "2.0")
    // private KieSession kieSession2;

    public RulesService(KieContainer kieContainer) {
        this.kContainer = kieContainer;
    }

    public void scanLatestKieBase() {
        // get the KieScanner reference from the Spring ApplicationContext
        KieScanner kieScanner = applicationContext.getBean("named-session#scanner", KieScanner.class);
        // to get direct from the Kie API...
        // KieServices ks = KieServices.Factory.get();
        // KieScanner kieScanner = ks.newKieScanner(kContainer);
        kieScanner.scanNow();
    }

    public DecisionResponse fireRules(DecisionRequest request) {
        List<Command<?>> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(request.getPerson(), "person"));
        cmds.add(CommandFactory.newFireAllRules("firedRules"));
        cmds.add(CommandFactory.newGetObjects(new ClassObjectFilter(DecisionResponse.class), "decisionFacts"));

        final long executionStart = System.currentTimeMillis();
        LOGGER.info("=================================== START {} ===================================", "DROOLS EXECUTION RULES");

        ExecutionResults results = kieStatelessSession.execute(CommandFactory.newBatchExecution(cmds));

        LOGGER.info("{} rules fired in {} ms", results.getValue("firedRules"), (System.currentTimeMillis() - executionStart));
        LOGGER.info("==================================== END {} ====================================", "DROOLS EXECUTION RULES");

        DecisionResponse response = new DecisionResponse();
        @SuppressWarnings("unchecked") // the kie api returns a List<Object> in this case
        List<Object> decisionFacts = ((List<Object>)results.getValue("decisionFacts"));
        if (!decisionFacts.isEmpty())
            response = (DecisionResponse)decisionFacts.iterator().next();

        return response;
    }

    public DecisionResponse fireRulesOnStatefulSession(DecisionRequest request) {
        KieSession kieStatefulSession = kContainer.newKieSession("stateful-session");

        final long executionStart = System.currentTimeMillis();
        LOGGER.info("=================================== START {} ===================================", "DROOLS EXECUTION RULES");
        kieStatefulSession.insert(request.getPerson());
        int firedRules = kieStatefulSession.fireAllRules(100);

        LOGGER.info("{} rules fired in {} ms", firedRules, (System.currentTimeMillis() - executionStart));
        LOGGER.info("==================================== END {} ====================================", "DROOLS EXECUTION RULES");

        DecisionResponse response = new DecisionResponse();
        Iterator<?> decisionFacts = kieStatefulSession.getObjects(new ClassObjectFilter(DecisionResponse.class)).iterator();
        if (decisionFacts.hasNext())
            response = (DecisionResponse)decisionFacts.next();
        
        kieStatefulSession.dispose();
        return response;
    }


}