package com.esoo.mq.server.processor;

import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.server.message.MessageFile;
import com.esoo.mq.server.message.MessageFileFactory;

public class SendMessageProcessor implements Processor<Message,Message> {

    @Override
    public ProcessorCommand handle(ProcessorCommand task) {
        MessageFile file = MessageFileFactory.getTopicFile(task.getResult().getTopic());
        task = file.appendMsg(task);
        return task;
    }


}
