package com.redhat.demos.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import org.apache.maven.internal.aether.DefaultRepositorySystemSessionFactory;
import org.kie.dmn.feel.lang.FEELProperty;

public class Fine {
    private Integer points;
    private BigDecimal amount;

    @FEELProperty("Points")
    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @FEELProperty("Amount")
    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public static Fine valueOf(Map dmnResult) {
        final Fine fine = new Fine();
        fine.setAmount((BigDecimal)dmnResult.get("Amount"));
        fine.setPoints(((BigDecimal)dmnResult.get("Points")).intValue());
        return fine;
    }

    @Override
    public String toString() {
        return "{" +
            " points='" + points + "'" +
            ", amount='" + amount + "'" +
            "}";
    }

}
