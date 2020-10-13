package com.esoo.mq.common.record;

import com.esoo.mq.common.Info;

public class Message<V> extends Info {

    private String topic;
    private V body;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public V getBody() {
        return body;
    }

    public void setBody(V body) {
        this.body = body;
    }

}
