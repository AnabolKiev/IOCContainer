package com.anabol.ioccontainer;

import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.reader.impl.XmlBeanDefinitionReader;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.List;

public class XmlBeanDefinitionReaderTest {

    @Test
    public void getBeanDefinitionListTest() {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader();
        List<BeanDefinition> list = reader.getBeanDefinitionList("src/test/resources/context.xml");
        assertEquals(2, list.size());
        assertEquals(1, list.get(0).getValueDependencies().size());
        assertEquals(0, list.get(0).getRefDependencies().size());
        assertEquals(0, list.get(1).getValueDependencies().size());
        assertEquals(1, list.get(1).getRefDependencies().size());
    }

}
