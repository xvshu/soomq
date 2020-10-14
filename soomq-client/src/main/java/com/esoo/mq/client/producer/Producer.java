package com.esoo.mq.client.producer;

import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.client.server.Server;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.Result.SooResult;
import com.esoo.mq.common.exception.SooMQException;

import java.util.HashMap;

public class Producer {
    private static HashMap<String,Server> topicServer=new HashMap<>();
    static{
        Runtime.getRuntime().addShutdownHook(new Thread("doveServer-shutdown-hook") {
            @Override
            public void run() {
                ConnectionManager.shutdown();
            }
        });
    }

    public static void sengMsg(Message msg){
        Server server = topicServer.get(msg.getTopic());
        if(server==null){
            throw new SooMQException("this topic["+msg.getTopic()+"] have no server");
        }
        TopicProducerFactory.get(server.getIp(),server.getPort(),msg.getTopic()).sengMsg(msg);
    }

    public static void addTopicServer(String serverIp,
                                      Integer serverPort, String topic){
        Server server=new Server(serverIp,serverPort,topic);
        topicServer.put(topic,server);
    }
}
