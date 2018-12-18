/**
  * @filename ImageRequest.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.bean;

/**
 * @type ImageRequest
 * @description
 * @author qianye.zheng
 */
public class ImageRequest
{

	private int width;
	private int height;
	private int numberOfCharacters;

	public ImageRequest(int width, int height, int numberOfCharacters)
	{
		this.width = width;
		this.height = height;
		this.numberOfCharacters = numberOfCharacters;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getNumberOfCharacters()
	{
		return numberOfCharacters;
	}
}
