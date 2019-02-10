package com.anabol.ioccontainer.reader.impl;

import com.anabol.ioccontainer.reader.impl.sax.ContextHandler;
import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.reader.BeanDefinitionReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    @Override
    public List<BeanDefinition> getBeanDefinitionList(String filePath) {
        List<BeanDefinition> beanDefinitions = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ContextHandler handler = new ContextHandler();
            saxParser.parse(new File(filePath), handler);
            beanDefinitions = handler.getBeanDefinitionList();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }
}



