/**
  * @filename EchoEndpoint.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * @type EchoEndpoint
 * @description 
 * @author qianye.zheng
 */
/*
 * 通过容器来实现
 * 例如 Tomcat Jetty WebLogci
 */
@ServerEndpoint("/echo/v1")
public class EchoEndpoint
{
	
	
	/**
	 * 
	 */
	
	/** 无参构造方法
 	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public EchoEndpoint()
	{
	}
	
	/**
	 * 
	 * @description 
	 * @param session
	 * @author qianye.zheng
	 */
	@OnOpen
	public void onOpen(final Session session)
	{
		
	}
	
	/**
	 * 
	 * @description 
	 * @param message
	 * @author qianye.zheng
	 */
	@OnMessage
	public void onMessage(final String message)
	{
		
	}
	
	
	/**
	 * 
	 * @description 
	 * @param message
	 * @author qianye.zheng
	 */
/*	@OnMessage
	public void message(final byte[] message)
	{
		
	}*/
	
	/**
	 * 
	 * @description 
	 * @param t
	 * @author qianye.zheng
	 */
	@OnError
	public void onError(final Throwable t)
	{
		
	}
	
	/**
	 * 
	 * @description 
	 * @param session
	 * @param reason
	 * @author qianye.zheng
	 */
	@OnClose
	public void onClose(final Session session, final CloseReason reason)
	{
		
	}
	
}
