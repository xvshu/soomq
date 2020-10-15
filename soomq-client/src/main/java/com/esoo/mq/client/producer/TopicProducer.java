package com.esoo.mq.client.producer;

import com.esoo.mq.client.connection.ConnectionManager;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.ProcessorType;
import io.netty.channel.Channel;

import java.util.concurrent.*;

public class TopicProducer {
    private  String serverIp;
    private  Integer serverPort;
    private  String topic;
    private  int threadNum=1;
    private ExecutorService es ;
    public  TopicProducer(String serverIp,Integer serverPort,String topic,int threadNum){
        this.serverIp=serverIp;
        this.serverPort=serverPort;
        this.topic=topic;
        if(threadNum<=0||threadNum>=10){
            threadNum=1;
        }
        this.threadNum=threadNum;
        es = new ThreadPoolExecutor(0,threadNum,60L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(200));
    }

    public void sengMsg(Message msg){
        es.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Channel channel = ConnectionManager.get(serverIp, serverPort);
                    ProcessorCommand command = new ProcessorCommand();
                    command.setResult(msg);
                    command.setType(ProcessorType.SendMessage.getType());
                    channel.writeAndFlush(command);
                }catch (Exception ex){

                }
            }
        });
    }
}
