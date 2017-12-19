package com.net.esdemo.test;  
  
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.net.esdemo.manager.ESCRUDManager;
import com.net.esdemo.manager.ESClientManager;
import com.net.esdemo.model.Goods;
import com.net.esdemo.model.GoodsFilter2ES;
  /**
   * 
   * @author  作者 E-mail: issacgo@outlook.com
   * @date 创建时间：2017年12月19日 下午12:02:12
   * @explain 单元测试类
   * @return
   *
   */
public class MyTest { 
	
	protected final Logger logger = LoggerFactory.getLogger(MyTest.class);
	@Autowired
	private  ESClientManager esClientManager;
	@Autowired
	private  ESCRUDManager escrudManager;
    /** 
     * 生成索引 
     * @throws UnknownHostException 
     * @throws JsonProcessingException 
     */  
    @Test  
    public void testCreatIndex() throws UnknownHostException, JsonProcessingException{
    	
        List<Goods> goodsList = new ArrayList<Goods>();  
          
        String[] r123 = {"r1","r2","r3"};  
        String[] r23 = {"r2"};  
        goodsList.add(new Goods(1L, "雀巢咖啡", r123));  
        goodsList.add(new Goods(2L, "雅哈咖啡", r23));  
        
        goodsList.add(new Goods(3L, "星巴克咖啡", r123));  
        goodsList.add(new Goods(4L, "可口可乐", r123));  
        goodsList.add(new Goods(5L, "猫屎咖啡", r123));  
          
        try {
        	escrudManager.createIndex(goodsList,"goods");  

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }  

    /** 
     * 测试search 
     * @throws JsonParseException 
     * @throws JsonMappingException 
     * @throws IOException 
     */  
    @Test  
    public void testSearch() throws JsonParseException, JsonMappingException, IOException{  
        GoodsFilter2ES filter = new GoodsFilter2ES();  
        filter.setQueryStr("咖啡");  
        filter.setRegionId("r3");  
        try {
        	Map<String, Object> result = escrudManager.searchDocHighlight("goods","name",filter,0,5);  
        	System.out.println(result.toString());
        			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
      
    }  
      
    /** 
     * 测试新增doc 
     * @throws UnknownHostException 
     * @throws JsonProcessingException 
     */  
    @Test  
    public void testAddDoc() throws UnknownHostException, JsonProcessingException{  
        //test_index 和 goods 在创建索引的时候写死了 所以这样 就传这两个值  
        String[] r = {"r2","r3"};  
        Goods goods = new Goods(5L, "新增的咖啡", r);  
        escrudManager.addDocument(esClientManager.getIndex(), "goods", goods);  
    }  
      
    /** 
     * 测试修改doc 
     * @throws UnknownHostException 
     * @throws JsonProcessingException 
     */  
    @Test  
    public void testUpdateDoc() throws UnknownHostException, JsonProcessingException{  
        String[] r = {"r2","r3"};  
        Goods goods = new Goods(5L, "修改 的咖啡", r);  
        escrudManager.updateDocument(esClientManager.getIndex(), "goods", goods);  
    }  
      
    /** 
     * 测试删除doc 
     * @throws UnknownHostException 
     * @throws JsonProcessingException 
     */  
    @Test  
    public void testDelDoc() throws UnknownHostException, JsonProcessingException{  
       
    	
    	escrudManager.deleteDocument(esClientManager.getIndex(), "goods", 5L);  
    }  
}  