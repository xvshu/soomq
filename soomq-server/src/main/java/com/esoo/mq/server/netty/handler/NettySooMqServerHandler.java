package com.esoo.mq.server.netty.handler;


import com.alibaba.fastjson.JSON;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.server.processor.Processor;
import com.esoo.mq.server.processor.ProcessorFactory;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class NettySooMqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        try {
            ProcessorCommand command = (ProcessorCommand) msg;
            System.out.println("["+ctx.channel().remoteAddress()+"] msg:"+JSON.toJSONString(msg));
            Processor processor = ProcessorFactory.getProcessorInstantiate(command.getType());
            msg = processor.handle(command);
            ChannelFuture f = ctx.writeAndFlush(msg);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("msg ctx send");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }



}
