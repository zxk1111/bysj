package com.zxgs.util;
/**
 * 客户端访问返回的基础类
 * @author huanggh
 *
 */
public class PlClientBaseModel {
	/**
	 * 返回代码,200成功，非200则返回相关错误code
	 */
	private String code="200";
	/**
	 * 返回消息
	 */
	private String message="Success";
	/**
	 * 返回的数据
	 */
	private Object data;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
