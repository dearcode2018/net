/**
  * @filename FtpParam.java
  * @description 
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.bean;

import com.hua.bean.FtpParam;
import com.hua.util.ReadProperties;

 /**
 * @type FtpParam
 * @description ftp 参数
 * @author qye.zheng
 */
public final class FtpParam {

	private String address;

	private int port;
	
	private String username;

	private String password;
	
	private int retryCount;
	
	private String encoding;
	
	private String tmpDir;
	
	
	private String mode;

	private static FtpParam instance;
	
	private static final String CONFIG_PATH = "/conf/properties/ftp.properties";
	
	private static final ReadProperties props = new ReadProperties(CONFIG_PATH);
	
	static
	{
		instance = new FtpParam();
		instance.setAddress(props.getProperty("ftp.address"));
		instance.setPort(Integer.parseInt(props.getProperty("ftp.port")));
		instance.setUsername(props.getProperty("ftp.username"));
		instance.setPassword((props.getProperty("ftp.password")));
		instance.setRetryCount(Integer.parseInt((props.getProperty("ftp.retryCount"))));
		instance.setMode(props.getProperty("ftp.mode"));
		instance.setEncoding(props.getProperty("ftp.encoding"));
		instance.setTmpDir(props.getProperty("ftp.tempDir"));
	}
	
	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public final void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the port
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public final void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public final String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the encoding
	 */
	public final String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding the encoding to set
	 */
	public final void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return the tmpDir
	 */
	public final String getTmpDir() {
		return tmpDir;
	}

	/**
	 * @param tmpDir the tmpDir to set
	 */
	public final void setTmpDir(String tmpDir) {
		this.tmpDir = tmpDir;
	}

	/**
	 * @return the retryCount
	 */
	public final int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public final void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the mode
	 */
	public final String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public final void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the instance
	 */
	public static final FtpParam getInstance() {
		return instance;
	}
	
}
