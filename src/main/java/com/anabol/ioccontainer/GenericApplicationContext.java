package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.Bean;
import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public class GenericApplicationContext implements ApplicationContext{
    private BeanDefinitionReader definitionReader;
    private List<BeanDefinition> beanDefinitions;
    private List<Bean> beans;

    @Override
    public Object getBean(String beanId) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return null;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        return null;
    }

    @Override
    public List<String> getBeanNames() {
        return null;
    }

    private void createBeans() {

    }

    private void injectValueDependencies() {

    }

    private void injectRefDependencies() {

    }

}
