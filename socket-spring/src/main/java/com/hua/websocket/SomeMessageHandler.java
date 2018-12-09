/**
  * @filename SomeMessageHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @type SomeMessageHandler
 * @description 
 * @author qianye.zheng
 */
@Component
public class SomeMessageHandler extends TextWebSocketHandler
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
		super.afterConnectionEstablished(session);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @author qianye.zheng
	 */
	@Override
	protected void handleBinaryMessage(WebSocketSession session,
			BinaryMessage message)
	{
		super.handleBinaryMessage(session, message);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session,
			TextMessage message) throws Exception
	{
		super.handleTextMessage(session, message);
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
		super.handleMessage(session, message);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	protected void handlePongMessage(WebSocketSession session,
			PongMessage message) throws Exception
	{
		super.handlePongMessage(session, message);
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
		super.handleTransportError(session, exception);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param status
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus status) throws Exception
	{
		super.afterConnectionClosed(session, status);
	}
	
	/**
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public boolean supportsPartialMessages()
	{
		return super.supportsPartialMessages();
	}
}
