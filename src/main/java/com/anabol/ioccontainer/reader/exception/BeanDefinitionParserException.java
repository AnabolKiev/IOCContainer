package com.anabol.ioccontainer.reader.exception;

public class BeanDefinitionParserException extends RuntimeException {

    public BeanDefinitionParserException() {
        super();
    }

    public BeanDefinitionParserException(String message) {
        super(message);
    }

    public BeanDefinitionParserException(Throwable cause) {
        super(cause);
    }

    public BeanDefinitionParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
