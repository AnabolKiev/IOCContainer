package com.anabol.ioccontainer.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestService {

    private ITest test;

    public void doSomething() {
        test.doSomething();
    }
}
