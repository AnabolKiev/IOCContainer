package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.Bean;
import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.exception.BeanInitiationException;
import com.anabol.ioccontainer.exception.NotUniqueBeanException;
import com.anabol.ioccontainer.reader.BeanDefinitionReader;
import com.anabol.ioccontainer.reader.impl.XmlBeanDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GenericApplicationContext implements ApplicationContext {

    private List<Bean> beans;

    public GenericApplicationContext(String... filePaths) {
        BeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(filePaths);
        List<BeanDefinition> beanDefinitions = definitionReader.getBeanDefinitionList();
        beans = createBeans(beanDefinitions);
        injectValueDependencies(beanDefinitions, beans);
        injectRefDependencies(beanDefinitions, beans);
    }

    @Override
    public Object getBean(String beanId) {
        return getBean(beanId, beans);
    }

    private Object getBean(String beanId, List<Bean> beans) {
        Object result = null;
        for (Bean bean : beans) {
            if (bean.getId().equals(beanId)) {
                if (result != null) {
                    throw new NotUniqueBeanException("There are more then one bean for ID " + beanId);
                }
                result = bean.getValue();
            }
        }
        return result;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        T result = null;
        for (Bean bean : beans) {
            if (clazz.isAssignableFrom(bean.getValue().getClass())) {
                if (result != null) {
                    throw new NotUniqueBeanException("There are more then one bean for " + clazz.getName());
                }
                result = clazz.cast(bean.getValue());
            }
        }
        return result;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object bean = getBean(beanId);
        if (bean != null && clazz.isAssignableFrom(bean.getClass())) {
            return clazz.cast(bean);
        }
        return null;
    }

    @Override
    public List<String> getBeanNames() {
        List<String> beanNames = new ArrayList<>();
        for (Bean bean : beans) {
            beanNames.add(bean.getId());
        }
        return beanNames;
    }

    List<Bean> createBeans(List<BeanDefinition> beanDefinitions) {
        List<Bean> result = new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Bean bean = new Bean();
                Class<?> clazz = Class.forName(beanDefinition.getClassName());
                bean.setId(beanDefinition.getId());
                bean.setValue(clazz.newInstance());
                result.add(bean);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new BeanInitiationException(e);
            }
        }
        return result;
    }

    void injectValueDependencies(List<BeanDefinition> beanDefinitions, List<Bean> beans) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            for (Map.Entry<String, String> entry : beanDefinition.getValueDependencies().entrySet()) {
                String setterName = getSetterName(entry.getKey());
                Object bean = getBean(beanDefinition.getId(), beans);
                Method[] methods = bean.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(setterName)) {
                        try {
                            method.invoke(bean, entry.getValue());
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new BeanInitiationException("Cannot set value for attribute " + entry.getKey() +
                                    " for bean " + beanDefinition.getId(), e);
                        }
                        break;
                    }
                }
            }
        }
    }

    void injectRefDependencies(List<BeanDefinition> beanDefinitions, List<Bean> beans) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            for (Map.Entry<String, String> entry : beanDefinition.getRefDependencies().entrySet()) {
                String setterName = getSetterName(entry.getKey());
                Object bean = getBean(beanDefinition.getId(), beans);
                Object linkedBean = getBean(entry.getValue(), beans);
                Method[] methods = bean.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(setterName)) {
                        try {
                            method.invoke(bean, linkedBean);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            throw new BeanInitiationException("Cannot wire dependency for bean " + beanDefinition.getId(), e);
                        }
                        break;
                    }
                }
            }
        }
    }

    static String getSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
