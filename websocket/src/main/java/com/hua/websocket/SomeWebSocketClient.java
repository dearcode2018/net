/**
  * @filename SomeWebSocketClient.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

/**
 * @type SomeWebSocketClient
 * @description 
 * @author qianye.zheng
 */
public class SomeWebSocketClient extends WebSocketClient
{

	/**
	 * @description 构造方法
	 * @param address
	 * @author qianye.zheng
	 */
	public SomeWebSocketClient(final URI uri)
	{
		super(uri);
	}

	/**
	 * @description 
	 * @param handshakedata
	 * @author qianye.zheng
	 */
	@Override
	public void onOpen(ServerHandshake handshakedata)
	{
		System.out.println("SomeWebSocketClient.onOpen()");
	}

	/**
	 * @description 
	 * @param message
	 * @author qianye.zheng
	 */
	@Override
	public void onMessage(String message)
	{
		System.out.println("SomeWebSocketClient.onMessage(), message = " + message);
	}

	/**
	 * @description 
	 * @param code
	 * @param reason
	 * @param remote
	 * @author qianye.zheng
	 */
	@Override
	public void onClose(int code, String reason, boolean remote)
	{
		System.out.println("SomeWebSocketClient.onClose()");
	}

	/**
	 * @description 
	 * @param ex
	 * @author qianye.zheng
	 */
	@Override
	public void onError(Exception ex)
	{
		System.out.println("SomeWebSocketClient.onError()");
	}

}
