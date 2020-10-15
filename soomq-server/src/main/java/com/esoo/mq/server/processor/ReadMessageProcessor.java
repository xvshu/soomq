package com.esoo.mq.server.processor;

import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.server.message.MessageFile;
import com.esoo.mq.server.message.MessageFileFactory;

public class ReadMessageProcessor implements Processor<Message,Message> {

    @Override
    public ProcessorCommand handle(ProcessorCommand task) {
        Message msg = task.getResult();
        MessageFile file = MessageFileFactory.getTopicFile(msg.getTopic());
        task = file.readMsg(task);
        return task;
    }


}
