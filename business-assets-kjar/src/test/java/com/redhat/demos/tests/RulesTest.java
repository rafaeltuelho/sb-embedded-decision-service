package com.redhat.demos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import org.junit.Test;
import org.kie.api.runtime.KieSession;

public class RulesTest extends RulesBaseTest {

    @Test
    public void rulesTest() {
        KieSession kSession = createDefaultSession();
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