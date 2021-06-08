package com.redhat.demos.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;

import com.redhat.demos.model.DecisionResponse;
import com.redhat.demos.model.Person;

import org.junit.Test;
import org.kie.api.runtime.KieSession;

public class RulesTest extends RulesBaseTest {

    @Test
    public void sampleRulesTest() {
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

    @Test
    public void adultRuleTest() {
        KieSession kSession = createDefaultSession();
        assertNotNull(kSession);

        Person person = new Person();
        person.setAge(37);
        person.setName("Rafael");
        kSession.insert(person);

        int fired = kSession.fireAllRules();
        assertEquals(2, fired);
        assertEquals(3, kSession.getFactCount());

        // filter DecisionResponse fact from the WM
        kSession.getFactHandles( f -> ( f instanceof DecisionResponse ) )
            .stream().forEach(System.out::println);

        System.out.println("Fact Count: " + kSession.getFactCount());
        System.out.println("Fired Rules: " + fired);
        kSession.dispose();
    }

}