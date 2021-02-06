/**
 * IPUtil.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
	 * @description 获取当前设备IPv6地址
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
		String ipv6Addr = null;
		try {
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress inet6Addr = null;
			while (networkInterfaces.hasMoreElements()) {
				final Enumeration<InetAddress> inetAdddrs = networkInterfaces.nextElement().getInetAddresses();
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
			if (null != inet6Addr) {
				ipv6Addr = inet6Addr.getHostAddress();
				int index = ipv6Addr.lastIndexOf(Constant.PERCENT);
				if (-1 != index) { // windows %和scope_id为后缀
					ipv6Addr = ipv6Addr.substring(0, index);
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return ipv6Addr;
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
	
}
