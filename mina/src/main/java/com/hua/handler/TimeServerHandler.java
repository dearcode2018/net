/**
  * @filename TimeServerHandler.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

 /**
 * @type TimeServerHandler
 * @description  时间服务器
 * @author qianye.zheng
 */
public final class TimeServerHandler extends IoHandlerAdapter {

	/**
	 * @description 异常捕获到
	 * @param session
	 * @param cause
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		cause.printStackTrace();
	}
	
	/**
	 * @description 消息收到
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String content = message.toString();
		if ("quit".equalsIgnoreCase(content.trim()))
		{
			System.out.println("close.");
			// close(boolean immediately); 是否立即关闭
			//session.closeNow();
			
			return;
		}
		System.out.println("message = " + content);
		Date date = new Date();
		session.write(date.toString());
		System.out.println("message written.");
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception
	{
		System.out.println("TimeServerHandler.messageSent()");
		super.messageSent(session, message);
	}
	
	/**
	 * @description 会话空闲
	 * @param session
	 * @param status
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		//super.sessionIdle(session, status);
		System.out.println("IDLE " + session.getIdleCount(status));
	}
}
