package com.esoo.mq.server.message;

import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.Utils;
import com.esoo.mq.server.SooMQBootstrap;

import java.io.RandomAccessFile;
import java.util.HashMap;

public class MessageFileFactory {

    private static final String dataPath = SooMQBootstrap.serverConfig.getDataPath();

    private static HashMap<String,MessageFile> filesMap = new HashMap();
    public static MessageFile getTopicFile (String topic){
        MessageFile result = null;
        synchronized(topic) {
            if (!filesMap.containsKey(topic)) {
                try {
                    String dataFilePath = dataPath + "/" + topic + ".data";
                    String indexFilePath = dataPath + "/" + topic + ".index";
                    MessageFile msgF = new MessageFile();

                    Utils.createFile(dataFilePath);
                    RandomAccessFile dataFile = new RandomAccessFile(dataFilePath, "rw");
                    Utils.createFile(indexFilePath);
                    RandomAccessFile indexFile = new RandomAccessFile(indexFilePath, "rw");
                    msgF.setDataFile(dataFile);
                    msgF.setIndexFile(indexFile);
                    filesMap.put(topic, msgF);
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
            result=filesMap.get(topic);

        }
        return result;
    }

}
