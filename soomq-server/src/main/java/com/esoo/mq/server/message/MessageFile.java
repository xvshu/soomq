package com.esoo.mq.server.message;

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
            String lastLine = readLastLine(indexFile,null);

            int lastOffset=1;
            long lastindex=lastindex = writeEndLine(dataFile,in.getBody());
            if(lastLine!=null && !lastLine.equals("")){
                String index[] = lastLine.split(":");
                lastOffset = Integer.valueOf(index[0]);
                lastOffset=lastOffset+1;
            }
            String insertMsgIndex=lastOffset+":"+lastindex+"\t\n";
            writeEndLine(indexFile,insertMsgIndex.getBytes());
            result.setResult(in);
            result.setSuccess(true);
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
