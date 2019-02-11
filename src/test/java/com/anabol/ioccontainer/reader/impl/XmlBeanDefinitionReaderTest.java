package com.anabol.ioccontainer.reader.impl;

import com.anabol.ioccontainer.entity.BeanDefinition;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class XmlBeanDefinitionReaderTest {
    private static final String CONTEXT_XML = "<beans>\n" +
            "    <bean id=\"testImpl\" class=\"com.anabol.ioccontainer.entities.TestImpl\">\n" +
            "        <property name=\"name\" value=\"TEST_NAME\"/>\n" +
            "    </bean>\n" +
            "    <bean id=\"testService\" class=\"com.anabol.ioccontainer.entities.TestService\">\n" +
            "        <property name=\"test\" ref=\"testImpl\"/>\n" +
            "    </bean>\n" +
            "</beans>";

    @Test
    public void testGetBeanDefinitions() throws IOException, SAXException, ParserConfigurationException {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader();
        List<BeanDefinition> beanDefinitions = reader.getBeanDefinitions(new ByteArrayInputStream(CONTEXT_XML.getBytes()));

        assertEquals(2, beanDefinitions.size());
        BeanDefinition beanDefinition = beanDefinitions.get(0);
        assertEquals("testImpl", beanDefinition.getId());
        assertEquals("com.anabol.ioccontainer.entities.TestImpl", beanDefinition.getClassName());
        assertTrue(beanDefinition.getRefDependencies().isEmpty());
        assertEquals(1, beanDefinition.getValueDependencies().size());
        assertTrue(beanDefinition.getValueDependencies().containsKey("name"));
        assertEquals("TEST_NAME", beanDefinition.getValueDependencies().get("name"));

        beanDefinition = beanDefinitions.get(1);
        assertEquals("testService", beanDefinition.getId());
        assertEquals("com.anabol.ioccontainer.entities.TestService", beanDefinition.getClassName());
        assertTrue(beanDefinition.getValueDependencies().isEmpty());
        assertEquals(1, beanDefinition.getRefDependencies().size());
        assertTrue(beanDefinition.getRefDependencies().containsKey("test"));
        assertEquals("testImpl", beanDefinition.getRefDependencies().get("test"));
    }

}
