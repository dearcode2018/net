/**
 * 描述: 
 * SocketServerTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.socket;

//静态导入
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.hua.constant.Constant;
import com.hua.test.BaseTest;
import com.hua.util.StringUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * SocketServerTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class SocketServerTest extends BaseTest {

	
	
	/**
	 * 
	 */
	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void startServer() {
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("等待连接的到来");
			//
			Socket socket = serverSocket.accept();
			// 建立连接之后，从socket中获取输入流，并建立缓冲进行读取
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			StringBuilder builder = StringUtil.getBuilder();
			int length = -1;
			while (-1 != (length = inputStream.read(buffer)))
			{
				builder.append(new String(buffer, 0, length, Constant.CHART_SET_UTF_8));
			}
			System.out.println("get message form client:" + builder.toString());
			
			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("startServer =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testRead() {
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("等待连接的到来");
			/*
			 * 阻塞 直到有请求到来
			 */
			Socket socket = serverSocket.accept();
			// 建立连接之后，从socket中获取输入流，并建立缓冲进行读取
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			StringBuilder builder = StringUtil.getBuilder();
			int length = -1;
			while (-1 != (length = inputStream.read(buffer)))
			{
				builder.append(new String(buffer, 0, length, Constant.CHART_SET_UTF_8));
			}
			System.out.println("get message form client:" + builder.toString());
			
			inputStream.close();
			
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("testRead =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testReadWrite() {
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("等待连接的到来");
			//
			Socket socket = serverSocket.accept();
			// 建立连接之后，从socket中获取输入流，并建立缓冲进行读取
			// 服务端读
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			StringBuilder builder = StringUtil.getBuilder();
			int length = -1;
			while (-1 != (length = inputStream.read(buffer)))
			{
				builder.append(new String(buffer, 0, length, Constant.CHART_SET_UTF_8));
			}
			System.out.println("get message form client:" + builder.toString());
			//
			//socket.shutdownInput();
			
			// 服务端写
			OutputStream outputStream = socket.getOutputStream();
			byte[] response = "from socketServer".getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(response);
			outputStream.flush();
			
			/*
			 * 关闭输入、输出流将直接导致socket关闭
			 * 
			 */
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("testReadWrite =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testReadWriteWithPhrase() {
		try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("等待连接的到来");
			//
			Socket socket = serverSocket.accept();
			// 建立连接之后，从socket中获取输入流，并建立缓冲进行读取
			// 服务端读
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Constant.CHART_SET_UTF_8));
			StringBuilder builder = StringUtil.getBuilder();
			String line = null;
			while (null != (line = reader.readLine()) && !MSG_END_PHRASE.equals(line) )
			{
				builder.append(line);
			}
			System.out.println("get message form client2:" + builder.toString());
			
			// 继续读
			while (null != (line = reader.readLine()) && !MSG_END_PHRASE.equals(line) )
			{
				builder.append(line);
			}
			System.out.println("get message form client3:" + builder.toString());
			
			// 服务端写
			OutputStream outputStream = socket.getOutputStream();
			// 最后一行 加上消息结束短语
			byte[] response = ("from socketServer\n" + MSG_END_PHRASE).getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(response);
			outputStream.flush();
			
			
			
			
			
			/*
			 * 关闭输入、输出流将直接导致socket关闭
			 * 
			 */
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("testReadWriteWithPhrase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testReadWriteWithLength() {
		try {
			/**
			 * 2个端约定采用2个字节(16位)长度来表示收发数据的长度
			 * 
			 * 发送端: 第一个字节-长度int型右移8位作为高8位，第二个字节直接传送作为低8位
			 * 2个字节分开发送，然后发送数据部分
			 * 
			 * 接收端: 第一个字节-长度byte型左移8位加上第二个字节，即为数据的长度
			 * 2个字节分开接收，然后再接收数据部分
			 * 
			 * Redis客户端Jedis即是采用长度方式来收发数据
			 */
			
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("等待连接的到来");
			//
			Socket socket = serverSocket.accept();
			int first = -1;
			int second = -1;
			int length = 0;
			byte[] data = null;
			// 建立连接之后，从socket中获取输入流，并建立缓冲进行读取
			// 服务端读
			InputStream inputStream = socket.getInputStream();
			
			// 1.读取第一个字节作为int型前8位
			first = inputStream.read();
			// 2.读取第二个字节作为int型后8位
			second = inputStream.read();
			length = (first << 8) + second;
			// 3.读取数据，构造指定长度的数组
			data = new byte[length];
			inputStream.read(data);
			System.out.println("get message form client2:" + new String(data, Constant.CHART_SET_UTF_8));
			
			// 继续读
			// 1.读取第一个字节作为int型前8位
			first = inputStream.read();
			// 2.读取第二个字节作为int型后8位
			second = inputStream.read();
			length = (first << 8) + second;
			// 3.读取数据，构造指定长度的数组
			data = new byte[length];
			inputStream.read(data);
			System.out.println("get message form client3:" + new String(data, Constant.CHART_SET_UTF_8));
			
			// 服务端写
			OutputStream outputStream = socket.getOutputStream();
			// 最后一行 加上消息结束短语
			byte[] response = ("from socketServer\n" + MSG_END_PHRASE).getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(response);
			outputStream.flush();
			
			
			
			
			
			/*
			 * 关闭输入、输出流将直接导致socket关闭
			 * 
			 */
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("testReadWriteWithPhrase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
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
	@DisplayName("testTemp")
	@Test
	public void testTemp() {
		try {
			//System.out.print(System.getProperty("line.separator"));
			
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
	@DisplayName("testCommon")
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
	@DisplayName("testSimple")
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
	@DisplayName("testBase")
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("beforeMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@BeforeEach
	public void beforeMethod() {
		System.out.println("beforeMethod()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("afterMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@AfterEach
	public void afterMethod() {
		System.out.println("afterMethod()");
	}
	
	/**
	 * 
	 * 描述: 测试忽略的方法
	 * @author qye.zheng
	 * 
	 */
	@Disabled
	@DisplayName("ignoreMethod")
	@Test
	public void ignoreMethod() {
		System.out.println("ignoreMethod()");
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("noUse")
	@Disabled("解决ide静态导入消除问题 ")
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
		assertArrayEquals(expecteds, actuals, message);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(true, message);
		assertTrue(true, message);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(expecteds, actuals, message);
		assertNotSame(expecteds, actuals, message);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(actuals, message);
		assertNotNull(actuals, message);
		
		fail();
		fail("Not yet implemented");
		
		dynamicTest(null, null);
		
		assumeFalse(false);
		assumeTrue(true);
		assumingThat(true, null);
	}

}
