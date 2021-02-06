/**
  * @filename MinaClientHandler.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.client;

import java.util.Date;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

 /**
 * @type MinaClientHandler
 * @description  
 * @author qianye.zheng
 */
public final class MinaClientHandler extends IoHandlerAdapter {

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("client-session创建，建立连接");
	}

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("client-server session打开");
	}

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("client-server session关闭");
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
			throws Exception {
		System.out.println("client-session进入空闲状态...");
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
			throws Exception {
		cause.printStackTrace();
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
			throws Exception {
		System.out.println("client 接收到消息: " + message.toString());
	}

	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("sessionId = " + session.getId());
		System.out.println("client消息已发送");
	}

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void inputClosed(IoSession session) throws Exception {
		//System.out.println("client-input 已关闭");
	}
}
