package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.exception.BeanInitiationException;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String id) throws BeanInitiationException;

    Object postProcessAfterInitialization(Object bean, String id) throws BeanInitiationException;
}


// declare as bean in XML
//class MyBeanPostProcessor implements BeanPostProcessor { // system bean
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String id) throws BeanInstantiationException {
//        return "Hello world";
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String id) throws BeanInstantiationException {
//        return null;
//    }
//}

//class MySecondBeanPostProcessor implements BeanPostProcessor { // system bean
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String id) throws BeanInstantiationException {
//        return null;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String id) throws BeanInstantiationException {
//        return null;
//    }
//}