package com.net.esdemo.model;

import java.util.List;

/** 
 * @author  作者 E-mail: issacgo@outlook.com
 * @date 创建时间：2017年12月14日 下午1:32:00
 * @explain 封装数据返回实体
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class ResultEntity {
	
	public static  final int SUCCESS=200; //请求成功
	public static  final int ERROR=500;  //请求错误
	public static  final int ACCESS_ERROR=300;//没有权限
	
	private Integer status;
	private long total;
	private String messages;
	private List<Object> resList;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public String getMessages() {
		return messages;
	}
	public void setMessages(String messages) {
		this.messages = messages;
	}
	public List<Object> getResList() {
		return resList;
	}
	public void setResList(List<Object> resList) {
		this.resList = resList;
	}
	@Override
	public String toString() {
		return "ResultEntity [status=" + status + ", total=" + total
				+ ", messages=" + messages + ", resList=" + resList
				+ "]";
	}
	
	

}
