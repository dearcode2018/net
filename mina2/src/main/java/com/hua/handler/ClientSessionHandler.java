/**
  * @filename ClientSessionHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.hua.entity.User;

/**
 * @type ClientSessionHandler
 * @description 
 * @author qianye.zheng
 */
public class ClientSessionHandler extends IoHandlerAdapter
{
	
	
	private boolean finished;
	
	/**
	 * @description 构造方法
	 * @author qianye.zheng
	 */
	public ClientSessionHandler()
	{
	}
	
	/**
	 * @description 
	 * @param session
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void sessionOpened(IoSession session) throws Exception
	{
		System.out.println("ClientSessionHandler.sessionOpened()");
		final User user = new User();
		user.setUsername("张三");
		//session.write(user);
		super.sessionOpened(session);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param message
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception
	{
		System.out.println("ClientSessionHandler.messageReceived()");
		super.messageReceived(session, message);
	}
	
	/**
	 * @description 
	 * @param session
	 * @param cause
	 * @throws Exception
	 * @author qianye.zheng
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception
	{
		super.exceptionCaught(session, cause);
	}

	/**
	* @return the finished
	*/
	public boolean isFinished()
	{
		return finished;
	}

	/**
	* @param finished the finished to set
	*/
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	
}
