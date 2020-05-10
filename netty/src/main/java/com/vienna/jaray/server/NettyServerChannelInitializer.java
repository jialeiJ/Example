package com.vienna.jaray.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 解码编码
		ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
		ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));

		ch.pipeline().addLast(new NettyServerHandler());
	}

}
