/**
 * 描述: 
 * InetAddressTest.java
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

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.hua.test.BaseTest;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * InetAddressTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class AsyncrhonousServerSocketChannelTest extends BaseTest {

	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testServerChannel() {
		try {
			AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8087);
			serverSocketChannel.bind(socketAddress);
			Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
			ByteBuffer buffer = ByteBuffer.allocateDirect(8);
			/*
			 * 将来式 轮询
			 */
/*			while (!future.isDone())
			{
				
			}*/
			/*
			 * 获取的时候会一直阻塞在这里
			 * 通常在主线程发起异步调用之后，完成了其他操作，
			 * 在最后或者合适的时间才去尝试获取(阻塞-轮询)异步调用的结果
			 */
			AsynchronousSocketChannel channel = future.get();
			/*
			 * 指定超时时间
			 * 超时无发获取目标对象将抛出超时异常
			 */
			//AsynchronousSocketChannel channel = future.get(5L, TimeUnit.SECONDS);
			
			channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer> () {
				/**
				 * @description 
				 * @param result
				 * @param attachment
				 * @author qianye.zheng
				 */
				@Override
				public void completed(Integer result, ByteBuffer attachment)
				{
					System.out.println("result = " + result + " attachment == buffer:" + (attachment == buffer));
				}
				/**
				 * @description 
				 * @param exc
				 * @param attachment
				 * @author qianye.zheng
				 */
				@Override
				public void failed(Throwable exc, ByteBuffer attachment)
				{
					System.out.println("failed");
				}
			});

			System.out.println("继续做其他事情");
			
			while (true)
			{
				System.out.println("do other thing...");
				Thread.sleep(1 * 1000);
			}
		} catch (Exception e) {
			log.error("testServerChannel =====> ", e);
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
	public void testFuture() {
		try {
			AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8087);
			serverSocketChannel.bind(socketAddress);
			Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
			ByteBuffer buffer = ByteBuffer.allocateDirect(8);
			/*
			 * 将来式 轮询
			 */
/*			while (!future.isDone())
			{
				
			}*/
			/*
			 * 获取的时候会一直阻塞在这里
			 * 通常在主线程发起异步调用之后，完成了其他操作，
			 * 在最后或者合适的时间才去尝试获取(阻塞-轮询)异步调用的结果
			 */
			AsynchronousSocketChannel channel = future.get();
			/*
			 * 指定超时时间
			 * 超时无发获取目标对象将抛出超时异常
			 */
			//AsynchronousSocketChannel channel = future.get(5L, TimeUnit.SECONDS);
			channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer> () {
				/**
				 * @description 
				 * @param result
				 * @param attachment
				 * @author qianye.zheng
				 */
				@Override
				public void completed(Integer result, ByteBuffer attachment)
				{
					System.out.println("result = " + result + " attachment == buffer:" + (attachment == buffer));
				}
				/**
				 * @description 
				 * @param exc
				 * @param attachment
				 * @author qianye.zheng
				 */
				@Override
				public void failed(Throwable exc, ByteBuffer attachment)
				{
					System.out.println("failed");
				}
			});

			System.out.println("继续做其他事情");
			
			while (true)
			{
				System.out.println("do other thing...");
				Thread.sleep(1 * 1000);
			}
		} catch (Exception e) {
			log.error("testFuture =====> ", e);
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
	public void testCompletionHandler() {
		try {
			AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
			InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 8087);
			serverSocketChannel.bind(socketAddress);
			ByteBuffer buffer = ByteBuffer.allocateDirect(8);
			serverSocketChannel.accept(buffer, new CompletionHandler<AsynchronousSocketChannel, ByteBuffer>() {
				/**
				 * @description 
				 * @param channel
				 * @param attachment
				 * @author qianye.zheng
				 */
				@Override
				public void completed(AsynchronousSocketChannel channel,
						ByteBuffer attachment)
				{
					/*
					 * 完成连接
					 */
					System.out.println("连接建立完成， 开始读取数据");
					channel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer> () {
						/**
						 * @description 
						 * @param result
						 * @param attachment
						 * @author qianye.zheng
						 */
						@Override
						public void completed(Integer result, ByteBuffer attachment)
						{
							System.out.println("读取数据完成");
						}
						/**
						 * @description 
						 * @param exc
						 * @param attachment
						 * @author qianye.zheng
						 */
						@Override
						public void failed(Throwable exc, ByteBuffer attachment)
						{
							System.out.println("failed");
						}
					});

				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment)
				{
				}
			});
			System.out.println("继续做其他事情");
			
			while (true)
			{
				System.out.println("do other thing...");
				Thread.sleep(1 * 1000);
			}
		} catch (Exception e) {
			log.error("testCompletionHandler =====> ", e);
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
