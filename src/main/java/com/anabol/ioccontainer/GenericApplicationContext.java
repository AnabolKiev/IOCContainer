package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.Bean;
import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.reader.BeanDefinitionReader;
import com.anabol.ioccontainer.reader.impl.XmlBeanDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class GenericApplicationContext implements ApplicationContext {
    private List<Bean> beans = new ArrayList<>();

    public GenericApplicationContext(String filePath) {
        BeanDefinitionReader definitionReader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = definitionReader.getBeanDefinitionList(filePath);
        beans = createBeans(beanDefinitions);
    }

    @Override
    public Object getBean(String beanId) {
        Object result = null;
        for (Bean bean : beans) {
            if (bean.getId().equals(beanId)) {
                if (result == null) {
                    result = bean;
                } else {
                    throw new RuntimeException("There are more then one bean for ID " + beanId);
                }
            }
        }
        return result;
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        T bean = null;
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            if (entry.getValue().getClass() == clazz) {
                if (bean == null) {
                    bean = clazz.cast(entry.getValue());
                } else {
                    throw new RuntimeException("There are more then one bean for " + clazz.getName());
                }
            }
        }
        return bean;
    }

    @Override
    public <T> T getBean(String beanId, Class<T> clazz) {
        Object bean = beans.get(beanId);
        if (bean != null && bean.getClass() == clazz) {
            return clazz.cast(bean);
        } else {
            return null;
        }
    }

    @Override
    public List<String> getBeanNames() {
        List<String> beanNames = new ArrayList<>();
        for (Bean bean : beans) {
            beanNames.add(bean.getId());
        }
        return beanNames;
    }

    private List<Bean> createBeans(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Bean bean = new Bean();
                Class<?> clazz = Class.forName(beanDefinition.getClassName());
                bean.setId(beanDefinition.getId());
                bean.setValue(clazz.newInstance());
                injectValueDependencies(beanDefinition, bean);
                injectRefDependencies(beanDefinition, bean);
                beans.add(bean);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void injectValueDependencies(BeanDefinition beanDefinition, Object bean) throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<String, String> entry : beanDefinition.getValueDependencies().entrySet()) {
            String setterName = getSetterName(entry.getKey());
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equals(setterName)) {
                    method.invoke(bean, entry.getValue());
                    break;
                }
            }
        }
    }

    private void injectRefDependencies(BeanDefinition beanDefinition, Object bean) throws InvocationTargetException, IllegalAccessException {
        for (Map.Entry<String, String> entry : beanDefinition.getRefDependencies().entrySet()) {
            String setterName = getSetterName(entry.getKey());
            Object linkedBean = getBean(entry.getValue());
            Method[] methods = bean.getClass().getMethods();
            for (Method method : methods) {
                if (method.getName().equals(setterName)) {
                    method.invoke(bean, linkedBean);
                    break;
                }
            }
        }
    }

    private static String getSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

}
