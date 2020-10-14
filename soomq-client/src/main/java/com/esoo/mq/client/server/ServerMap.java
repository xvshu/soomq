package com.esoo.mq.client.server;

import com.esoo.mq.client.connection.ConnectionManager;

import java.util.HashMap;

public class ServerMap {
    static{
        Runtime.getRuntime().addShutdownHook(new Thread("doveServer-shutdown-hook") {
            @Override
            public void run() {
                ConnectionManager.shutdown();
            }
        });
    }
    public static HashMap<String,Server> TopicServer=new HashMap<>();
    public static void addTopicServer(String serverIp,
                                      Integer serverPort, String topic){
        Server server=new Server(serverIp,serverPort,topic);
        TopicServer.put(topic,server);
    }
}
