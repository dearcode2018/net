/**
 * 描述: 
 * SocketTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.socket;

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

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.hua.constant.Constant;
import com.hua.constant.NetConstant;
import com.hua.test.BaseTest;
import com.hua.util.StringUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * SocketTest
 */
public final class SocketTest extends BaseTest {

	/**
	 * 
	 * 描述: 服务端 接收多个客户端
	 * 异步步方式
	 * 解决高并发访问
	 * 通过线程来实现
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testServerAcceptMultiAsync() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	
	/**
	 * 
	 * 描述: 服务端 接收多个客户端
	 * 同步方式
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testServerAcceptMulti() {
		try {
			// 将读写 放在一个while 循环中
			while (true) {
				/** begin 服务端读取数据 */
				// 指定监听端口的ServerSocket
				serverSocket = new ServerSocket(SERVER_PORT);
				// socket
				socketOfServer = serverSocket.accept();
				
				log.info("testServer =====> server begin read");
				serverReader = new InputStreamReader(socketOfServer.getInputStream(), Constant.CHART_SET_UTF_8);
				// 装载数据
				final char[] ch = new char[64];
				int len = 0;
				int index = Constant.NEGATIVE_ONE;
				String temp = null;
				final StringBuilder builder = StringUtil.getBuilder();
				while (Constant.NEGATIVE_ONE != (len = serverReader.read(ch))) {
					temp = new String(ch, 0, len);
					if (Constant.NEGATIVE_ONE != (index = temp.indexOf(NetConstant.END_OF_SYMBOL))) {
						builder.append(temp.subSequence(0,  index));
						log.info("testServer =====>server 结束读取");
						
						break;
					}
					builder.append(temp);
				}
				serverResult = builder.toString();
				
				log.info("testServerSimple =====> result = " + serverResult);
				/** end of 服务端读取数据 */
				
				/** begin 服务端写数据 */
				serverWriter = new OutputStreamWriter(socketOfServer.getOutputStream(), Constant.CHART_SET_UTF_8);
				log.info("testServer =====> server begin write");
				// 写入数据
				serverWriter.write("server : welcome to myserver!");
				// 写入 结束标识
				serverWriter.write(NetConstant.END_OF_SYMBOL);
				// 刷新缓存
				serverWriter.flush();
				/** end of 服务端写数据 */
			}
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 客户端写入
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testClientSimple() {
		try {
			socketOfClient = new Socket(SERVER_ADDR, SERVER_PORT);
			
			clientWriter = new OutputStreamWriter(socketOfClient.getOutputStream(), Constant.CHART_SET_UTF_8);
			log.info("testClientSimple =====>client begin write");
			// 写入数据
			clientWriter.write("client : hello this is my word!");
			// 刷新缓存
			clientWriter.flush();
			
		} catch (Exception e) {
			log.error("testClientSimple =====> ", e);
		}  finally {
			try
			{
				if (null != clientWriter)
				{
					clientWriter.close();
				}
				if (null != clientReader) {
					clientReader.close();
				}
				if (null != clientReader) {
					// 关闭
					clientReader.close();
				}
				if (null != socketOfClient) {
					socketOfClient.close();
				}
			} catch (Exception e2)
			{
				log.error("testClientSimple =====> ", e2);
			}
		}
	}
	
	/**
	 * 
	 * 描述: 服务器读取
	 * 接收单个客户端
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testServerSimple() {
		try {
			// 指定监听端口的ServerSocket
			serverSocket = new ServerSocket(SERVER_PORT);
			// socket
			socketOfServer = serverSocket.accept();
			
			serverReader = new InputStreamReader(socketOfServer.getInputStream(), Constant.CHART_SET_UTF_8);
			final char[] ch = new char[64];
			int len = 0;
			final StringBuilder builder = StringUtil.getBuilder();
			while (Constant.NEGATIVE_ONE != (len = serverReader.read(ch))) {
				builder.append(new String(ch, 0, len));
			}
			result = builder.toString();
			
			log.info("testServerSimple =====> result = " + result);
		} catch (Exception e) {
			log.error("testServerSimple =====> ", e);
		} finally {
			try
			{
				if (null != serverReader) {
					// 关闭
					serverReader.close();
				}
				if (null != serverWriter) 
				{
					serverWriter.close();
				}
				if (null != socketOfServer) {
					socketOfServer.close();
				}
				if (null != serverSocket) {
					serverSocket.close();
				}
			} catch (Exception e2)
			{
				log.error("testServerSimple =====> ", e2);
			}
		}
	}
	
	/**
	 * 
	 * 描述: 客户端读写
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testClient() {
		try {
			/** begin 客户端写数据 */
			socketOfClient = new Socket(SERVER_ADDR, SERVER_PORT);
			clientWriter = new OutputStreamWriter(socketOfClient.getOutputStream(), Constant.CHART_SET_UTF_8);
			log.info("testClientSimple =====>client begin write");
			// 写入数据
			clientWriter.write("client : hello this is my word!");
			// 写入 结束标识
			clientWriter.write(NetConstant.END_OF_SYMBOL);
			// 刷新缓存
			clientWriter.flush();
			/** end of 客户端写数据 */
			
			/** begin 客户端读取数据 */
			log.info("testClient =====> client begin read");
			clientReader = new InputStreamReader(socketOfClient.getInputStream(), Constant.CHART_SET_UTF_8);
			// 装载数据
			final char[] ch = new char[64];
			int index = Constant.NEGATIVE_ONE;
			int len = 0;
			String temp = null;
			final StringBuilder builder = StringUtil.getBuilder();
			while (Constant.NEGATIVE_ONE != (len = clientReader.read(ch))) {
				temp = new String(ch, 0, len);
				if (Constant.NEGATIVE_ONE != (index = temp.indexOf(NetConstant.END_OF_SYMBOL))) {
					builder.append(temp.subSequence(0,  index));
					log.info("testclient =====>client 结束读取");
					
					break;
				}
				builder.append(temp);
			}
			clientResult = builder.toString();
			
			log.info("testClient =====> clientResult = " + clientResult);
			/** end of 客户端读取数据 */
		
		} catch (Exception e) {
			log.error("testClient =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 服务器读写
	 * 接收单个客户端
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testServer() {
		try {
			/** begin 服务端读取数据 */
			// 指定监听端口的ServerSocket
			serverSocket = new ServerSocket(SERVER_PORT);
			// socket
			socketOfServer = serverSocket.accept();
			
			log.info("testServer =====> server begin read");
			serverReader = new InputStreamReader(socketOfServer.getInputStream(), Constant.CHART_SET_UTF_8);
			// 装载数据
			final char[] ch = new char[64];
			int len = 0;
			int index = Constant.NEGATIVE_ONE;
			String temp = null;
			final StringBuilder builder = StringUtil.getBuilder();
			while (Constant.NEGATIVE_ONE != (len = serverReader.read(ch))) {
				temp = new String(ch, 0, len);
				if (Constant.NEGATIVE_ONE != (index = temp.indexOf(NetConstant.END_OF_SYMBOL))) {
					builder.append(temp.subSequence(0,  index));
					log.info("testServer =====>server 结束读取");
					
					break;
				}
				builder.append(temp);
			}
			serverResult = builder.toString();
			
			log.info("testServerSimple =====> result = " + serverResult);
			/** end of 服务端读取数据 */
			
			/** begin 服务端写数据 */
			serverWriter = new OutputStreamWriter(socketOfServer.getOutputStream(), Constant.CHART_SET_UTF_8);
			log.info("testServer =====> server begin write");
			// 写入数据
			serverWriter.write("server : welcome to myserver!");
			// 写入 结束标识
			serverWriter.write(NetConstant.END_OF_SYMBOL);
			// 刷新缓存
			serverWriter.flush();
			
			/** end of 服务端写数据 */
			
		} catch (Exception e) {
			log.error("testServer =====> ", e);
		} finally {
			try
			{
				if (null != serverReader) {
					// 关闭
					serverReader.close();
				}
				if (null != socketOfServer) {
					socketOfServer.close();
				}
				if (null != serverSocket) {
					serverSocket.close();
				}
			} catch (Exception e2)
			{
				log.error("testServerSimple =====> ", e2);
			}
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
	 * 描述: 普通测试方法
	 ,@Test注解 不带参数
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testNormal() {
		System.out.println("testNormal()");
	}
	
	/**
	 * 
	 * 描述: 期望发生异常-测试方法
	 ,@Test注解 异常
	 * @author qye.zheng
	 * 
	 */
	@Test(expected=NullPointerException.class)
	@SuppressWarnings("all")
	public void testException() {
		String str = null;
		System.out.println(str.length());
	}
	
	/**
	 * 
	 * 描述: 超时测试方法
	 ,@Test注解 指定运行时间 (单位 : 毫秒)
	 * @author qye.zheng
	 * 
	 */
	@Test(timeout=3000)
	public void testTimeOut() {
		System.out.println("testTimeOut()");
	}
	
	/**
	 * 
	 * 描述: 测试忽略的方法
	 * @author qye.zheng
	 * 
	 */
	@Ignore("暂时忽略的方法")
	@Test
	public void ignoreMethod() {
		System.out.println("ignoreMethod()");
	}
	
	/**
	 * 
	 * 描述: [所有测试]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass()");
	}
	
	/**
	 * 
	 * 描述: [所有测试]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@Before
	public void beforeMethod() {
		System.out.println("beforeMethod()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@After
	public void afterMethod() {
		System.out.println("afterMethod()");
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

}
