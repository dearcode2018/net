/**
 * FileShareDriver.java
 * @author  qye.zheng
 * 
 * 	version 1.0
 */
package com.hua.driver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import jcifs.smb.SmbFile;

import com.hua.constant.Constant;
import com.hua.entity.SmbParam;
import com.hua.util.ReadProperties;

/**
 * FileShareDriver
 * 描述: 
 * @author  qye.zheng
 * 
 */
public final class FileShareDriver
{
	private static final String CONFIG_PATH = "/conf/properties/smb.properties";

	private static final ReadProperties readProps = new ReadProperties(CONFIG_PATH);

	/* 共用 : 单例，没有特殊要求 直接使用该实例 */
	private static SmbParam param;

	// 连接url值
	private static String url;
	
	// 加载连接共用属性
	static
	{
		final Properties props = readProps.getProps();
		param = new SmbParam(props);
		url = param.getUrl();
	}

	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 * 
	 */
	private FileShareDriver()
	{
	}
	
	/*
	 拷贝文件之前，通常拷贝的目录是在properties文件中配置好的；如果有特殊情况，可以使用param
	 来修改共享路径
	 */
	
	
	/**
	 * 
	 * 描述: 远程拷贝
	 * @author  qye.zheng
	 * 
	 * @param serverPath 共享路径(服务器路径) - 以斜杠结束
	 * @param filename 要拷贝的文件名
	 * @param destPath 拷贝目的路径 - 以斜杠结束
	 * @return
	 */
	public static boolean copyToClient(final String serverPath, final String filename, final String destPath)
	{
		boolean flag = false;
		//InputStream is = null;
		OutputStream os = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try
		{
			// 修改param参数值，需要重新调用其方法获取新的url值
			//param.setSharePath(sharePath);
			//url = param.getUrl();
			if (null == serverPath)
			{
				System.out.println("参数 : 共享路径 为空，使用配置文件设定的值");
			} else
			{
				param.setSharePath(serverPath);
				// 重新获取url
				url = param.getUrl();
			}
			final SmbFile smbFile = new SmbFile(url);
			if (smbFile.isDirectory())
			{
				//
				final SmbFile file = new SmbFile(smbFile, filename);
				// 输入流
				reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
				// 输出流 - 将数据转到目的地
				os = new FileOutputStream(destPath + filename);
				writer = new BufferedWriter(new OutputStreamWriter(os));
				String temp = null;
				while (null != (temp = reader.readLine()))
				{
					writer.write(temp);
					// 换行 (逐行读取，应该考虑换行问题)
					writer.write(Constant.LINE_BREAK);
				}
				/*
				 在循环体外面执行缓存刷新动作，将最后小于缓存值
				 那部分的数据输出，确保数据的输出或完整的输出
				 */
				writer.flush();
				/*	
				 warning: 这种写法会导致1024kb及以下的文件无法成功写出输出流 
				 byte[] data = new byte[1024];
				// 每次读取的字节长度，到文件末尾的时候，为了防止写入字节数变多
				int length = data.length;
				System.out.println("SSS" + is.available());
				
				while (Constant.NEGATIVE_ONE != is.read(data))
				{
					// 由于 SmbFile 对于 getInputStream()返回的InputStream对象并没有实现 available()方法的功能
					// 因此放弃使用此方法
					if (is.available() < length)
					{
						length = is.available();
						System.out.println("-----------" + length);
					}
					os.write(data, 0, length);
				}*/
				
				/*
				 正确的写法，可以防止出现上面的问题
				 byte[] data = new byte[1024];
				// 每次读取的字节长度，到文件末尾的时候，为了防止写入字节数变多
				int length = -1;
				System.out.println("SSS" + is.available());
				// is.read(data) 每次返回读取了多少个字节，若到达文件末尾，则返回-1
				while (Constant.NEGATIVE_ONE != (length = is.read(data)))
				{
					os.write(data, 0, length);
				}
				 
				 */
				
				
			} else 
			{
				System.out.println("所提供共享路径不是目录");
				
				return flag;
			}
			// 
			flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		} finally {
			try
			{
				if (null != writer)
				{
					writer.close();
				}
				if (null != os)
				{
					os.close();
				}
				if (null != reader)
				{
					reader.close();
				}
				
			} catch (IOException cl)
			{
				cl.printStackTrace();
			}
		}
		
		return flag;
	}
	
	
	/**
	 * 
	 * 描述: 
	 * @author  qye.zheng
	 * 
	 * @param serverPath 服务器路径
	 * @param clientPath 客户端路径(可以包含文件名)
	 * @return
	 */
	public static boolean copyToServer(final String serverPath, final String clientPath)
	{
		boolean flag = false;
		try
		{
			final File clientFile = new File(clientPath);
			
			
			flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}
	

}
