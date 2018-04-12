/**
 * SmbParam.java
 * @author  qye.zheng
 * 
 * 	version 1.0
 */
package com.hua.entity;

import java.util.Properties;

import com.hua.constant.SmbConstant;

/**
 * SmbParam
 * 描述: 
 * @author  qye.zheng
 * 
 */
public final class SmbParam
{

	/* 协议 */
	private String protocol = "smb";
	
	/* 用户名 */
	private String username;
	
	/* 密码 */
	private String password;
	
	/* IP 地址 */
	private String address;
	
	/* 共享路径 共享路径必须以斜杠结束 例如 /a/b/ */
	private String sharePath;
	
	private Properties props;

	/* 参数连接成url */
	private String url;
	
	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 * 
	 */
	public SmbParam()
	{
	}
	
	/**
	 * 构造方法
	 * 描述: 
	 * @author  qye.zheng
	 * 
	 */
	public SmbParam(final Properties props)
	{
		setProps(props);
	}
	
	/**
	 * @return the protocol
	 */
	public String getProtocol()
	{
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol)
	{
		this.protocol = protocol;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the sharePath
	 */
	public String getSharePath()
	{
		return sharePath;
	}

	/**
	 * @param sharePath the sharePath to set
	 */
	public void setSharePath(String sharePath)
	{
		this.sharePath = sharePath;
	}

	/**
	 * @return the props
	 */
	public Properties getProps()
	{
		return props;
	}

	/**
	 * @param props the props to set
	 */
	public void setProps(Properties props)
	{
		this.props = props;
		// 初始化属性
		this.protocol = props.getProperty(SmbConstant.SMB_PROTOCOL);
		this.username = props.getProperty(SmbConstant.SMB_USERNAME);
		this.password = props.getProperty(SmbConstant.SMB_PASSWORD);
		this.address = props.getProperty(SmbConstant.SMB_ADDRESS);
		this.sharePath = props.getProperty(SmbConstant.SMB_SHARE_PATH);
	}

	/**
	 * 根据属性参数生成url
	 * @return the url
	 */
	public String getUrl()
	{
		url = protocol + "://" + username + ":" + password + "@" + address + sharePath;
		
		return url;
	}
	
}
