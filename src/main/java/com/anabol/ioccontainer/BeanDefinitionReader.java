package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public interface BeanDefinitionReader {

    List<BeanDefinition> getBeanDefinitionList();

}
