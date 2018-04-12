/**
 * FtpServiceImpl.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.hua.bean.FtpParam;
import com.hua.service.FtpService;
import com.hua.util.EmptyUtil;
import com.hua.util.IOUtil;

/**
 * FtpServiceImpl
 * 描述: File Transport Protocol - 服务
 * @author  qye.zheng
 */
public final class FtpServiceImpl extends CoreServiceImpl implements
		FtpService
{
	/**
	 * 
	增: 上传文件、创建目录(多级)
	删: 删除文件、删除目录
	改: 重命名文件、重命名目录
	查: 下载文件、下载目录、罗列文件/目录、是否是文件/目录
	 */

	private FtpParam param;
	
	/**
	 * @description 构造方法
	 * @author qye.zheng
	 */
	public FtpServiceImpl(final FtpParam param) {
		this.param = param;
	}
    
	/**
	 * 
	 * @description 上传单个文件或目录 到指定的路径
	 * @param file 要上传的文件对象
	 * @param remote 服务器存放路径
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean upload(final File file, final String remote)
	{
		boolean flag = false;
		InputStream inputStream = null;
		FTPClient ftpClient = getFtpClient();
		try {
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			int code = ftpClient.cwd(remote);
			
			if (550 == code)
			{
				// 目录不存在，创建目录
				ftpClient.makeDirectory(remote);
				ftpClient.cwd(remote);
			}
			// 设置为二进制流方式
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			inputStream = new FileInputStream(file);
			// 执行上传存储
			flag = ftpClient.storeFile(remote, inputStream);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
			IOUtil.close(inputStream);
		}
		
		return flag;
	}

	/**
	 * 
	 * @description 上传多级目录
	 * @param dir 绝对目录路径，例如 /home/a/b
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean mkdirs(final String dir)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		try
		{
			flag = ftpClient.makeDirectory(dir);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 删除单个文件
	 * @param remoteFile
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean deleteFile(final String remoteFile)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		try
		{
			flag = ftpClient.deleteFile(remoteFile);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 删除单个目录
	 * @param remoteDir
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean deleteDirectory(final String remoteDir)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		try
		{
			flag = ftpClient.removeDirectory(remoteDir);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 
	 * 文件重命名: ("/uplo/a.txt","/uplo/b.txt")
	 * 目录重命名: ("/upload/a","/upload/b")
	 * @param fromPath
	 * @param toPath
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean rename(final String fromPath, final String toPath)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		try
		{
			flag = ftpClient.rename(fromPath, toPath);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}	
	
	/**
	 * 
	 * @description 是否是文件
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean isFile(String remote)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		// 去掉最后一个 / 
		if (remote.endsWith("/"))
		{
			remote = remote.substring(0, remote.lastIndexOf("/"));
		}
		final String name = remote.substring(remote.lastIndexOf("/") + 1);
		// 获取上一级路径值
		remote = remote.substring(0, remote.lastIndexOf("/"));
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			ftpClient.cwd(remote);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			if (!EmptyUtil.isEmpty(ftpFiles))
			{
				for (FTPFile f : ftpFiles)
				{
					if (name.equals(f.getName()))
					{
						return f.isFile();
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}	
	
	/**
	 * 
	 * @description 是否是目录
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean isDirectory(String remote)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		// 去掉最后一个 / 
		if (remote.endsWith("/"))
		{
			remote = remote.substring(0, remote.lastIndexOf("/"));
		}
		final String name = remote.substring(remote.lastIndexOf("/") + 1);
		// 获取上一级路径值
		remote = remote.substring(0, remote.lastIndexOf("/"));
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			ftpClient.cwd(remote);
			FTPFile[] ftpFiles = ftpClient.listFiles();
			if (!EmptyUtil.isEmpty(ftpFiles))
			{
				for (FTPFile f : ftpFiles)
				{
					if (name.equals(f.getName()))
					{
						return f.isDirectory();
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 文件或路径是否存在
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean isExists(final String remote)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			FTPFile[] ftpFiles = ftpClient.listFiles(remote);
			
			return ftpFiles.length > 0;
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		
		return flag;
	}
	
	/**
	 * 
	 * @description 列出文件列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public Collection<String> listFile(final String remote)
	{
		final Collection<String> names = new ArrayList<String>();
		FTPClient ftpClient = getFtpClient();
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			final FTPFile[] ftpFiles = ftpClient.listFiles(remote);
			if (!EmptyUtil.isEmpty(ftpFiles))
			{
				for (FTPFile f : ftpFiles)
				{
					if (f.isFile())
					{
						names.add(f.getName());
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		return names;
	}
	
	/**
	 * 
	 * @description 列出目录列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public Collection<String> listDirectory(final String remote)
	{
		final Collection<String> names = new ArrayList<String>();
		FTPClient ftpClient = getFtpClient();
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			final FTPFile[] ftpFiles = ftpClient.listFiles(remote);
			if (!EmptyUtil.isEmpty(ftpFiles))
			{
				for (FTPFile f : ftpFiles)
				{
					if (f.isDirectory())
					{
						names.add(f.getName());
					}
				}
			}
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
		}
		return names;
	}
	
	/**
	 * 
	 * @description 列出文件或目录 对象列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public Collection<File> listFiles(final String remote)
	{
		return null;
	}
	
	/**
	 * 统一使用 二进制流的方式下载文件
	 */
	/**
	 * 
	 * @description 下载一个文件/目录到指定的本地目录
	 * @param remote
	 * @param local 本地路径
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean download(final String remote, final String local)
	{
		boolean flag = false;
		FTPClient ftpClient = getFtpClient();
		// 不存在
		if (!isExists(remote))
		{
			return flag;
		}
		OutputStream localOutputStream = null;
		try
		{
			// 切换到根目录
			ftpClient.cwd("/");
			// 切换到目标路径
			ftpClient.cwd(remote);
			// 输出到本地 outputStream
			localOutputStream = new FileOutputStream(local);
			// 设置为二进制流的方式
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.retrieveFile(remote, localOutputStream);
			// 传输完成后，还原默认设置
			//ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
		} catch (IOException e) 
		{
			e.printStackTrace();
		} finally 
		{
			// 断开连接
			disconnect(ftpClient);
			IOUtil.close(localOutputStream);
		}
		
		return flag;
	}	
	
	/**
	 * 
	 * @description 获取临时目录
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public String getTmpDir()
	{
		return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * 
	 * @description 在指定尝试次数内获取 ftp 客户端对象
	 * @return
	 * @author qye.zheng
	 */
	public FTPClient getFtpClient()
	{
		FTPClient ftpClient = null;
		int i = 0;
		while (null == ftpClient && i < param.getRetryCount())
		{
			ftpClient = createFtpClient();
			i++;
		}
		
		return ftpClient;
	}
	
	/**
	 * 
	 * @description 创建 ftp 客户端
	 * @return
	 * @author qye.zheng
	 */
	private FTPClient createFtpClient()
	{
		FTPClient ftpClient = null;
		ftpClient = new FTPClient();
		try {
			// 登录之前设置 编码
			ftpClient.setControlEncoding(param.getEncoding());
			// 执行连接
			ftpClient.connect(param.getHost(), param.getPort());
			//ftpClient.connect(param.getAddress());
			// 登录
			ftpClient.login(param.getUsername(), param.getPassword());
			// 获取响应码
			int reply = ftpClient.getReplyCode();
			// 判断响应是否正常
			if (!FTPReply.isPositiveCompletion(reply))
			{
				throw new RuntimeException("connection error,reply for:" + reply);
			}
			if ("PASSIVE".equalsIgnoreCase(param.getMode()))
			{
				// 本地被动模式
				ftpClient.enterLocalPassiveMode();
			} else
			{
				// 本地主动模式
				ftpClient.enterLocalActiveMode();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			// 发生异常则断开连接
			disconnect(ftpClient);
		}
		
		return ftpClient;
	}
	
	/**
	 * 
	 * @description 断开连接
	 * @param ftpClient
	 * @return
	 * @author qye.zheng
	 */
	private boolean disconnect(FTPClient ftpClient)
	{
		boolean flag = false;
		try {
			if (null != ftpClient)
			{
				ftpClient.disconnect();
			}
			// 将对象置空
			ftpClient = null;
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
}
