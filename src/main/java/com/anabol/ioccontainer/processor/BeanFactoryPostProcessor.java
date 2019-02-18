package com.anabol.ioccontainer.processor;

import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public interface BeanFactoryPostProcessor {
    void postProcessBeanFactory(List<BeanDefinition> definitions);
}


// declare bean in XML
//class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor { // system bean

//    @Override
//    public void postProcessBeanFactory(List<BeanDefinition> definitions) {

//    }
//}
