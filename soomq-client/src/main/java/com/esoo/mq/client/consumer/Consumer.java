package com.esoo.mq.client.consumer;


import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.client.server.Server;
import com.esoo.mq.client.server.ServerMap;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.ProcessorType;
import com.esoo.mq.common.exception.SooMQException;
import io.netty.channel.Channel;

public class Consumer {
    public static void readMsg(Message msg){
        try {
            Server server = ServerMap.TopicServer.get(msg.getTopic());
            if (server == null) {
                throw new SooMQException("this topic[" + msg.getTopic() + "] have no server");
            }
            Channel channel = ConnectionManager.get(server.getIp(), server.getPort());
            ProcessorCommand command = new ProcessorCommand();
            command.setResult(msg);
            command.setType(ProcessorType.ReadMessage.getType());
            channel.writeAndFlush(command);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
