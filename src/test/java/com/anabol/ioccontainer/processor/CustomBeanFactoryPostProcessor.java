package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            if ("com.anabol.ioccontainer.entity.TestImpl".equals(beanDefinition.getClassName())) {
                beanDefinition.getValueDependencies().put("name", "Updated by PostProcessor");
            }
        }
    }
}
