package com.zxgs.model;

public class PageBase {
	protected int start;	//起始
	
	protected int rows;	//返回行数

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
