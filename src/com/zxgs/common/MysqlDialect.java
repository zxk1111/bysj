package com.zxgs.common;

public class MysqlDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 50);
		pagingSelect.append("select t.* from (");
		pagingSelect.append(sql);
		pagingSelect.append(")t limit ").append(offset).append(",").append(limit);
		return pagingSelect.toString();
	}

}
