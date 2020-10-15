package com.esoo.mq.client.connection;

import com.alibaba.fastjson.JSON;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.ProcessorType;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@ChannelHandler.Sharable
public class SooMqClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client re:"+JSON.toJSONString(msg));
        ProcessorCommand cm = (ProcessorCommand)msg;
        if(((ProcessorCommand) msg).getType().equals(ProcessorType.ReadMessage.getType())){
            String msgBody = new String(((ProcessorCommand) msg).getResult().getBody());
            System.out.println("msg body is ： "+msgBody);
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
