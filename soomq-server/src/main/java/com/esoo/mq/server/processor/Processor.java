package com.esoo.mq.server.processor;

import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.Result.SooResult;

public interface Processor<P,V> {
    public SooResult<P,V> handle(ProcessorCommand<P,V> task);
}
