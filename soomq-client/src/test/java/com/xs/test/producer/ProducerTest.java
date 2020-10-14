package com.xs.test.producer;

import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.client.producer.Producer;
import com.esoo.mq.client.producer.TopicProducerFactory;
import com.esoo.mq.common.Message;

public class ProducerTest {

    private static String ip="127.0.0.1";
    private static Integer port=9870;
    public static void main(String[] args) {
        Message msg = new Message();
        msg.setTopic("tp_test1");
        Producer.addTopicServer(ip,port,msg.getTopic());

        for(int i=0;i<100;i++){
            msg.setBody((" ["+i+"] hello soomq ").getBytes());
            Producer.sengMsg(msg);
        }

    }
}
