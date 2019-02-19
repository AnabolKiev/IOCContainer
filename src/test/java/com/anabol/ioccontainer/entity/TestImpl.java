package com.anabol.ioccontainer.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;

@Setter
@Getter
public class TestImpl implements ITest {

    private String name;

    @Override
    public void doSomething() {
        System.out.println("I did something");
        System.out.println("My name is " + name);
    }

    @PostConstruct
    public void postConstruct() {
        name = "Updated in init section";
    }
}
