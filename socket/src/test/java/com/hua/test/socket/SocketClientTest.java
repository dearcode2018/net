/**
 * 描述: 
 * SocketClientTest.java
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
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
 * SocketClientTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class SocketClientTest extends BaseTest {

	
	/**
	 * 结束的标志
	 * 
	 * 1) 关闭socket
	 * socket.close()
	 * 2) 关闭流
	 * stream.close()，关闭将导致socket关闭，等价于socket关闭
	 * 不管是输入流还是输出流，关闭其中一个将导致socket关闭
	 * 3) 通过socket关闭流
	 * shutdownOutput/shutdownInput
	 * 4) 通过约定符号
	 * 约定字符或短语，当作消息发送完成的标记，这需要改造发送/接收方法.
	 * 优点: 不需要关闭流，发送完一条消息后续还可以继续发送消息
	 * 缺点: 额外的标记，容易和消息内容重复，短语太复杂不好处理还占带宽
	 * 
	 * 5) 指定长度
		 * 2个端约定采用2个字节(16位)长度来表示收发数据的长度
		 * 
		 * 发送端: 第一个字节-长度int型右移8位作为高8位，第二个字节直接传送作为低8位
		 * 2个字节分开发送，然后发送数据部分
		 * 
		 * 接收端: 第一个字节-长度byte型左移8位加上第二个字节，即为数据的长度
		 * 2个字节分开接收，然后再接收数据部分
		 * 
		 * Redis客户端Jedis即是采用长度方式来收发数据
		 * 
		 * 6) 流行的模式: 长度 + 类型 + 数据
		 * 和http Content-Length + Content-Type + Data模式相似
	 */
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void startClient() {
		try {
			
			Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
			
			// 客户端写数据
			OutputStream outputStream = socket.getOutputStream();
			byte[] request = "from socketServer".getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(request);
			outputStream.flush();
			
			// 客户端读
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			StringBuilder builder = StringUtil.getBuilder();
			int length = -1;
			while (-1 != (length = inputStream.read(buffer)))
			{
				builder.append(new String(buffer, 0, length, Constant.CHART_SET_UTF_8));
			}
			System.out.println("get message form server:" + builder.toString());
			
			
			inputStream.close();
			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("startClient =====> ", e);
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
	public void testWrite() {
		try {
			
			Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
			
			// 客户端写数据
			OutputStream outputStream = socket.getOutputStream();
			byte[] request = "from client".getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(request);
			outputStream.flush();

			outputStream.close();
			socket.close();
			serverSocket.close();
		} catch (Exception e) {
			log.error("testWrite =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 写读: 先请求数据，然后再接收数据
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testWriteRead() {
		try {
			
			Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
			
			// 客户端写数据
			OutputStream outputStream = socket.getOutputStream();
			byte[] request = "from client2".getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(request);
			outputStream.flush();
			
			/*
			 * 通知服务器发送数据完毕，后续只接收数据
			 * 而不是关闭输出流，因为关闭流将直接导致socket关闭
			 * shutdownOutput()执行之后，后面就无法输出消息到另外一端了.
			 */
			socket.shutdownOutput();
			/*
			 * 输出完毕要关闭输出流，然后服务端才能继续读取
			 * 否则服务端会一直阻塞
			 */
			//outputStream.close();
			
			// 客户端读
			InputStream inputStream = socket.getInputStream();
			byte[] buffer = new byte[1024];
			StringBuilder builder = StringUtil.getBuilder();
			int length = -1;
			while (-1 != (length = inputStream.read(buffer)))
			{
				builder.append(new String(buffer, 0, length, Constant.CHART_SET_UTF_8));
			}
			System.out.println("get message form server:" + builder.toString());
			
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (Exception e) {
			log.error("testWriteRead =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 写读: 先请求数据，然后再接收数据
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testWriteReadWithPhrase() {
		try {
			
			Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
			
			// 客户端写数据
			OutputStream outputStream = socket.getOutputStream();
			byte[] request = null;
			/*
			 * 最后一行 加上消息结束短语，
			 * 注意，消息短语的最后还要再添加一个换行符，这样才避免另一端在读取的时候
			 * 处于堵塞状态，因为读取到最后一行的时候会进入阻塞状态
			 */
			request = ("from client2\n" + MSG_END_PHRASE + "\n").getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(request);
			outputStream.flush();
			BufferedWriter writer = null;
			writer.newLine();
			
			// 继续写
			request = ("from client3\n" + MSG_END_PHRASE + "\n").getBytes(Constant.CHART_SET_UTF_8);
			outputStream.write(request);
			outputStream.flush();
			
			
			// 客户端读
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Constant.CHART_SET_UTF_8));
			StringBuilder builder = StringUtil.getBuilder();
			String line = null;
			while ( null != (line = reader.readLine()) && !MSG_END_PHRASE.equals(line))
			{
				builder.append(line);
			}
			System.out.println("get message form server:" + builder.toString());
			
			
			
			
			
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (Exception e) {
			log.error("testWriteReadWithPhrase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 写读: 先请求数据，然后再接收数据
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testWriteReadWithLength() {
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
			
			Socket socket = new Socket(SERVER_ADDR, SERVER_PORT);
			
			// 客户端写数据
			OutputStream outputStream = socket.getOutputStream();
			
			byte[] request = null;
			/*
			 * 最后一行 加上消息结束短语，
			 * 注意，消息短语的最后还要再添加一个换行符，这样才避免另一端在读取的时候
			 * 处于堵塞状态，因为读取到最后一行的时候会进入阻塞状态
			 */
			request = ("from client2").getBytes(Constant.CHART_SET_UTF_8);
			// 1.发送长度第一个字节 作为int型的高8位
			outputStream.write(request.length >> 8);
			// 2.发送长度第二个字节
			outputStream.write(request.length);
			// 3.发送数据
			outputStream.write(request);
			outputStream.flush();
			
			// 继续写
			request = ("from client3").getBytes(Constant.CHART_SET_UTF_8);
			// 1.发送长度第一个字节 作为int型的高8位
			outputStream.write(request.length >> 8);
			// 2.发送长度第二个字节
			outputStream.write(request.length);
			// 3.发送数据
			outputStream.write(request);
			outputStream.flush();
			
			// 客户端读
			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Constant.CHART_SET_UTF_8));
			StringBuilder builder = StringUtil.getBuilder();
			String line = null;
			while ( null != (line = reader.readLine()) && !MSG_END_PHRASE.equals(line))
			{
				builder.append(line);
			}
			System.out.println("get message form server:" + builder.toString());
			
			outputStream.close();
			inputStream.close();
			socket.close();
		} catch (Exception e) {
			log.error("testWriteReadWithLength =====> ", e);
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
