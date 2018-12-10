/**
  * @filename WebSocketConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @type WebSocketConfig
 * @description 
 * @author qianye.zheng
 */
@Configuration
@EnableWebSocket
//@EnableWebMvc
public class WebSocketConfig implements WebSocketConfigurer
{

	@Resource
	private SomeMessageHandler someMessageHandler;
	
	/**
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
	{
		/*
		 * 指定处理器的uri
		 * ws://ip:port/context-path/some/message
		 */
		registry.addHandler(someMessageHandler, "/some/message").addInterceptors(new WebSocketInterceptor());
	}
	


}
