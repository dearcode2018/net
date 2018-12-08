/**
 * SocketUtil.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.util;

import java.net.Socket;

/**
 * SocketUtil
 * 描述: 
 * @author  qye.zheng
 */
public final class SocketUtil
{
	
	/**
	 * 拆包: 一次发送Socket数据量过大，而底层不支持一次发送这么大的数据量，则会拆开发送
	 * 
	 * 
	 * 黏包: 在短时间内发送很多数据量小的包时，底层会根据一定的算法(Nagle)把一些包合并发送
	 * 关闭黏包: socket.setTcpNoDely(true); 当发送速率大于读取速率时，服务端也会发生黏包.
	 * 
	 */

	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 */
	private SocketUtil()
	{
	}
	
	
	/**
	 * 判断sokcet是否可用，valid方法判断是有限的，若网络断开、服务器主动断开，底层方法是无法知道
	 * 确切的状态，因此真实检测连接状态需要通过额外的手段
	 * 1) 自定义心跳包: 在定义消息的长度基础上增加一个字节来声明心跳包
	 * 2) 发送紧急数据: socket.setOOBInline(false), 服务端会丢弃紧急数据
	 * socket.sendUrgentData(0); 
	 * 
	 */
	
	/**
	 * 
	 * @description 判断socket是否可用
	 * @param socket
	 * @return
	 * @author qianye.zheng
	 */
	public static final boolean valid(final Socket socket)
	{
		/*
		 * 1) 是否为空
		 * 2) 是否绑定
		 * 3) 是否关闭
		 * 4) 是否连接
		 * 5) 是否输入流关闭
		 * 6) 是否输出流关闭
		 */
		final boolean flag = (null != socket) && socket.isBound() && !socket.isClosed() 
				&& socket.isConnected() && !socket.isInputShutdown() && !socket.isOutputShutdown();
		
		return flag;
	}

}
