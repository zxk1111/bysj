package com.zxgs.common;

import java.util.List;

/**
 * 分页返回dto
 * @author zhongyuwen
 *
 * @param <T>
 */
public class ResultPage<T> {

	/**
	 * 结果集
	 */	
	List<T> dataList;
	/**
	 * 页号
	 */
	Integer pageIndex = 1;
	/**
	 * 每页记录数
	 */
	Integer pageSize = 10;
	/**
	 * 总记录数
	 */
	Integer totalCount;
	
	public ResultPage() {}
	
	public ResultPage(Integer pageIndex, Integer pageSize) {
		// TODO Auto-generated constructor stub
		if(pageSize != null && pageSize > 0){
			this.pageSize = pageSize;
		}
		if(pageIndex != null && pageIndex > 0){
			this.pageIndex = pageIndex;
		}
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
}
