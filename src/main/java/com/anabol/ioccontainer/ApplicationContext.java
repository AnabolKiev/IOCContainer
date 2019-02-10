package com.anabol.ioccontainer;

import java.util.Set;

public interface ApplicationContext {

    Object getBean(String beanId);

    <T> T getBean(Class<T> clazz);

    <T> T getBean(String beanId, Class<T> clazz);

    Set<String> getBeanNames();
}
