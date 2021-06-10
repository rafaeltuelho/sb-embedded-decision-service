package com.redhat.demos.service;

import java.util.Map;
import java.util.stream.Collectors;

import com.redhat.demos.model.DecisionRequest;
import com.redhat.demos.model.DecisionResponse;

import org.kie.api.builder.KieScanner;
import org.kie.api.cdi.KSession;
import org.kie.api.runtime.KieSession;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNMessage;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DecisionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DecisionService.class);
    @Autowired 
    private ApplicationContext applicationContext;
    
    @KSession("stateful-session")
    private KieSession kieSession;

    @Value("${dmn.modelNamespace}")
    private String modelNamespace;
    @Value("${dmn.modelName}")
    private String modelName;

    /**
     * Trigger the kie-scanner
     */
    public void scanLatestKieBase() {
        KieScanner releaseIdScanner = applicationContext.getBean("named-session#scanner", KieScanner.class);
        releaseIdScanner.scanNow();        
    }

    /**
     * Execute the DMN decisions
     * @param request DecisionRequest
     * @return DecisionResponse
     */
    public DecisionResponse callDecision(DecisionRequest request) {
        LOGGER.info("KieSession: " + kieSession.getIdentifier());
        final DMNRuntime dmnRuntime = kieSession.getKieRuntime(DMNRuntime.class);
        final DMNModel dmnModel = dmnRuntime.getModel(modelNamespace, modelName);
        final DMNContext dmnContext = dmnRuntime.newContext();

        dmnContext.set("Person", request.getPerson());

        final DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
        return extractResult(dmnResult);
    }

    private DecisionResponse extractResult(final DMNResult dmnResult) {
        final DMNDecisionResult isAnAdult = dmnResult.getDecisionResultByName("is an Adult?");
        if(dmnResult.hasErrors()){
            final String errors = dmnResult.getMessages(DMNMessage.Severity.ERROR).stream()
                    .map(message -> message.toString())
                    .collect(Collectors.joining(", "));
            throw new RuntimeException("DMN Error messages {" + errors + "}");
        }

        DecisionResponse decisionResponse = new DecisionResponse((Boolean)isAnAdult.getResult());

        LOGGER.debug("DecisionResponse: " + decisionResponse);
        return decisionResponse;
    }
}