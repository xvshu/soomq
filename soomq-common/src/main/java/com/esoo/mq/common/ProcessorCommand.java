package com.esoo.mq.common;

import java.io.Serializable;

public class ProcessorCommand implements Serializable {
    private String type;
    private String exmsg;
    private Message result;
    private boolean isSuccess=false;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Message getResult() {
        return result;
    }

    public void setResult(Message result) {
        this.result = result;
    }

    public String getExmsg() {
        return exmsg;
    }

    public void setExmsg(String exmsg) {
        this.exmsg = exmsg;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
