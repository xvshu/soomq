package com.esoo.mq.client.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    private static HashMap<String,Channel> channelMap=new HashMap<>();

    public static Channel get(String ip,Integer port){
        Channel channel=null;
        String url = ip+":"+port;
        synchronized (url) {
            if (!channelMap.containsKey(url)) {
                channel=createChannel(ip,port);
                channelMap.put(url,channel);
            }else{
                channel= channelMap.get(url);
            }
        }
        return channel;
    }

    private static Channel createChannel(String ip,Integer port){
        Bootstrap b = new Bootstrap();
        //创建reactor 线程组
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        Channel channel=null;
        try {

            //1 设置reactor 线程组
            b.group(workerLoopGroup);
            //2 设置nio类型的channel
            b.channel(NioSocketChannel.class);
            //3 设置监听端口
            b.remoteAddress(ip, port);
            //4 设置通道的参数
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            //5 装配子通道流水线
            b.handler(new ChannelInitializer<io.netty.channel.socket.SocketChannel>() {
                //有连接到达时会创建一个channel
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
                    System.out.println("EchoClient客户端连接成功!");

                } else {
                    System.out.println("EchoClient客户端连接失败!");
                }

            });

            // 阻塞,直到连接完成
            f.sync();
            channel = f.channel();
        }catch (Exception ex){
            ex.printStackTrace();
            channel=null;
        }
        return channel;
    }

    public static void shutdown(){
        for(Map.Entry<String, Channel> entry : channelMap.entrySet()){
            try {
                Channel channel = entry.getValue();
                if (channel != null && channel.isOpen()) {
                    channel.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
