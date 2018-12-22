/**
  * @filename DiscardServerHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * @type DiscardServerHandler
 * @description 
 * @author qianye.zheng
 */
public class DiscardServerHandler extends ChannelHandlerAdapter
{
	
	/**
	 * @description 
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception
	{
		super.channelRead(ctx, msg);
		ByteBuf in = (ByteBuf) msg;
		try
		{
			while (in.isReadable())
			{
				System.out.println(((char) in.readByte()));
			}
		} finally
		{
			 ReferenceCountUtil.release(msg); 
			// TODO: handle finally clause
		}
		//
		
	}
	
	/**
	 * @description 
	 * @param ctx
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		super.channelActive(ctx);
		
	}
	
	/**
	 * @description 
	 * @param ctx
	 * @param cause
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception
	{
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
	
}
