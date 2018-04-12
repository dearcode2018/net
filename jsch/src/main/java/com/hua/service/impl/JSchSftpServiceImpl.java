/**
 * JSchSftpServiceImpl.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.Vector;

import com.hua.bean.FtpParam;
import com.hua.constant.NetConstant;
import com.hua.service.SftpService;
import com.hua.util.EmptyUtil;
import com.hua.util.IOUtil;
import com.hua.util.StringUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * JSchSftpServiceImpl 描述: File Transport Protocol - 服务
 * 
 * @author qye.zheng
 */
public final class JSchSftpServiceImpl extends CoreServiceImpl implements
		SftpService {
	/**
	 * 
	 增: 上传文件、创建目录(多级) 删: 删除文件、删除目录 改: 重命名文件、重命名目录 查:
	 * 下载文件、下载目录、罗列文件/目录、是否是文件/目录
	 */

	private FtpParam param;

	private Session session;

	// private Channel channel;

	/**
	 * @description 构造方法
	 * @author qye.zheng
	 */
	public JSchSftpServiceImpl(final FtpParam param) {
		this.param = param;
		 init();
	}

	/**
	 * 
	 * @description 初始化
	 * @author qye.zheng
	 */
	private void init() {
		// 创建 JSch对象
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(param.getUsername(), param.getHost(),
					param.getPort());
			log.info("Session created.");
			// 设置密码
			if (!StringUtil.isEmpty(param.getPassword())) {
				session.setPassword(param.getPassword());
			}
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			// 为Session对象设置properties
			session.setConfig(config);
			// 设置超时时间 (单位: 毫秒) 尽量把时间定义长一些 (50秒左右 50000)
			session.setTimeout(param.getTimeout());
			// 通过 session 建立连接
			session.connect();
			log.info("Session connected.");
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @description 初始化
	 * @author qye.zheng
	 */
	public ChannelSftp getChannel() {
		ChannelSftp channel = null;
		try {
			log.info("Opening Channel");
			/**
			 * 注意: sftp 必须是小写，大写无法打开通道
			 */
			// 打开 sftp channel 通道
			channel = (ChannelSftp) session
					.openChannel(NetConstant.PROTOCOL_SFTP);
			
			// 建立SFTP通道的连接
			channel.connect();
			
			log.info("Connected successfully to ftpHost = " + param.getHost()
					+ ",as ftpUserName = " + param.getUsername()
					+ ", returning: " + channel);
			
		} catch (JSchException e) {
			e.printStackTrace();
		}

		return channel;
	}

	/**
	 * 
	 * @description 上传单个文件或目录 到指定的路径
	 * @param file
	 *            要上传的文件对象
	 * @param remote
	 *            服务器存放路径
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean upload(final File file, final String remote) {
		boolean flag = false;
		InputStream inputStream = null;
		ChannelSftp channel = null;
		try {
			inputStream = new FileInputStream(file);
			channel = getChannel();
			// 执行上传存储
			channel.put(inputStream, remote);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			close(channel);
			// 断开连接
			IOUtil.close(inputStream);
		}

		return flag;
	}

	/**
	 * 
	 * @description 创建多级目录
	 * 创建已存在的目录会抛异常
	 * @param dir
	 *            绝对目录路径，例如 /home/a/b
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean mkdirs(final String dir) {
		boolean flag = false;
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			// 创建目录
			channel.mkdir(dir);
			flag = true;
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
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
	public boolean deleteFile(final String remoteFile) {
		boolean flag = false;
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			// 删除文件
			channel.rm(remoteFile);
			flag = true;
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
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
	public boolean deleteDirectory(final String remoteDir) {
		boolean flag = false;
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			// 删除目录
			channel.rmdir(remoteDir);
			flag = true;			
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
		}

		return flag;
	}

	/**
	 * 
	 * @description 文件重命名: ("/uplo/a.txt","/uplo/b.txt") 目录重命名:
	 *              ("/upload/a","/upload/b")
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean rename(final String oldPath, final String newPath) {
		boolean flag = false;
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			// 重命名
			channel.rename(oldPath, newPath);
			flag = true;			
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
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
	public boolean isFile(String remote) {
		boolean flag = false;

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
	public boolean isDirectory(String remote) {
		boolean flag = false;

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
	public boolean isExists(String remote) {
		boolean flag = false;
		ChannelSftp channel = null;
		// 去除末尾 /
		if (remote.endsWith("/"))
		{
			remote = remote.substring(0, remote.lastIndexOf('/'));
		}
		final String name = remote.substring(remote.lastIndexOf('/') + 1);
		// 取出上一级路径
		remote = remote.substring(0, remote.lastIndexOf('/'));
		channel = getChannel();
		try {
			// 切换到指定的路径
			//channel.cd(remote);
			// 列出指定路径下所有的项
			@SuppressWarnings({ "unchecked" })
			Vector<LsEntry> v = channel.ls(remote);
			if (!EmptyUtil.isEmpty(v))
			{
				for (int i = 0; i < v.size(); i++)
				{
					if (v.get(i).getFilename().equals(name))
					{
						return true;
					}
				}
			}		
		} catch (SftpException e) {
			e.printStackTrace();
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
	public Collection<String> listFile(final String remote) {
		final Collection<String> names = new ArrayList<String>();
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			@SuppressWarnings({"unchecked"})
			Vector<LsEntry> v = channel.ls(remote);
			if (!EmptyUtil.isEmpty(v))
			{
				for (int i = 0; i < v.size(); i++)
				{
					names.add(v.get(i).getFilename());
				}
			}
			
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
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
	public Collection<String> listDirectory(final String remote) {
		final Collection<String> names = new ArrayList<String>();
		ChannelSftp channel = null;
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
	public Collection<File> listFiles(final String remote) {
		return null;
	}

	/**
	 * 统一使用 二进制流的方式下载文件
	 */
	/**
	 * 
	 * @description 下载一个文件/目录到指定的本地目录
	 * @param remote
	 * @param local
	 *            本地路径
	 * @return
	 * @author qye.zheng
	 */
	@Override
	public boolean download(final String remote, final String local) {
		boolean flag = false;
		ChannelSftp channel = null;
		try {
			channel = getChannel();
			// 重命名
			channel.get(remote, local);
			flag = true;			
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			// 断开连接
			close(channel);
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
	public String getTmpDir() {
		return System.getProperty("java.io.tmpdir");
	}

	/**
	 * 
	 * @description 关闭通道
	 * @author qye.zheng
	 */
	public void close(final Channel channel) {
		if (null != channel) {
			channel.disconnect();
		}
		if (null != session) {
			session.disconnect();
		}
	}

}
