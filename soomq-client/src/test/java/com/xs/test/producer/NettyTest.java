package com.xs.test.producer;

import com.esoo.mq.client.connection.SooMqClientHandler;
import com.esoo.mq.common.Message;
import com.esoo.mq.common.ProcessorCommand;
import com.esoo.mq.common.ProcessorType;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Scanner;

public class NettyTest {
    public static void main(String[] args) {
        Bootstrap b = new Bootstrap();
        //创建reactor 线程组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {

            //1 设置reactor 线程组
            b.group(workerLoopGroup);
            //2 设置nio类型的channel
            b.channel(NioSocketChannel.class);
            //3 设置监听端口
            b.remoteAddress(ProducerTest.ip, ProducerTest.port);
            //4 设置通道的参数
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            //5 装配子通道流水线
            b.handler(new ChannelInitializer<SocketChannel>() {
                //有连接到达时会创建一个channel
                @Override
                protected void initChannel(io.netty.channel.socket.SocketChannel ch) throws Exception {
                    // pipeline管理子通道channel中的Handler
                    // 向子channel流水线添加一个handler处理器
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                            ClassResolvers.cacheDisabled(null)));
                    ch.pipeline().addLast(new SooMqClientHandler());
                }
            });
            ChannelFuture f = b.connect();
            f.addListener((ChannelFuture futureListener) ->
            {
                if (futureListener.isSuccess()) {
                    System.out.println("Client客户端连接成功!");

                } else {
                    System.out.println("Client客户端连接失败!");
                }

            });

            // 阻塞,直到连接完成
            f.sync();
            Channel channel = f.channel();

            Scanner scanner = new Scanner(System.in);
            Print.tcfo("请输入发送内容:");

            while (scanner.hasNext()) {
                //获取输入的内容
                String next = scanner.next();
                Message msg = new Message();
                msg.setTopic(ProducerTest.topic);

                msg.setBody(next.getBytes());

                ProcessorCommand command = new ProcessorCommand();
                command.setResult(msg);
                command.setType(ProcessorType.SendMessage.getType());

                channel.writeAndFlush(command);
            }

        }catch (Exception ex){
            ex.printStackTrace();

        }finally {
            // 优雅关闭EventLoopGroup，
            // 释放掉所有资源包括创建的线程
            workerLoopGroup.shutdownGracefully();
        }

    }
}
