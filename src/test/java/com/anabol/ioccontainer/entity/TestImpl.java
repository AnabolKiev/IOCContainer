package com.anabol.ioccontainer.entity;

public class TestImpl implements ITest {

    private String name;

    @Override
    public void doSomething() {
        System.out.println("I did something");
        System.out.println("My name is " + name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
