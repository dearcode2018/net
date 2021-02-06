/**
 * 描述: 
 * IPv6Test.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.ip;

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

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.hua.constant.Constant;
import com.hua.test.BaseTest;




/**
 * 描述: 
 * 
 * @author qye.zheng
 * IPv6Test
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class IPv6Test extends BaseTest {

	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testIPv6() {
		try {
			String address = "";
			// 私有 不可见，不建议使用
			//sun.net.util.IPAddressUtil.isIPv6LiteralAddress(address);
			
			
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
	//@DisplayName("test")
	@Test
	public void testInetAddress() {
		try {
			String hostOrIp = "fe80::18b5:7c11:cc48:41de";
			InetAddress inetAddress = InetAddress.getByName(hostOrIp);
			String format = "hostName: %s, hostAddress: %s";
			// InetAddress.getHostAddress() 可以获取正规化 ipv6 字符串
			System.out.println(String.format(format, inetAddress.getHostName(), inetAddress.getHostAddress()));
		
			
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
	//@DisplayName("test")
	@Test
	public void testLocalHost() {
		try {
			/*
			 * 获取本机对外IP，不能通过InetAddress.getLocalHost()获取
			 * 通过getLocalHost()方法可能得到诸如 ::1这样的特殊IP，这种ip其他设备无法连接
			 * 
			 * 通过遍历网络接口的各个地址，直至找到符合要求的地址
			 */
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress inet6Addr = null;
			while (networkInterfaces.hasMoreElements()) {
				Enumeration<InetAddress> inetAdddrs = networkInterfaces.nextElement().getInetAddresses();
				while (inetAdddrs.hasMoreElements()) {
					InetAddress inetAddr = inetAdddrs.nextElement();
					if (inetAddr instanceof Inet6Address && !isReservedAddr(inetAddr)) {
						inet6Addr = inetAddr;
						break;
					}
				}
				if (null != inet6Addr) {
					break;
				}
			}
			assertNotNull(inet6Addr);
			
			String ipv6Addr = inet6Addr.getHostAddress();
			int index = ipv6Addr.lastIndexOf(Constant.PERCENT);
			if (-1 != index) {
				ipv6Addr = ipv6Addr.substring(0, index);
			}
			
			System.out.println(String.format("IPv6 = %s", ipv6Addr));
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}	
	
	/**
	 * 
	 * @description 是否是保留地址
	 * 本地、回路、链路本地地址
	 * @param inetAddress
	 * @return
	 * @author qianye.zheng
	 */
	public boolean isReservedAddr(InetAddress inetAddress) {
		System.out.println(inetAddress.isAnyLocalAddress());
		System.out.println(inetAddress.isLinkLocalAddress());
		System.out.println(inetAddress.isLoopbackAddress());
		return inetAddress.isAnyLocalAddress() || inetAddress.isLinkLocalAddress() 
				|| inetAddress.isLoopbackAddress();
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testLocalHostOld() {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String format = "hostName: %s, hostAddress: %s";
			// InetAddress.getHostAddress() 可以获取正规化 ipv6 字符串
			System.out.println(String.format(format, inetAddress.getHostName(), inetAddress.getHostAddress()));
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
	//@DisplayName("test")
	@Test
	public void testInet6Address() {
		try {
			//Inet6Address inet6Address = new Inet6Address();
			
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
