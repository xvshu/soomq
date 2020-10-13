package com.esoo.mq.common;

import java.io.Serializable;

public class ProcessorCommand<P,V> implements Serializable {
    private String type;
    private V result;
    private P parameter;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public V getResult() {
        return result;
    }

    public void setResult(V result) {
        this.result = result;
    }

    public P getParameter() {
        return parameter;
    }

    public void setParameter(P parameter) {
        this.parameter = parameter;
    }
}
