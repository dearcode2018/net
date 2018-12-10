/**
  * @filename WebSocketConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @type WebSocketConfig
 * @description 
 * @author qianye.zheng
 */
/*
 * 采用这种方式不可以
 */
@Configuration
//注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
@EnableWebSocketMessageBroker
public class WebSocketConfig2 implements WebSocketMessageBrokerConfigurer
{

	/**
	 * @description 
	 * @param registry
	 * @author qianye.zheng
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry)
	{
		System.out.println("WebSocketConfig2.registerStompEndpoints()");
		registry.addEndpoint("/some3").withSockJS();
		WebSocketMessageBrokerConfigurer.super.registerStompEndpoints(registry);
	}
	


}
