/**
 * 
 */
package com.zxgs.entity;

/**
 * @Desc //TODO 分页通用返回类型
 * @author zxk
 *
 * @Date 2019年5月5日 下午5:14:45
 */
public class ObjectReturnMessage {

	/**
	 * //TODO 
	 * @auther zxk
	 * @ 2019年5月5日 下午5:14:45
	 */
	public ObjectReturnMessage() {
		// TODO Auto-generated constructor stub
	}
	
	private Object obj;
	
	private String returnMessage;
	
	private Integer totalPage;
	
	private Integer totalNumber;
	

	public Integer getTotalPage() {
		return totalPage;
	}



	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}



	public Integer getTotalNumber() {
		return totalNumber;
	}



	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}


	//含参构造方法
	public ObjectReturnMessage(Object obj, String returnMessage, Integer totalPage, Integer totalNumber) {
		super();
		this.obj = obj;
		this.returnMessage = returnMessage;
		this.totalPage = totalPage;
		this.totalNumber = totalNumber;
	}



	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}


}
