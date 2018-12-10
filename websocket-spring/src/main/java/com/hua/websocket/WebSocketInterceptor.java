/**
  * @filename WebSocketInterceptor.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @type WebSocketInterceptor
 * @description 
 * @author qianye.zheng
 */
public class WebSocketInterceptor extends HttpSessionHandshakeInterceptor
{
	
	/**
	 * @description 
	 * @param request
	 * @param response
	 * @param wsHandler
	 * @param attributes
	 * @return
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception
	{
		System.out.println("WebSocketInterceptor.beforeHandshake()");
		//return super.beforeHandshake(request, response, wsHandler, attributes);
		return true;
	}
	
	/**
	 * @description 
	 * @param request
	 * @param response
	 * @param wsHandler
	 * @param ex
	 * @author qianye.zheng
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex)
	{
		System.out.println("WebSocketInterceptor.afterHandshake()");
		//super.afterHandshake(request, response, wsHandler, ex);
	}
}
