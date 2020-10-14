package com.esoo.mq.client.consumer;


import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.client.producer.TopicProducerFactory;
import com.esoo.mq.client.server.Server;
import com.esoo.mq.client.server.ServerMap;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.ProcessorType;
import com.esoo.mq.common.exception.SooMQException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public class Consumer {
    public static void readMsg(Message msg){
        Server server = ServerMap.TopicServer.get(msg.getTopic());
        if(server==null){
            throw new SooMQException("this topic["+msg.getTopic()+"] have no server");
        }
        Channel channel = ConnectionManager.get(server.getIp(), server.getPort());
        ProcessorCommand<Message, Message> command = new ProcessorCommand();
        command.setParameter(msg);
        command.setResult(msg);
        command.setType(ProcessorType.ReadMessage.getType());
        ChannelFuture future = channel.writeAndFlush(command);
        future.addListener((ChannelFuture futureListener) -> {
            futureListener.channel().alloc();
        });

    }

}
