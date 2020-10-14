package com.xs.test.producer;

import com.esoo.mq.client.consumer.Consumer;
import com.esoo.mq.client.producer.Producer;
import com.esoo.mq.client.server.ServerMap;
import com.esoo.mq.common.Message;

public class ConsumerTest {

    private static String ip="127.0.0.1";
    private static Integer port=9870;
    public static void main(String[] args) {
        Message msg = new Message();
        msg.setTopic("tp_test1");
        ServerMap.addTopicServer(ip,port,msg.getTopic());
        msg.setOffset(0);
        for(int i=0;i<1;i++){
            Consumer.readMsg(msg);
        }

    }
}
