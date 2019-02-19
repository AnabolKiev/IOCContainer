package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.exception.BeanInitiationException;

public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object bean, String id) throws BeanInitiationException;

    Object postProcessAfterInitialization(Object bean, String id) throws BeanInitiationException;
}