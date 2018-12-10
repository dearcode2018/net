/**
  * @filename SomeMessageHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * @type SomeMessageHandler
 * @description 
 * @author qianye.zheng
 */
//@Component
public class SomeMessageHandler2 implements WebSocketHandler
{

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception
	{
	}

	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception
	{
	}

	/**
	 * @description 
	 * @param session
	 * @param exception
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception
	{
	}

	/**
	 * @description 
	 * @param session
	 * @param closeStatus
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception
	{
	}

	/**
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public boolean supportsPartialMessages()
	{
		return true;
	}
	
	
}
