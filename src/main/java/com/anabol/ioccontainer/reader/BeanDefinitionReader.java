package com.anabol.ioccontainer.reader;

import com.anabol.ioccontainer.entity.BeanDefinition;

import java.util.List;

public interface BeanDefinitionReader {

    List<BeanDefinition> getBeanDefinitionList();

}
