/**
  * @filename ImageRequestEncoder.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.hua.bean.ImageRequest;

/**
 * @type ImageRequestEncoder
 * @description 
 * @author qianye.zheng
 */
public class ImageRequestEncoder implements ProtocolEncoder
{
	/*
	 * 或继承 ProtocolEncoderAdaptor
	 */

	/**
	 * @description 
	 * @param session
	 * @param message
	 * @param out
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception
	{
		ImageRequest request = (ImageRequest) message;
		IoBuffer buffer = IoBuffer.allocate(12, false);		
		buffer.putInt(request.getWidth());
		buffer.putInt(request.getHeight());
		buffer.putInt(request.getNumberOfCharacters());
		buffer.flip();
		out.write(buffer);
	}

	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void dispose(IoSession session) throws Exception
	{
	}

}
