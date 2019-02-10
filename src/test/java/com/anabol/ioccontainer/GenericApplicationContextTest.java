package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entities.TestImpl;
import com.anabol.ioccontainer.entities.TestService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class GenericApplicationContextTest {

    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void before() {
        applicationContext = new GenericApplicationContext("src/test/resources/context.xml");
    }

    @Test
    public void getBeanNamesTest() {
        Set<String> beanNames = applicationContext.getBeanNames();
        assertEquals(3, beanNames.size());
    }

    @Test
    public void getBeanByIdTest() {
        assertNotNull(applicationContext.getBean("testImpl"));
    }

    @Test
    public void getBeanByWrongIdTest() {
        assertNull(applicationContext.getBean("testImpl2"));
    }

    @Test
    public void getBeanByClassTest() {
        assertNotNull(applicationContext.getBean(TestImpl.class));
    }

    @Test(expected = RuntimeException.class)
    public void getBeanByClassDuplicatedTest() {
        applicationContext.getBean(TestService.class);
    }

    @Test
    public void getBeanByClassAndIdTest() {
        assertNotNull(applicationContext.getBean("testImpl", TestImpl.class));
        assertNull(applicationContext.getBean("testImpl", TestService.class));
        assertNull(applicationContext.getBean("testImpl2", TestImpl.class));
    }

}
