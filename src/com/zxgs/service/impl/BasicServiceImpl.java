package com.zxgs.service.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zxgs.common.ResultPage;


/**
 * 
 * @author yanxiao
 *
 * @param <T>
 */
public class BasicServiceImpl<T> {
	
	@SuppressWarnings("unchecked")
	protected ResultPage<T> doQueryPage(Integer pageIndex, Integer pageSize,
			Object dao, Object custom) {
		// TODO Auto-generated method stub
		ResultPage<T> page = new ResultPage<T>(pageIndex, pageSize);
		try{
			Class<?> customClass = custom.getClass();
			Method setLimitStart = customClass.getMethod("setLimit", Integer.class);
			setLimitStart.invoke(custom, page.getPageSize());
			Method setLimitEnd = customClass.getMethod("setOffset", Integer.class);
			setLimitEnd.invoke(custom, (page.getPageIndex()-1)*page.getPageSize());
			
			//默认设置createTime的倒排序
			Method getOrderByClause = customClass.getMethod("getOrderByClause");
			String orderByClause = (String) getOrderByClause.invoke(custom);
			if (StringUtils.isBlank(orderByClause)) {
				Class<T> entityClass = (Class<T>) ((ParameterizedType)getClass()
						.getGenericSuperclass()).getActualTypeArguments()[0]; 
				 if (entityClass.getDeclaredFields().length > 0) {
					 boolean isFieldExist = false;
					 for (int i=0; i<entityClass.getDeclaredFields().length; i++) {
						 if ("createTime".equals(entityClass.getDeclaredFields()[i].getName())) {
							 isFieldExist = true;
							 break;
						 }
					 }
					 if (isFieldExist) {
						 Method setOrderByClause = customClass.getMethod("setOrderByClause", String.class);
						 setOrderByClause.invoke(custom, "create_time desc");
					 }
				 }
			}
			Class<?> daoClass = dao.getClass();
			Method countByExample = daoClass.getMethod("countByExample", customClass);
			Integer totalCount =(Integer) countByExample.invoke(dao, custom);
			page.setTotalCount(totalCount);
			Method selectByPage = daoClass.getMethod("selectByPage", customClass);
			List<T> list = (List<T>) selectByPage.invoke(dao, custom);
			page.setDataList(list);		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	protected ResultPage<T> doQueryPage(Object dao, Object custom) {
		// TODO Auto-generated method stub
		return this.doQueryPage(null, null, dao, custom);
	}
	
	protected ResultPage<T> getNullPage(Integer pageIndex, Integer pageSize){
		ResultPage<T> page = new ResultPage<T>(pageIndex, pageSize);
		page.setTotalCount(0);
		page.setDataList(new ArrayList<T>());
		return page;
	}
}
