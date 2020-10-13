package com.esoo.mq.server.netty.handler;


import com.esoo.mq.common.ObjectAndByte;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.server.processor.Processor;
import com.esoo.mq.server.processor.ProcessorFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;

@ChannelHandler.Sharable
public class NettySooMqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            ProcessorCommand command = (ProcessorCommand) msg;
            Processor processor = ProcessorFactory.getProcessorInstantiate(command.getType());
            processor.handle(command);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

}
