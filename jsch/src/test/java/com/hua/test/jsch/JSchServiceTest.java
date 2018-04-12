/**
 * 描述: 
 * JSchServiceTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.jsch;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.hua.bean.FtpParam;
import com.hua.service.SftpService;
import com.hua.service.impl.JSchSftpServiceImpl;
import com.hua.test.BaseTest;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * JSchServiceTest
 */
public final class JSchServiceTest extends BaseTest {

	private SftpService sftpService;


	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Before
	public void beforeMethod() {
		try {
			sftpService = new JSchSftpServiceImpl(FtpParam.getInstance());
			
		} catch (Exception e) {
			log.error("beforeMethod =====> ", e);
		}
	}


	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testJSchService() {
		try {
			
			Channel channel = new JSchSftpServiceImpl(FtpParam.getInstance()).getChannel();
			
			System.out.println(channel);
			
		} catch (Exception e) {
			log.error("testJSchService =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testChannel() {
		try {
			ChannelSftp channel = new JSchSftpServiceImpl(FtpParam.getInstance()).getChannel();
			
			log.info("testChannel =====> " + channel.getHome());
			
			log.info("testChannel =====> " + channel.pwd());
			
			
		} catch (Exception e) {
			log.error("testChannel =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testUpload() {
		try {
			// 必须是一个文件
			String remote = "/home/xtionweb/pom-new.xml";
			String local = "C:/";
			local = "C:/pom-he.xml";
			File file = new File(local);
			boolean flag = sftpService.upload(file, remote);
			
			log.info("testUpload =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testUpload =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testMkdirs() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.mkdirs(dir);
			
			log.info("testMkdirs =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testMkdirs =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testDeleteFile() {
		try {
			String remote = "/home/xtionweb/pom.xml";
			
			boolean flag = sftpService.deleteFile(remote);
			
			log.info("testDeleteFile =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testDeleteFile =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testDeleteDir() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.deleteDirectory(dir);
			log.info("testDeleteDir =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testDeleteDir =====> ", e);
		}
	}

	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testRename() {
		try {
			String oldPath = "/home/xtionweb/pom.xml";
			String newPath = "/home/xtionweb/pom-new.xml";
			boolean flag = sftpService.rename(oldPath, newPath);
			
			log.info("testRemoveFile =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testRename =====> ", e);
		}
	}

	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testIsExists() {
		try {
			String remote = "/home/xtionweb/";
			remote = "/home/xtionweba/";
			remote = "/home/xtionweb";
			remote = "/home/xtionweb/pom-new.xml";
			
			boolean flag = sftpService.isExists(remote);
			
			log.info("testRemoveFile =====> flag = " + flag);
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
	@Test
	public void testIsFile() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.mkdirs(dir);
			log.info("testIsFile =====> flag = " + flag);		
		} catch (Exception e) {
			log.error("testIsFile =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testIsDirectory() {
		try {
			
			String remote = "/home/xtionweb";
			boolean flag = sftpService.isDirectory(remote);
			log.info("testIsDirectory =====> flag = " + flag);
			
		} catch (Exception e) {
			log.error("testIsDirectory =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testListFile() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.mkdirs(dir);
			log.info("testListFile =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testListFile =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testListDirectory() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.mkdirs(dir);
			log.info("testListDirectory =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testListDirectory =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testDownload() {
		try {
			String remote = "/home/xtionweb/pom-new.xml";
			String local = "C:/";
			local = "C:/pom-he.xml";
			boolean flag = sftpService.download(remote, local);
			
			log.info("testDownload =====> flag = " + flag);
		} catch (Exception e) {
			log.error("testDownload =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
			String dir = "/home/xtionweb/a";
			boolean flag = sftpService.mkdirs(dir);
			log.info("testRemoveFile =====> flag = " + flag);
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
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
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
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}

}
