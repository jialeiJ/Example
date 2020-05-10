package com.vienna.jaray.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

//客户端业务处理类
public class NettyClientHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		ctx.channel().attr(AttributeKey.valueOf("Attribute_key")).set(msg);
		ctx.close();
	}
	
}