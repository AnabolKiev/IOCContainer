package com.anabol.ioccontainer.reader.impl.sax;

import com.anabol.ioccontainer.entity.BeanDefinition;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextHandler extends DefaultHandler {

    private List<BeanDefinition> beanDefinitions = new ArrayList<>();
    private BeanDefinition beanDefinition = null;
    private Map<String, String> valueDependencies = null;
    private Map<String, String> refDependencies = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("bean")) {  // create a new BeanDefinition and put it in Map
            beanDefinition = new BeanDefinition();
            valueDependencies = new HashMap<>();
            refDependencies = new HashMap<>();
            if (attributes != null) {
                String id = attributes.getValue("id");
                String className = attributes.getValue("class");
                if (id != null && className != null) {
                    beanDefinition.setId(id);
                    beanDefinition.setClassName(className);
                } else {
                    throw new SAXException("ID or ClassName of bean is missed");
                }
            }

        } else if (qName.equalsIgnoreCase("property")) {
            if (attributes != null) {
                String name = attributes.getValue("name");
                String value = attributes.getValue("value");
                String ref = attributes.getValue("ref");
                if (name != null && value != null && ref == null) {
                    valueDependencies.put(name, value);
                } else if (name != null && value == null && ref != null) {
                    refDependencies.put(name, ref);
                } else {
                    throw new SAXException("Bean property are incorrect");
                }
            } else {
                throw new SAXException("Missed property attributes");
            }
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("bean")) {
            beanDefinition.setValueDependencies(valueDependencies);
            beanDefinition.setRefDependencies(refDependencies);
            beanDefinitions.add(beanDefinition);
        }
    }


    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitions;
    }
}
