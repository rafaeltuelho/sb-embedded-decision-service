package com.redhat.demos.model;

import org.kie.dmn.feel.lang.FEELProperty;

public class Driver {
    private String name;
    private Integer age;
    private String state;
    private String city;
    private Integer points;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @FEELProperty("Points")
    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + name + "'" +
            ", age='" + age + "'" +
            ", state='" + state + "'" +
            ", city='" + city + "'" +
            ", points='" + points + "'" +
            "}";
    }
}