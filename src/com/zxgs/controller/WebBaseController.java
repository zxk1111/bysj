package com.zxgs.controller;

import com.zxgs.common.Constant;
import com.zxgs.js.JSBaseParameter;

/**
 * 
 */
public abstract class WebBaseController<E extends JSBaseParameter> extends BaseController<E> implements Constant {

	protected static final Integer LOG_STATES_FALSE = 1;	//日志状态 错误
	
	protected static final Integer LOG_STATES_TRUE = 0;	//日志状态 正确
	
	protected static final Integer LOG_LEVEL_SYSTEM = 0;	//系统
	
}
