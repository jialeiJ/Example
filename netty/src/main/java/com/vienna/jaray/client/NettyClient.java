package com.vienna.jaray.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/*客户端*/
@Component
public class NettyClient {
	private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

    private static Bootstrap b;
    private static ChannelFuture f;
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
	
    private void init() {
        try {
            log.info("init...");
            b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    // 解码编码
                    socketChannel.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                    socketChannel.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));

                    socketChannel.pipeline().addLast(new NettyClientHandler());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object startAndWrite(InetSocketAddress address, Object send) throws InterruptedException {
        init();
        Object object = null;
        try {
            f = b.connect(address).sync();
            // 传数据给服务端
            f.channel().writeAndFlush(send);
            f.channel().closeFuture().sync();
            object = f.channel().attr(AttributeKey.valueOf("Attribute_key")).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return object;
    }

//    public static void main(String[] args) {
//        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);
//        String message = "hello";
//        try {
//        	for (int i = 0; i < 100000; i++) {
//        		Object result = NettyClient.startAndWrite(address, message);
//        		log.info("....result：{}", result);
//			}
//
//            //Object result = NettyClient.startAndWrite(address, message);
//            //log.info("....result:" + result);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        } finally {
//            f.channel().close();
//            workerGroup.shutdownGracefully();
//            log.info("Closed client!");
//        }
//    }
}
