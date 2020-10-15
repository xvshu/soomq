package com.esoo.mq.server.processor;

import com.esoo.mq.common.ProcessorCommand;

public interface Processor<P,V> {
    public ProcessorCommand handle(ProcessorCommand task);
}
