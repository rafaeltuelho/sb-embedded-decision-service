package com.redhat.demos.model;

public class DecisionRequest {
    private Person person;

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "DecisionRequest [person=" + person + "]";
    }

}
