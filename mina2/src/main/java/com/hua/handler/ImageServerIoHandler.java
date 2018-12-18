/**
  * @filename ImageServerIoHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 * @type ImageServerIoHandler
 * @description 
 * @author qianye.zheng
 */
public class ImageServerIoHandler extends IoHandlerAdapter
{
	
	private static final String characters = "mina rocks abcdefghijklmnopqrstuvwxyz0123456789";
	
	public static final String INDEX_KEY = ImageServerIoHandler.class.getName() + ".INDEX";
	
	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		super.sessionOpened(session);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param cause
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		super.exceptionCaught(session, cause);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		super.messageReceived(session, message);
	}
	
}
