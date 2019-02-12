package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class GenericApplicationContextTest {

    private GenericApplicationContext applicationContext;
    private List<BeanDefinition> beanDefinitions;

    @Before
    public void before() {
        applicationContext = new GenericApplicationContext("src/test/resources/context.xml");

        BeanDefinition firstBeanDefinition = new BeanDefinition();
        firstBeanDefinition.setId("testBean1");
        firstBeanDefinition.setClassName("com.anabol.ioccontainer.entity.TestImpl");
        firstBeanDefinition.setValueDependencies(new HashMap<>());
        firstBeanDefinition.getValueDependencies().put("name", "TEST");
        firstBeanDefinition.setRefDependencies(new HashMap<>());

        BeanDefinition secondBeanDefinition = new BeanDefinition();
        secondBeanDefinition.setId("testBean2");
        secondBeanDefinition.setClassName("com.anabol.ioccontainer.entity.TestService");
        secondBeanDefinition.setValueDependencies(new HashMap<>());
        secondBeanDefinition.setRefDependencies(new HashMap<>());
        secondBeanDefinition.getRefDependencies().put("test", "testBean1");

        beanDefinitions = new ArrayList<>();
        beanDefinitions.add(firstBeanDefinition);
        beanDefinitions.add(secondBeanDefinition);
    }

    @Test
    public void testCreateBeans() {
        List<Bean> beans = applicationContext.createBeans(beanDefinitions);

        assertEquals(2, beans.size());
        assertEquals("testBean1", beans.get(0).getId());
        assertEquals("com.anabol.ioccontainer.entity.TestImpl", beans.get(0).getValue().getClass().getName());
        assertEquals("testBean2", beans.get(1).getId());
        assertEquals("com.anabol.ioccontainer.entity.TestService", beans.get(1).getValue().getClass().getName());
    }

    @Test
    public void testInjectValueDependencies() {
        List<Bean> beans = applicationContext.createBeans(beanDefinitions);
        Object bean = beans.get(0).getValue();
        applicationContext.injectValueDependencies(beanDefinitions, beans);

        assertEquals("com.anabol.ioccontainer.entity.TestImpl", bean.getClass().getName());
        TestImpl testImpl = (TestImpl) bean;
        assertEquals("TEST", testImpl.getName());
    }

    @Test
    public void testInjectRefDependencies() {
        List<Bean> beans = applicationContext.createBeans(beanDefinitions);
        Object testBean = beans.get(0).getValue();
        Object testServiceBean = beans.get(1).getValue();
        applicationContext.injectRefDependencies(beanDefinitions, beans);

        assertEquals("com.anabol.ioccontainer.entity.TestImpl", testBean.getClass().getName());
        TestImpl testImpl = (TestImpl) testBean;
        assertEquals("com.anabol.ioccontainer.entity.TestService", testServiceBean.getClass().getName());
        TestService testService = (TestService) testServiceBean;

        assertEquals(testImpl, testService.getTest());
    }

    @Test
    public void testGetSetterName() {
        assertEquals("setAttribute", GenericApplicationContext.getSetterName("attribute"));
    }

    @Test
    public void testGetBeanNames() {
        List<String> beanNames = applicationContext.getBeanNames();
        assertEquals(3, beanNames.size());
    }

    @Test
    public void testGetBeanById() {
        assertNotNull(applicationContext.getBean("testImpl"));
    }

    @Test
    public void testGetBeanByWrongId() {
        assertNull(applicationContext.getBean("testImpl2"));
    }

    @Test
    public void testGetBeanByClass() {
        assertTrue(applicationContext.getBean(TestImpl.class).getClass() == TestImpl.class);
        TestImpl bean = applicationContext.getBean(TestImpl.class);
        assertNotNull(bean);
        assertEquals("TEST_NAME", bean.getName());
    }

    @Test
    public void testGetBeanByInterface() {
        assertTrue(applicationContext.getBean(ITest.class).getClass() == TestImpl.class);
        ITest bean = applicationContext.getBean(ITest.class);
        assertNotNull(bean);
        assertEquals("TEST_NAME", bean.getName());
    }

    @Test(expected = RuntimeException.class)
    public void testGetBeanByClassDuplicated() {
        applicationContext.getBean(TestService.class);
    }

    @Test
    public void testGetBeanByClassAndId() {
        assertNotNull(applicationContext.getBean("testImpl", ITest.class));
        assertNull(applicationContext.getBean("testImpl", TestService.class));
        assertNull(applicationContext.getBean("testImpl2", TestImpl.class));
    }

}
