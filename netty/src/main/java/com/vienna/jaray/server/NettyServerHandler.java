package com.vienna.jaray.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 	服务端事务处理类
 * 	服务端业务逻辑处理类
 * @author Administrator
 *
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("server receive message：{}", msg);
        ctx.channel().writeAndFlush("yes server already accept your message" + msg);
        ctx.close();
	}
	
}