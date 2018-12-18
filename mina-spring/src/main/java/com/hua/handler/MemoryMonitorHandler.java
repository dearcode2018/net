/**
  * @filename MemoryMonitorHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 * @type MemoryMonitorHandler
 * @description 
 * @author qianye.zheng
 */
public class MemoryMonitorHandler extends IoHandlerAdapter
{
	
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
		System.out.println("MemoryMonitorHandler.exceptionCaught()");
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
		System.out.println("MemoryMonitorHandler.messageReceived(), message = " + message);
		super.messageReceived(session, message);
	}
	
	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception
	{
		System.out.println("MemoryMonitorHandler.sessionClosed()");
		super.sessionClosed(session);
	}
	
	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception
	{
		System.out.println("MemoryMonitorHandler.sessionCreated()");
		super.sessionCreated(session);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param status
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception
	{
		System.out.println("MemoryMonitorHandler.sessionIdle()");
		super.sessionIdle(session, status);
	}
	
	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		System.out.println("MemoryMonitorHandler.sessionOpened()");
		super.sessionOpened(session);
	}
	
}
