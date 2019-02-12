package com.anabol.ioccontainer.exception;

public class BeanInitiationException extends RuntimeException{

    public BeanInitiationException() {
        super();
    }

    public BeanInitiationException(String message) {
        super(message);
    }

    public BeanInitiationException(Throwable cause) {
        super(cause);
    }

    public BeanInitiationException(String message, Throwable cause) {
        super(message, cause);
    }
}
