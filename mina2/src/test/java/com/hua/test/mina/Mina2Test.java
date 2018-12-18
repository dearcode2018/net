/**
 * 描述: 
 * Mina2Test.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.mina;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.beans.Transient;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.junit.Ignore;
import org.junit.Test;

import com.hua.client.MinaClientHandler;
import com.hua.constant.Constant;
import com.hua.constant.MinaConstant;
import com.hua.server.MinaServerHandler;
import com.hua.test.BaseTest;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * Mina2Test
 */
public final class Mina2Test extends BaseTest {

	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void startServer() {
		try {
			
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
			//acceptor.setDefaultLocalAddress(new InetSocketAddress(MinaConstant.HOST, MinaConstant.PORT));
			// 绑定
			acceptor.bind(new InetSocketAddress(MinaConstant.HOST, MinaConstant.PORT));
			/**
			 * 服务端一直开启
			 */
			Thread.sleep(1000 * 1000);
			//acceptor.dispose();
		} catch (Exception e) {
			log.error("testMinaServer =====> ", e);
		}
	}

	/**
	 * 
	 * @description
	 * @author qianye.zheng
	 */
	@Test
	public final void startClient()
	{
		try {
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
			session.write("hello i am client..." + new Date());
			
			session.write("quit");
			// 等待连接断开，等待服务器来实施 关闭?
			//session.getCloseFuture().awaitUninterruptibly();
			// 连接闲置
			connector.dispose();
			//Thread.sleep(900 * 1000);
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testTemp() {
		try {
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}
	
	public static void main(String[] args) {
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
		
		acceptor.dispose();
	}

}
