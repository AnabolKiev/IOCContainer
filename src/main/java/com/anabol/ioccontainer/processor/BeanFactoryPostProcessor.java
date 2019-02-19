package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(List<BeanDefinition> beanDefinitions);

}