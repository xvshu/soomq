package com.esoo.mq.common;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Map;

public class Message implements Serializable {
    private String topic;

    private Map<String, String> properties;
    private byte[] body;
    private Integer offset;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

}
