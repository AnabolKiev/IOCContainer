package com.anabol.ioccontainer.reader.impl;

import com.anabol.ioccontainer.reader.exception.BeanDefinitionParserException;
import com.anabol.ioccontainer.reader.impl.sax.ContextHandler;
import com.anabol.ioccontainer.entity.BeanDefinition;
import com.anabol.ioccontainer.reader.BeanDefinitionReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.List;

public class XmlBeanDefinitionReader implements BeanDefinitionReader {

    @Override
    public List<BeanDefinition> getBeanDefinitionList(String filePath) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filePath))) {
            return getBeanDefinitions(bufferedInputStream);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new BeanDefinitionParserException("Cannot read bean definitions from: " + filePath, e);
        }
    }

    private List<BeanDefinition> getBeanDefinitions(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        ContextHandler handler = new ContextHandler();
        saxParser.parse(inputStream, handler);
        return handler.getBeanDefinitionList();
    }
}



