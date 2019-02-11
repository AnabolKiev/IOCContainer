package com.anabol.ioccontainer.reader.impl.sax;

import com.anabol.ioccontainer.entity.BeanDefinition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ContextHandler extends DefaultHandler {

    private LinkedList<BeanDefinition> beanDefinitions = new LinkedList<>();

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("bean")) {  // create a new BeanDefinition and put it in Map
            BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setValueDependencies(new HashMap<>());
            beanDefinition.setRefDependencies(new HashMap<>());
            beanDefinitions.add(beanDefinition);

            String id = attributes.getValue("id");
            String className = attributes.getValue("class");
            if (id == null || className == null) {
                throw new SAXException("ID or ClassName of bean is missed");
            }
            beanDefinition.setId(id);
            beanDefinition.setClassName(className);

        } else if (qName.equalsIgnoreCase("property")) {
            if (attributes.getLength() != 0) {
                BeanDefinition lastBeanDefinition = beanDefinitions.getLast();

                String name = attributes.getValue("name");
                String value = attributes.getValue("value");
                String ref = attributes.getValue("ref");
                if (name != null && value != null && ref == null) {
                    lastBeanDefinition.getValueDependencies().put(name, value);
                } else if (name != null && value == null && ref != null) {
                    lastBeanDefinition.getRefDependencies().put(name, ref);
                } else {
                    throw new SAXException("Bean property are incorrect");
                }
            } else {
                throw new SAXException("Missed property attributes");
            }
        }

    }

    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitions;
    }
}
