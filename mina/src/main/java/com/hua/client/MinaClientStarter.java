/**
  * @filename MinaClientStarter.java
  * @description  
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.Test;

import com.hua.constant.Constant;
import com.hua.constant.MinaConstant;

 /**
 * @type MinaClientStarter
 * @description  
 * @author qianye.zheng
 */
public final class MinaClientStarter {

	/**
	 * 
	 * @description
	 * @author qianye.zheng
	 */
	@Test
	public static final void start()
	{
		/*
		 * IO 接收器，创建一个非阻塞服务端Socket
		 * 服务端用 IOAcceptor，客户端用IOConnector
		 */
		IoConnector connector = new NioSocketConnector();
		   // 设置链接超时时间  
		connector.setConnectTimeoutMillis(30000L);  
		// 添加 过滤器
		// 日志过滤器
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		// 编码过滤器
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
				new TextLineCodecFactory(Charset.forName(Constant.CHART_SET_UTF_8))));
		// 设置 IO处理器
		connector.setHandler(new MinaClientHandler());
		IoSession session = null;
		ConnectFuture future = connector.connect(new InetSocketAddress(MinaConstant.HOST, MinaConstant.PORT));
		// 等待连接创建完成
		future.awaitUninterruptibly();
		// 获得session
		session = future.getSession();
		// 发送消息
		session.write("hello i am client...");
		// 等待连接断开
		session.getCloseFuture().awaitUninterruptibly();
		// 连接闲置
		connector.dispose();
	}
}
