package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.exception.BeanInitiationException;

public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String id) throws BeanInitiationException {
        return "Hello world";
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String id) throws BeanInitiationException {
        return null;
    }
}