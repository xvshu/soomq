package com.esoo.mq.client.server;

public class Server {
    private String ip;
    private Integer port;
    private String topic;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Server(String ip, Integer port, String topic) {
        this.ip = ip;
        this.port = port;
        this.topic = topic;
    }
}
