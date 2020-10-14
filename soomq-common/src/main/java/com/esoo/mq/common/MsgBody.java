package com.esoo.mq.common;

import java.io.Serializable;

public class MsgBody implements Serializable {
    private int index;
    private Object body;
    private String md5;
}
