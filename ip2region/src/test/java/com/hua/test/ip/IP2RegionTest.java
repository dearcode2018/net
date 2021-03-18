/**
 * 描述: 
 * IP2RegionTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.ip;

//静态导入
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import com.hua.constant.Constant;
import com.hua.dto.IPRegionDTO;
import com.hua.test.BaseTest;
import com.hua.util.JacksonUtil;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * IP2RegionTest
 */
//@DisplayName("测试类名称")
//@Tag("测试类标签")
//@Tags({@Tag("测试类标签1"), @Tag("测试类标签2")})
public final class IP2RegionTest extends BaseTest {

	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void testIP2Region() {
		try {
			/*
			 * InputStream inputStream = getClass().getResourceAsStream("/ip2region.db");
			 * ByteArrayOutputStream outputStream = new
			 * ByteArrayOutputStream(inputStream.available()); IOUtils.copy(inputStream,
			 * outputStream);
			 */			
			String path = "C:/data/ipgeo/ip2region.db";
			DbConfig config = new DbConfig();
			config.setTotalHeaderSize(1024);
			config.setIndexBlockSize(1024);
			DbSearcher searcher = new DbSearcher(config, path);
			String ip = null;
			ip = "61.144.144.46";
			//ip = "192.168.5.130";
			/*
			 * ipv6 返回，判定其暂时不支持ipv6
			 * {"region":"0|0|0|内网IP|内网IP","dataPtr":16392,"cityId":0}
			 */
			//ip = "240e:69:3106::";
			//ip = "2002:92a:8f7a:100:10::250";
			//DbSearcher searcher = new DbSearcher(config, path);
			DataBlock data = searcher.memorySearch(ip);
			assertNotNull(data);
			//DataBlock data = searcher.binarySearch("61.144.144.46");
			//DataBlock data = searcher.btreeSearch("61.144.144.46");
			/*
			 * 中文乱码解决方案
			 * 1.对文件进行转码
			 * 2.不修改文件编码，对查询的数据进行转码 (现在采用的方式)
			 * 
			 * 是否有可以提高效率的方式
			 */
			//data.setRegion(new String(data.getRegion().getBytes(Constant.CHART_SET_GBK)));
			IPRegionDTO region = convert(new String(data.getRegion().getBytes(Constant.CHART_SET_GBK)));
			//IPRegionDTO region = convert(data.getRegion());
			System.out.println(JacksonUtil.writeAsString(region));
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}	
	
	/**
	 * 
	 * @description 
	 * @param region
	 * @return
	 * @author qianye.zheng
	 */
    private static final IPRegionDTO convert(final String region) {
    	final IPRegionDTO result = new IPRegionDTO();
        final String[] regionArr = StringUtils.split(region, Constant.OR_MARK);
        if (isNotZero(regionArr, 0)) { // 国家，如中国
        	result.setCountry(regionArr[0]);
        }
        if (isNotZero(regionArr, 2)) { // 省份，如广东省
        	result.setProvince(StringUtils.replace(regionArr[2], "省", ""));
        }
        if (isNotZero(regionArr, 3)) { // 市，如广州市
        	result.setCity(StringUtils.replace(regionArr[3], "市", ""));
        }
        
        return result;
    }
    
    /**
     * 
     * @description 
     * @param regionArr
     * @param index
     * @return
     * @author qianye.zheng
     */
    private static final boolean isNotZero(String[] regionArr, int index) {
        return regionArr.length > index && !StringUtils.equals("0", regionArr[index]);
    }
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	//@DisplayName("test")
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testTemp")
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testCommon")
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testSimple")
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("testBase")
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]开始之前运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("beforeMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@BeforeEach
	public void beforeMethod() {
		System.out.println("beforeMethod()");
	}
	
	/**
	 * 
	 * 描述: [每个测试-方法]结束之后运行
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("afterMethod")
	@Tag(" [每个测试-方法]结束之后运行")
	@AfterEach
	public void afterMethod() {
		System.out.println("afterMethod()");
	}
	
	/**
	 * 
	 * 描述: 测试忽略的方法
	 * @author qye.zheng
	 * 
	 */
	@Disabled
	@DisplayName("ignoreMethod")
	@Test
	public void ignoreMethod() {
		System.out.println("ignoreMethod()");
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@DisplayName("noUse")
	@Disabled("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(expecteds, actuals, message);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(true, message);
		assertTrue(true, message);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(expecteds, actuals, message);
		assertNotSame(expecteds, actuals, message);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(actuals, message);
		assertNotNull(actuals, message);
		
		fail();
		fail("Not yet implemented");
		
		dynamicTest(null, null);
		
		assumeFalse(false);
		assumeTrue(true);
		assumingThat(true, null);
	}

}
