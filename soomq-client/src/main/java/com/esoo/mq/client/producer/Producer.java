package com.esoo.mq.client.producer;

import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.Result.SooResult;

public class Producer {
    static{
        Runtime.getRuntime().addShutdownHook(new Thread("doveServer-shutdown-hook") {
            @Override
            public void run() {
                ConnectionManager.shutdown();
            }
        });
    }

    public static void sengMsg(String serverIp,
            Integer serverPort, Message msg){
        TopicProducerFactory.get(serverIp,serverPort,msg.getTopic()).sengMsg(msg);
    }
}
