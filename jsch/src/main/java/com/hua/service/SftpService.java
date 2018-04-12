/**
  * @filename SftpService.java
  * @description 
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.service;

import java.io.File;
import java.util.Collection;

 /**
 * @type SftpService
 * @description 
 * @author qye.zheng
 */
public interface SftpService {
	/**
	 * 
	增: 上传文件、创建目录(多级)
	删: 删除文件、删除目录
	改: 重命名文件、重命名目录
	查: 下载文件、下载目录、罗列文件/目录、是否是文件/目录
	 */

	/**
	 * 
	 * @description 上传单个文件或目录 到指定的路径
	 * @param file 要上传的文件对象
	 * @param remote 服务器存放路径
	 * @return
	 * @author qye.zheng
	 */
	public boolean upload(final File file, final String remote);

	/**
	 * 
	 * @description 上传多级目录
	 * @param dir 绝对目录路径，例如 /home/a/b
	 * @return
	 * @author qye.zheng
	 */
	public boolean mkdirs(final String dir);
	
	/**
	 * 
	 * @description 删除单个文件
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public boolean deleteFile(final String remote);
	
	/**
	 * 
	 * @description 删除单个目录
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public boolean deleteDirectory(final String remote);
	
	/**
	 * 
	 * @description 
	 * 文件重命名: ("/uplo/a.txt","/uplo/b.txt")
	 * 目录重命名: ("/upload/a","/upload/b")
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @author qye.zheng
	 */
	public boolean rename(final String oldPath, final String newPath);
	
	/**
	 * 
	 * @description 是否是文件
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public boolean isFile(final String remote);
	
	/**
	 * 
	 * @description 是否是目录
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public boolean isDirectory(final String remote);
	
	/**
	 * 
	 * @description 文件或路径是否存在
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public boolean isExists(final String remote);
	
	/**
	 * 
	 * @description 列出文件列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public Collection<String> listFile(final String remote);
	
	/**
	 * 
	 * @description 列出目录列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public Collection<String> listDirectory(final String remote);
	
	/**
	 * 
	 * @description 列出文件或目录 对象列表
	 * @param remote
	 * @return
	 * @author qye.zheng
	 */
	public Collection<File> listFiles(final String remote);
	
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
	public boolean download(final String remote, final String local);
	
	/**
	 * 
	 * @description 获取临时目录
	 * @return
	 * @author qye.zheng
	 */
	public String getTmpDir();
}
