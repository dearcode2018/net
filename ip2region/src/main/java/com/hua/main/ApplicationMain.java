/**
  * @filename ApplicationMain.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;

import com.hua.constant.Constant;
import com.hua.dto.IPRegionDTO;
import com.hua.util.EmptyUtil;
import com.hua.util.IPUtil;
import com.hua.util.JacksonUtil;

/**
 * @type ApplicationMain
 * @description 
 * @author qianye.zheng
 */
public final class ApplicationMain {

	/**
	 * @description 
	 * @param args
	 * @author qianye.zheng
	 */
	public static void main(String[] args) {
		if (EmptyUtil.isNotEmpty(args)) {
			for (int i = 0; i < args.length; i++) {
				System.out.println(String.format("arg[%d] = %s", i, args[i]));
			}
		}
		String path = "/usr/local/ipgeo/ip2region.db";
		try {
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
			e.printStackTrace();
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
	
}