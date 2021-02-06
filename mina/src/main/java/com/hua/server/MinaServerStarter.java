/**
  * @filename MinaServerStarter.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Test;

import com.hua.constant.Constant;
import com.hua.constant.MinaConstant;
import com.hua.handler.TimeServerHandler;

 /**
 * @type MinaServerStarter
 * @description  
 * @author qianye.zheng
 */
public final class MinaServerStarter {

	/**
	 * 
	 * @description
	 * @author qianye.zheng
	 */
	public static final void start()
	{
		/*
		 * IO 接收器，创建一个非阻塞服务端Socket
		 * 服务端用 IOAcceptor，客户端用IOConnector
		 */
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		// 添加 过滤器
		// 日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 编码过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName(Constant.CHART_SET_UTF_8))));
		// 设置 IO处理器
		acceptor.setHandler(new MinaServerHandler());
		// 设置 读缓存的大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 设置 空闲时间
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		/**
		 * server 监听参数
		 */
		acceptor.setDefaultLocalAddress(new InetSocketAddress(MinaConstant.HOST, MinaConstant.PORT));
		// 绑定
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 
	 * @description 示例
	 * @author qianye.zheng
	 */
	public static final void example()
	{
		// IO 接收器
		IoAcceptor acceptor = new NioSocketAcceptor();
		
		// 添加 过滤器
		// 日志过滤器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 编码过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName(Constant.CHART_SET_UTF_8))));
		// 设置 IO处理器
		acceptor.setHandler(new TimeServerHandler());
		// 设置 读缓存的大小
		acceptor.getSessionConfig().setReadBufferSize(2048);
		// 设置 空闲时间
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		/**
		 * server 监听参数
		 */
		acceptor.setDefaultLocalAddress(new InetSocketAddress("localhost", 8001));
		// 绑定
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
