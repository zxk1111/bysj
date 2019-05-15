package com.zxgs.common;

import java.util.HashMap;
import java.util.Map;

public class SystemConstant {
	//行政区划编码信息
	public static Map<String,String> areaInfo=new HashMap<String, String>();
	
	public static final String ZHSQCORE_SPEPARATOR="|ZX|";
	
	//PHP项目名称配置信息
	public static final String PHP_NAME="cqzhsqpro";
	
	//WEB_ROOT_PATH
	public static final String WEB_ROOT_PATH="http://192.168.110.116";
	
	/**
	 * 推送模块
	 */
	//测试地址
	public static final String ZXGSPUSH_DEVPATH="zxgspush_devpath";
	//正式地址
	public static final String ZXGSPUSH_REALPATH="zxgspush_realpath";
	//推送环境 1正式 2测试
	public static final String ZXGSPUSH_DEV="zxgspush_dev";
	// 推送应用apikey
	public static final String ZXGSPUSH_APIKEY="zxgspush_apikey";
}
