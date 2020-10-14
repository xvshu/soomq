package com.esoo.mq.common;

public enum ProcessorType {
    SendMessage("sendmsg"),ReadMessage("readmsg");

    private String type;

    ProcessorType(String type){
        this.type=type;
    }


    public String getType() {
        return type;
    }


}
