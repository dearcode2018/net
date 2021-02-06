/**
  * @filename ImageRequestDecoder.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.hua.bean.ImageRequest;

/**
 * @type ImageRequestDecoder
 * @description 
 * @author qianye.zheng
 */
public class ImageRequestDecoder extends CumulativeProtocolDecoder
{

	/*
	 * CumulativeProtocolDecoder 增量协议解码器
	 */
	
	/**
	 * @description 
	 * @param session
	 * @param in
	 * @param out
	 * @return
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception
	{
		if (in.remaining() >= 12)
		{
			int width = in.getInt();
			int height = in.getInt();
			int numberOfCharacters = in.getInt();
			ImageRequest request = new ImageRequest(width, height, numberOfCharacters);
			out.write(request);
			
			return true;
		}
		
		return false;
	}

}
