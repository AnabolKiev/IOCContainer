package com.anabol.ioccontainer.entity;

public class TestService {

    private ITest test;

    public void setTest(ITest test) {
        this.test = test;
    }

    public ITest getTest() {
        return test;
    }

    public void doSomething() {
        test.doSomething();
    }
}
