package com.esoo.mq.server.message;

import com.alibaba.fastjson.JSON;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.Result.SooResult;

import java.io.RandomAccessFile;

public class MessageFile {
    private String topic;
    private Long offset;
    private RandomAccessFile indexFile = null ;
    private RandomAccessFile dataFile = null ;

    public SooResult<Message,Message> appendMsg(Message in){
        SooResult<Message,Message> result = new SooResult();
        try {
            synchronized (in.getTopic()) {
                String lastLine = readLastLine(indexFile, null);

                int lastOffset = 1;
                long lastindex = lastindex = writeEndLine(dataFile, in.getBody());

                if (lastLine != null && !lastLine.equals("")) {
                    String index[] = lastLine.split(":");
                    lastOffset = Integer.valueOf(index[0]);
                    lastOffset = lastOffset + 1;
                }
                String insertMsgIndex = lastOffset + ":" + lastindex + "\t\n";
                writeEndLine(indexFile, insertMsgIndex.getBytes());
                result.setResult(in);
                result.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(null);
            result.setSuccess(false);
            result.setException(e);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    public SooResult<Message,Message>readMsg(Message in){

        SooResult<Message,Message> result = new SooResult();
        try {
            synchronized (in.getTopic()) {
                int seekIn = 0;
                int bodySize = 0;
                indexFile.seek(0);
                String indesMap=null;
                while ((indesMap = indexFile.readLine())!=null){
                    String index[] = indesMap.split(":");
                    int inNum = Integer.valueOf(String.valueOf(index[0]).trim());
                    int off = Integer.valueOf(String.valueOf(index[1]).trim());
                    if (inNum == in.getOffset()) {
                        seekIn = off;
                    }
                    if (inNum == (in.getOffset() + 1)) {
                        bodySize = off - seekIn;
                    }
                }
                if (bodySize == 0) {
                    result.setResult(null);
                    result.setSuccess(false);
                    result.setMsg("offset is end");
                    return result;
                }
                dataFile.seek(seekIn);

                byte[] b = new byte[bodySize];
                dataFile.read(b);
                in.setBody(b);

                result.setResult(in);
                result.setSuccess(true);
                System.out.println(" READ MSG IS: "+JSON.toJSONString(result));
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setResult(null);
            result.setSuccess(false);
            result.setException(e);
            result.setMsg(e.getMessage());
        }
        return result;

    }

    public static long writeEndLine(RandomAccessFile file, byte[] msg)
            throws Exception {
        // 文件长度，字节数
        long fileLength = file.length();
        // 将写文件指针移到文件尾。
        file.seek(fileLength);
        file.write(msg);
        return file.getFilePointer();

    }

    public static String readLastLine(RandomAccessFile file, String charset) throws Exception {

        long len = file.length();
        if (len == 0L) {
            return "";
        } else {
            long pos = len - 1;
            while (pos > 0) {
                pos--;
                file.seek(pos);
                if (file.readByte() == '\n') {
                    break;
                }
            }
            if (pos == 0) {
                file.seek(0);
            }
            byte[] bytes = new byte[(int) (len - pos)];
            file.read(bytes);
            if (charset == null) {
                return new String(bytes);
            } else {
                return new String(bytes, charset);
            }
        }

    }

    public static String readByOffset(RandomAccessFile file, String charset) throws Exception {

        return null;
    }



    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public RandomAccessFile getIndexFile() {
        return indexFile;
    }

    public void setIndexFile(RandomAccessFile indexFile) {
        this.indexFile = indexFile;
    }

    public RandomAccessFile getDataFile() {
        return dataFile;
    }

    public void setDataFile(RandomAccessFile dataFile) {
        this.dataFile = dataFile;
    }
}
