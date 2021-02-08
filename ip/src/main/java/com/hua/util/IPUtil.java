/**
 * IPUtil.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.util;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.hua.constant.Constant;

/**
 * IPUtil
 * 描述: 
 * @author  qye.zheng
 */
public final class IPUtil
{

	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 */
	private IPUtil()
	{
	}
	
	/**
	 * 
	 * @description 规范化IP
	 * 将简缩ip转换为标准形式
	 * @param ip
	 * @return
	 * @author qianye.zheng
	 */
	public static final String normalize(final String ip) {
		final AtomicReference<String> flag = new AtomicReference<>();
		getInstance(ip).ifPresent(x ->  flag.set(x.getHostAddress()));

		return flag.get();
	}
	
	/**
	 * 
	 * @description 是否是ipv4
	 * @param ip ip地址
	 * @return
	 * @author qianye.zheng
	 */
	public static final boolean isIPv4(final String ip) {
		final AtomicBoolean flag = new AtomicBoolean(false);
		getInstance(ip).ifPresent(x -> { if (x instanceof Inet4Address) flag.set(true); });
		
		return flag.get();
	}
	
	/**
	 * 
	 * @description 是否是ipv6
	 * @param ip
	 * @return
	 * @author qianye.zheng
	 */
	public static final boolean isIPv6(final String ip) {
		final AtomicBoolean flag = new AtomicBoolean(false);
		getInstance(ip).ifPresent(x -> { if (x instanceof Inet6Address) flag.set(true); });
		
		return flag.get();
	}
	
	/**
	 * 
	 * @description 获取当前设备对外IP(v4/v6)地址
	 * @return
	 * @author qianye.zheng
	 */
	public static final String getLocalI() {
		/*
		 * 获取本机对外IP，不能通过InetAddress.getLocalHost()获取
		 * 通过getLocalHost()方法可能得到诸如 ::1这样的特殊IP，这种ip其他设备无法连接
		 * 
		 * 通过遍历网络接口的各个地址，直至找到符合要求的地址
		 */
		String ipAddr = null;
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress targetAddr = null;
			while (networkInterfaces.hasMoreElements()) {
				final Enumeration<InetAddress> inetAdddrs = networkInterfaces.nextElement().getInetAddresses();
				while (inetAdddrs.hasMoreElements()) {
					InetAddress inetAddr = inetAdddrs.nextElement();
					if (!isReservedAddr(inetAddr)) {
						targetAddr = inetAddr;
						break;
					}
				}
				if (null != targetAddr) {
					break;
				}
			}
			if (null != targetAddr) {
				ipAddr = targetAddr.getHostAddress();
				int index = ipAddr.lastIndexOf(Constant.PERCENT);
				if (-1 != index) { // windows %和scope_id为后缀
					ipAddr = ipAddr.substring(0, index);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return ipAddr;
	}

	/**
	 * 
	 * @description 获取当前设备对外IPv6地址
	 * @return
	 * @author qianye.zheng
	 */
	public static final String getLocalIPv6() {
		/*
		 * 获取本机对外IP，不能通过InetAddress.getLocalHost()获取
		 * 通过getLocalHost()方法可能得到诸如 ::1这样的特殊IP，这种ip其他设备无法连接
		 * 
		 * 通过遍历网络接口的各个地址，直至找到符合要求的地址
		 */
		String ipAddr = null;
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress targetAddr = null;
			while (networkInterfaces.hasMoreElements()) {
				final Enumeration<InetAddress> inetAdddrs = networkInterfaces.nextElement().getInetAddresses();
				while (inetAdddrs.hasMoreElements()) {
					InetAddress inetAddr = inetAdddrs.nextElement();
					if (inetAddr instanceof Inet6Address && !isReservedAddr(inetAddr)) {
						targetAddr = inetAddr;
						break;
					}
				}
				if (null != targetAddr) {
					break;
				}
			}
			if (null != targetAddr) {
				ipAddr = targetAddr.getHostAddress();
				int index = ipAddr.lastIndexOf(Constant.PERCENT);
				if (-1 != index) { // windows %和scope_id为后缀
					ipAddr = ipAddr.substring(0, index);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return ipAddr;
	}
	
	/**
	 * 
	 * @description 是否是保留地址
	 * 本地、回路、链路本地地址
	 * @param inetAddress
	 * @return
	 * @author qianye.zheng
	 */
	private static final boolean isReservedAddr(final InetAddress inetAddress) {
		return inetAddress.isAnyLocalAddress() // 本地地址
				|| inetAddress.isLinkLocalAddress() // 链路本地地址
				|| inetAddress.isLoopbackAddress(); // 回路地址
	}
	
	/**
	 * 
	 * @description 
	 * @param hostOrIp
	 * @return
	 * @author qianye.zheng
	 */
	private static final Optional<InetAddress> getInstance(final String hostOrIp) {
		try {
			return Optional.ofNullable(InetAddress.getByName(hostOrIp));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return Optional.empty();
	}
	
}
