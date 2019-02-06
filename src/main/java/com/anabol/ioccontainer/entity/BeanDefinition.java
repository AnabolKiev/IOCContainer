package com.anabol.ioccontainer.entity;

import java.util.Map;

public class BeanDefinition {

    private String id;
    private String className;
    private Map<String, String> valueDependencies;
    private Map<String, String> refDependencies;

}
