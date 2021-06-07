package com.redhat.demos.model;

import java.time.LocalDate;

import org.kie.dmn.feel.lang.FEELProperty;

public class Violation {
    private String code;
    private LocalDate date;
    private String type;
    private Integer speedLimit;
    private Integer actualSpeed;

    @FEELProperty("Code")
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @FEELProperty("Date")
    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @FEELProperty("Type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @FEELProperty("Speed Limit")
    public Integer getSpeedLimit() {
        return this.speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    @FEELProperty("Actual Speed")
    public Integer getActualSpeed() {
        return this.actualSpeed;
    }

    public void setActualSpeed(Integer actualSpeed) {
        this.actualSpeed = actualSpeed;
    }

    @Override
    public String toString() {
        return "{" +
            " code='" + code + "'" +
            ", date='" + date + "'" +
            ", type='" + type + "'" +
            ", speedLimit='" + speedLimit + "'" +
            ", actualSpeed='" + actualSpeed + "'" +
            "}";
    }

}
