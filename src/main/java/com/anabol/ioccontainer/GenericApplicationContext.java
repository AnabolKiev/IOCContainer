package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.reader.BeanDefinitionReader;
import com.anabol.ioccontainer.reader.impl.XmlBeanDefinitionReader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GenericApplicationContext implements ApplicationContext {
    private List<BeanDefinition> beanDefinitions;
    private Map<String, Object> beans = new HashMap<>();

    @Override
    public Object getBean(String beanId) {
        return beans.get(beanId);
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
    public Set<String> getBeanNames() {
        return beans.keySet();
    }

    private void createBeans() {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getClassName());
                Object bean = clazz.newInstance();
                injectValueDependencies(beanDefinition, bean);
                injectRefDependencies(beanDefinition, bean);
                beans.put(beanDefinition.getId(), bean);
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

    public GenericApplicationContext(String filePath) {
        BeanDefinitionReader definitionReader = new XmlBeanDefinitionReader();
        beanDefinitions = definitionReader.getBeanDefinitionList(filePath);
        createBeans();
    }

}
