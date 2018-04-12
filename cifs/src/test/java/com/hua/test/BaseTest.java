/**
 * 描述: 
 * BaseTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test;

// 静态导入
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.hua.entity.SmbParam;
import com.hua.log.BaseLog;
import com.hua.util.ReadProperties;

/**
 * 描述: 测试基类
 * 包含多个测试示例
 * 
 * @author qye.zheng
 * BaseTest
 */
//@RunWith()
public class BaseTest extends BaseLog {
	
	private static final String CONFIG_PATH = "/conf/properties/smb.properties";

	private static final ReadProperties readProps = new ReadProperties(CONFIG_PATH);

	/* 共用 : 单例，没有特殊要求 直接使用该实例 */
	public static SmbParam param;
	
	// 连接url值
	public static String url;

	// 加载邮件会话共用属性
	static
	{
		final Properties props = readProps.getProps();
		param = new SmbParam(props);
		url = param.getUrl();
	}
	
	
	/**
	 * 
	 * 描述: [所有测试]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("beforeClass()");
	}
	
	/**
	 * 
	 * 描述: [所有测试]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("afterClass()");
	}

}
