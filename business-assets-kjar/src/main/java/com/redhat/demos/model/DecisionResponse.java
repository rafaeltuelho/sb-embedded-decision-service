package com.redhat.demos.model;

public class DecisionResponse {
    private Boolean adult;

    public DecisionResponse() {
    }

    public DecisionResponse(Boolean isAdult) {
        this.adult = isAdult;
    }

    public Boolean isAdult() {
        return this.adult;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @Override
    public String toString() {
        return "{" +
            " adult='" + isAdult() + "'" +
            "}";
    }

}
