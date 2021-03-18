/**
  * @filename IPRegionDTO.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.dto;

import lombok.Data;

/**
 * @type IPRegionDTO
 * @description 
 * @author qianye.zheng
 */
@Data
public final class IPRegionDTO {
	
    private static final String COUNTRY_CHINA = "中国";
    
    // 国家名称
    private String country; 
    
    // 省份
    private String province;
    
    // 城市
    private String city; 
    
    /**
     * 
     * @description 是否属于中国
     * @return
     * @author qianye.zheng
     */
    public boolean isChina() {
    	return COUNTRY_CHINA.equals(country);
    }
    
}
