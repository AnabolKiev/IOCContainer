package com.anabol.ioccontainer.entities;

public class TestService {

    private ITest test;

    public void setTest(ITest test) {
        this.test = test;
    }

    public void doSomething() {
        test.doSomething();
    }
}
