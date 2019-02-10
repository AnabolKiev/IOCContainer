package com.anabol.ioccontainer.entities;

public class TestImpl implements ITest {

    private String name;
    private int count;

    @Override
    public void doSomething() {
        System.out.println("I did something");
        System.out.println("My name is " + name + " and count = " + count);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
