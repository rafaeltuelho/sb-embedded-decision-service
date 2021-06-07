package com.redhat.demos.model;

public class DecisionResponse {
    private Fine fine;
    private String driverSuspended;

    public Fine getFine() {
        return this.fine;
    }

    public void setFine(Fine fine) {
        this.fine = fine;
    }

    public String getDriverSuspended() {
        return this.driverSuspended;
    }

    public void setDriverSuspended(String driverSuspended) {
        this.driverSuspended = driverSuspended;
    }

    public DecisionResponse(Fine fine, String driverSuspended) {
        this.fine = fine;
        this.driverSuspended = driverSuspended;
    }

    @Override
    public String toString() {
        return "DecisionResponse [driverSuspended=" + driverSuspended + ", fine=" + fine + "]";
    }

}
