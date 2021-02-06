/**
  * @filename ImageResponseEncoder.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.codec;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.hua.bean.ImageResponse;

/**
 * @type ImageResponseEncoder
 * @description 
 * @author qianye.zheng
 */
public class ImageResponseEncoder extends ProtocolEncoderAdapter
{

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
		ImageResponse response = (ImageResponse) message;
		byte[] bytes1 = getBytes(response.getImage1());
		byte[] bytes2 = getBytes(response.getImage2());
		int capacity = bytes1.length + bytes2.length + 8;
		IoBuffer buffer = IoBuffer.allocate(capacity, false);
		buffer.setAutoExpand(true);
		buffer.putInt(bytes1.length);
		buffer.put(bytes1);
		buffer.putInt(bytes2.length);
		buffer.put(bytes2);
		buffer.flip();
		out.write(buffer);
	}
	
	private byte[] getBytes(BufferedImage image) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", baos);
		
		return baos.toByteArray();
	}

}
