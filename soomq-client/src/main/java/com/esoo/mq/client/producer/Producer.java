package com.esoo.mq.client.producer;

import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.client.server.Server;
import com.esoo.mq.client.server.ServerMap;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.Result.SooResult;
import com.esoo.mq.common.exception.SooMQException;

import java.util.HashMap;

public class Producer {


    public static void sengMsg(Message msg){
        Server server = ServerMap.TopicServer.get(msg.getTopic());
        if(server==null){
            throw new SooMQException("this topic["+msg.getTopic()+"] have no server");
        }
        TopicProducerFactory.get(server.getIp(),server.getPort(),msg.getTopic()).sengMsg(msg);
    }


}
