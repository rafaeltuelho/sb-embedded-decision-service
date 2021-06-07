package com.redhat.demos.model;

public class DecisionRequest {
    private Driver driver;
    private Violation violation;

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Violation getViolation() {
        return this.violation;
    }

    public void setViolation(Violation violation) {
        this.violation = violation;
    }

    @Override
    public String toString() {
        return "DecisionRequest [driver=" + driver + ", violation=" + violation + "]";
    }

}
