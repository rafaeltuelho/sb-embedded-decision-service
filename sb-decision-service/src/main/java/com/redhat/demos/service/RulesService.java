package com.redhat.demos.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.redhat.demos.model.DecisionRequest;
import com.redhat.demos.model.DecisionResponse;
import com.redhat.demos.model.Person;

import org.kie.api.builder.KieScanner;
import org.kie.api.cdi.KReleaseId;
import org.kie.api.cdi.KSession;
import org.kie.api.command.Command;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.ObjectFilter;
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
    
    // @KSession("stateful-session")
    // private KieSession kieSession;

    @KSession("stateless-session")
    private StatelessKieSession kieSession;

    // @KSession("stateful-session")
    // @KReleaseId( groupId = "com.redhat.demos", artifactId = "business-assets-kjar", version = "1.0")
    // private KieSession kieSession2;

    public void scanLatestKieBase() {
        KieScanner releaseIdScanner = applicationContext.getBean("named-session#scanner", KieScanner.class);
        releaseIdScanner.scanNow();        
    }

    public DecisionResponse fireRules(DecisionRequest request) {
        List<Command<?>> cmds = new ArrayList<>();
        cmds.add(CommandFactory.newInsert(request.getPerson(), "person"));
        cmds.add(CommandFactory.newFireAllRules("firedRules"));
        cmds.add(CommandFactory.newGetObjects(new ClassObjectFilter(DecisionResponse.class), "decisionFacts"));

        final long executionStart = System.currentTimeMillis();
        LOGGER.info("=================================== START {} ===================================", "DROOLS EXECUTION RULES");

        ExecutionResults results = kieSession.execute(CommandFactory.newBatchExecution(cmds));

        LOGGER.info("{} rules fired in {} ms", results.getValue("firedRules"), (System.currentTimeMillis() - executionStart));
        LOGGER.info("==================================== END {} ====================================", "DROOLS EXECUTION RULES");

        DecisionResponse response = new DecisionResponse();
        @SuppressWarnings("unchecked") // the kie api returns a List<Object> in this case
        List<Object> decisionFacts = ((List<Object>)results.getValue("decisionFacts"));
        if (!decisionFacts.isEmpty())
            response = (DecisionResponse)decisionFacts.iterator().next();

        return response;
    }

}