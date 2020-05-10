package com.vienna.jaray.server;

import java.net.InetSocketAddress;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/*服务端*/
@Service
public class NettyServer implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    /**
     * 	启动服务
     */
    public ChannelFuture start(InetSocketAddress address) {

        ChannelFuture f = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            f = b.bind(address).syncUninterruptibly();
            channel = f.channel();
        } catch (Exception e) {
        	logger.error("Netty start error:", e);
        } finally {
            if (f != null && f.isSuccess()) {
            	logger.info("Netty server listening " + address.getHostName() + " on port " + address.getPort() + " and ready for connections...");
            } else {
            	logger.error("Netty server start up Error!");
            }
        }

        return f;
    }

    public void destroy() {
    	logger.info("Shutdown Netty Server...");
        if(channel != null) { channel.close();}
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        logger.info("Shutdown Netty Server Success!");
    }

    /**
     * 启动
     * @PostConstruct 不能使用该注解启动，会阻塞导致项目无法完成启动
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        String ip = "127.0.0.1";
        int port = 8765;
        NettyServer nettyServer = new NettyServer();
        InetSocketAddress address = new InetSocketAddress(ip, port);
        ChannelFuture future = nettyServer.start(address);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                nettyServer.destroy();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}