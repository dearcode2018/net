/**
  * @filename ImageCodeFactory.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.factory;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.hua.codec.ImageRequestDecoder;
import com.hua.codec.ImageRequestEncoder;
import com.hua.codec.ImageResponseDecoder;
import com.hua.codec.ImageResponseEncoder;

/**
 * @type ImageCodeFactory
 * @description 
 * @author qianye.zheng
 */
public class ImageCodeFactory implements ProtocolCodecFactory
{

	private ProtocolEncoder encoder;
	
	private ProtocolDecoder decoder;
	
	/**
	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public ImageCodeFactory(boolean client)
	{
		if (client)
		{
			encoder = new ImageRequestEncoder();
			decoder = new ImageRequestDecoder();
		} else
		{
			encoder = new ImageResponseEncoder();
			decoder = new ImageResponseDecoder();
		}
	}
	
	/**
	 * @description 
	 * @param session
	 * @return
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception
	{
		return encoder;
	}

	/**
	 * @description 
	 * @param session
	 * @return
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception
	{
		return decoder;
	}

}
