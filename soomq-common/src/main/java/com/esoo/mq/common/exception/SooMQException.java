package com.esoo.mq.common.exception;

public class SooMQException extends RuntimeException {
    public SooMQException(){
        super();
    }
    public SooMQException(String message) {
        super(message);
    }
}
