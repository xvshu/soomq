package com.esoo.mq.client.producer;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

public class TopicProducerFactory {
    private static HashMap<String,TopicProducer> producerMap=new HashMap<>();

    public static TopicProducer get(String ip,Integer port,String topic){
        TopicProducer topicProducer=null;
        String url = ip+":"+port+"#"+topic;
        synchronized (url) {
            if (!producerMap.containsKey(url)) {
                topicProducer=new TopicProducer(ip,port,topic,1);
                producerMap.put(url,topicProducer);
            }else{
                topicProducer= producerMap.get(url);
            }
        }
        return topicProducer;
    }

}
