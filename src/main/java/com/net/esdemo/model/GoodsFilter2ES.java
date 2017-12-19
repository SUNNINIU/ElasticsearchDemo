package com.net.esdemo.model;  
  
/**
 *  
 * @author  作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月19日 下午12:01:26
 * @explain 用于es查询的dto 
 * @return
 *
 */
public class GoodsFilter2ES {
	
    private String regionId; // 园区UUID  不指定表示查询所有园区
  
    private String queryStr; // 条件  
  
    public String getRegionId() {  
        return regionId;  
    }  
  
    public void setRegionId(String regionId) {  
        this.regionId = regionId;  
    }  
  
    public String getQueryStr() {  
        return queryStr;  
    }  
  
    public void setQueryStr(String queryStr) {  
        this.queryStr = queryStr;  
    }  
}  