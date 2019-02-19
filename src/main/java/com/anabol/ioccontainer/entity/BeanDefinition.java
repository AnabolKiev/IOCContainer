package com.anabol.ioccontainer.entity;

import lombok.*;

import java.util.Map;

@Getter
@Setter
public class BeanDefinition {

    private String id;
    private String className;
    private Map<String, String> valueDependencies;
    private Map<String, String> refDependencies;

}
