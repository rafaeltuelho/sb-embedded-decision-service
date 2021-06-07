package com.redhat.demos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.drools.compiler.compiler.DecisionTableProvider;
import org.drools.core.util.IoUtils;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.cdi.KContainer;
import org.kie.api.command.Command;
import org.kie.api.io.Resource;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import org.kie.internal.builder.DecisionTableConfiguration;
import org.kie.internal.builder.DecisionTableInputType;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.command.CommandFactory;
import org.kie.internal.io.ResourceFactory;

public class RulesTest extends RulesBaseTest {

    @Test
    public void rulesTest() {
        KieSession kSession = createDefaultSession();//createSession("default");
        assertNotNull(kSession);

        kSession.insert(Integer.valueOf(50));
        kSession.insert("go");
        kSession.insert(LocalDate.now());

        int fired = kSession.fireAllRules();
        assertEquals(4, fired);
        assertEquals(7, kSession.getFactCount());

        // filter LocaDate facts from the WM
        kSession.getFactHandles( f -> ( f instanceof LocalDate ) )
            .stream().forEach(System.out::println);        

        System.out.println("Fact Count: " + kSession.getFactCount());
        System.out.println("Fired Rules: " + fired);
        kSession.dispose();
    }

}