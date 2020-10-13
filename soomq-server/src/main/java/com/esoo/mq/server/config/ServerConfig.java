package com.esoo.mq.server.config;

import java.util.Properties;

public class ServerConfig {
    private String serverIp;
    private String serverIpKey="server.ip";
    private int serverId;
    private String serverIdKey="server.id";
    private String dataPath;
    private String dataPathKey="data.path";

    public ServerConfig(Properties properties ){
        serverIp = getPropertiesFromFile(properties,serverIpKey,null);
        serverId = Integer.valueOf(getPropertiesFromFile(properties,serverIdKey,"0"));
        dataPath = getPropertiesFromFile(properties,dataPathKey,"./data/path");
    }

    private String getPropertiesFromFile(Properties properties,String key,String defaultValue){
        if(properties==null
                || !properties.contains(key)
                ||key==null
                ||properties.getProperty(key)==null){
            return defaultValue;
        }
        return properties.getProperty(key,defaultValue);
    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }
}
