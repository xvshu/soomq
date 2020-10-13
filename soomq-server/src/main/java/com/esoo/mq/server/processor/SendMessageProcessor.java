package com.esoo.mq.server.processor;

import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.Result.SooResult;
import com.esoo.mq.server.message.MessageFile;
import com.esoo.mq.server.message.MessageFileFactory;

public class SendMessageProcessor implements Processor<Message,Message> {

    @Override
    public SooResult<Message,Message> handle(ProcessorCommand<Message,Message> task) {
        Message msg = task.getParameter();
        SooResult<Object,MessageFile> msgFileResult = MessageFileFactory.getTopicFile(msg.getTopic());
        SooResult<Message,Message> appendMsgResult = msgFileResult.getResult().appendMsg(msg);
        return appendMsgResult;
    }


}
