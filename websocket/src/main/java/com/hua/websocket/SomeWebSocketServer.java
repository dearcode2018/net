/**
  * @filename SomeWebSocketServer.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 * @type SomeWebSocketServer
 * @description 
 * @author qianye.zheng
 */
public class SomeWebSocketServer extends WebSocketServer
{
	
	/**
	 * 1. 客户端请求建立WebSocket连接
	 * 
	 * 2.服务端维持WebSocket队列，和用户或游客建立对应关系
	 * 
	 * 3.客户端持有的WebSocket对象发送消息给服务端
	 * 
	 * 4.服务端从方法参数或会话队列中获取WebSocket对象发送消息给客户端
	 * 
	 * 
	 */

	/**
	 * @description 构造方法
	 * @param port
	 * @author qianye.zheng
	 */
	public SomeWebSocketServer(final int port)
	{
		super(new InetSocketAddress(port));
	}
	
	/**
	 * @description 
	 * @author qianye.zheng
	 */
	@Override
	public void onStart()
	{
		System.out.println("SomeWebSocketServer.onStart()");
	}
	
	/**
	 * @description 
	 * @param conn
	 * @param handshake 握手
	 * @author qianye.zheng
	 */
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake)
	{
		System.out.println("SomeWebSocketServer.onOpen()");
	}

	/**
	 * @description 
	 * @param conn
	 * @param code
	 * @param reason
	 * @param remote
	 * @author qianye.zheng
	 */
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote)
	{
		System.out.println("SomeWebSocketServer.onClose()");
	}

	/**
	 * @description 
	 * @param conn
	 * @param message
	 * @author qianye.zheng
	 */
	@Override
	public void onMessage(WebSocket conn, String message)
	{
		System.out.println("SomeWebSocketServer.onMessage(), message = " + message);
		// 发送消息给客户端
		conn.send("你好，I am server!");
	}

	/**
	 * @description 
	 * @param conn
	 * @param ex
	 * @author qianye.zheng
	 */
	@Override
	public void onError(WebSocket conn, Exception ex)
	{
		System.out.println("SomeWebSocketServer.onError()");
	}

}
