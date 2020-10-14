package com.esoo.mq.server;

import com.esoo.mq.server.config.ServerConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SooMQBootstrap {

    public static ServerConfig serverConfig;

    public static void main(String[] args) throws Exception {
        serverConfig=getServerConfig(args);
        SooMQServer server=new SooMQServer();
        server.start();
    }

    public static ServerConfig getServerConfig(String[] args)throws Exception{
        final String configFilePath = getConfigFilePath(args);
        return getConfig(configFilePath);
    }

    private static String getConfigFilePath(final String[] args) {
        if (args.length != 1) {
            return null;
        }
        return args[0];
    }

    private static ServerConfig getConfig(final String configFilePath) throws IOException {
        Properties properties = new Properties();
        if(configFilePath!=null){
            try{
                InputStream inputStream = new FileInputStream(new File(configFilePath));
                properties.load(inputStream);
            }catch (Exception ex){
                ex.printStackTrace();
                properties=null;
            }
        }
        return new ServerConfig(properties);
    }

}
