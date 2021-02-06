/**
  * @filename DiscardServer.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.server;

import com.hua.discard.DiscardServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @type DiscardServer
 * @description 
 * @author qianye.zheng
 */
public class DiscardServer
{
	
	private int port;
	
	/**
	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public DiscardServer(final int port)
	{
		this.port = port;
	}
	
	/**
	 * 
	 * @description 
	 * @author qianye.zheng
	 */
	public void run() throws Exception
	{
		EventLoopGroup boosGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(boosGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>()
			{
				/**
				 * @description 
				 * @param ch
				 * @throws Exception
				 * @author qianye.zheng
				 */
				@Override
				protected void initChannel(SocketChannel ch) throws Exception
				{
					ch.pipeline().addLast(new DiscardServerHandler());
				}
			}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture future = bootstrap.bind(port).sync();
			future.channel().closeFuture().sync();
		} finally
		{
			// TODO: handle finally clause
			workerGroup.shutdownGracefully();
			boosGroup.shutdownGracefully();
		}
	}
	
}
