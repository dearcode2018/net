/**
 * SocketTask.java
 * @author qye.zheng
 * 
 * 	version 1.0
 */
package com.hua.net;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import com.hua.constant.Constant;
import com.hua.constant.NetConstant;
import com.hua.log.BaseLog;
import com.hua.util.StringUtil;

/**
 * SocketTask
 * 描述: 
 * @author qye.zheng
 * 
 */
public final class SocketTask extends BaseLog implements Runnable
{

	private Socket socket;
	
	private Reader reader;
	
	private Writer writer;
	
	/**
	 * 构造方法
	 * 描述: 
	 * @author qye.zheng
	 * 
	 * @param socket
	 */
	public SocketTask(Socket socket)
	{
		super();
		this.socket = socket;
	}



	/**
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Override
	public void run()
	{
		// 调用socket 处理
		handleSocket();
	}

	
	/**
	 * 
	 * 描述: socket 处理
	 * 适用于服务端和客户端
	 * @author qye.zheng
	 * 
	 */
	public void handleSocket() {
		
		try
		{
			reader = new InputStreamReader(socket.getInputStream(), Constant.CHART_SET_UTF_8);
			// 装载数据
			final char[] ch = new char[64];
			int len = 0;
			int index = Constant.NEGATIVE_ONE;
			String temp = null;
			final StringBuilder builder = StringUtil.getBuilder();
			while (Constant.NEGATIVE_ONE != (len = reader.read(ch))) {
				temp = new String(ch, 0, len);
				if (Constant.NEGATIVE_ONE != (index = temp.indexOf(NetConstant.END_OF_SYMBOL))) {
					builder.append(temp.subSequence(0,  index));
					log.info("handleSocket =====>handleSocket 结束读取");
					
					break;
				}
				builder.append(temp);
			}
			writer = new OutputStreamWriter(socket.getOutputStream(), Constant.CHART_SET_UTF_8);
			log.info("handleSocket =====> server begin write");
			// 写入数据
			writer.write("server : welcome to myserver!");
			// 写入 结束标识
			writer.write(NetConstant.END_OF_SYMBOL);
			// 刷新缓存
			writer.flush();
			
		} catch (Exception e)
		{
			log.error("handleSocket =====> ", e);
		} finally {
			try
			{
				if (null != reader) {
					reader.close();
				}
				if (null != writer) {
					writer.close();
				}
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e2)
			{
				log.error("handleSocket =====> ", e2);
			}
		}
	}
	
}
