package com.zxgs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ESUtil {
	
public static String URL = null;


public static String json_url=null;
	static {
		Properties pps = new Properties();
		InputStream in = null;
		try {
			in = ESUtil.class.getResourceAsStream("/es.properties");
			pps.load(in);
			String ip = pps.getProperty("esserver_ip");
			String webapp = pps.getProperty("esserver_webapp");
			json_url = pps.getProperty("esserver_json");
			StringBuffer sb = new StringBuffer();
			sb.append("http://").append(ip)
					.append("/").append(webapp);
			URL = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/***
	 * 查询es数据
	 * @param version 版本
	 * @param type 查询的数据类型
	 * @param queryJson 查询的json语句
	 * @return
	 */
	public static String queryEsData(String type,String queryJson){
		String queryUrl=getSearchUrl(type);
		String result = HttpClientUtil.httpPostRequestForJsonData(queryUrl,queryJson);
		return result;
	}
	
	/**
	 * 插入ES数据
	 * @param type
	 * @param json
	 * @return
	 */
	public static String insertEsData(String type,String json){
		String queryUrl=getPutUrl(type);
		System.out.println(queryUrl);
		String result = HttpClientUtil.httpPutRequestForJsonData(queryUrl,json);
		return result;
	}
	
	/***
	 * 组装ES查询地址
	 * @param version 版本
	 * @param type 类型
	 * @return
	 */
	public static String getSearchUrl(String type){
		//type=getVersionType(version,type);
		String searychUrl=URL+"/"+type+"/_search";
		return searychUrl;
	}
	
	/**
	 * 组装es 插入地址
	 * @param type
	 * @return
	 */
	public static String getPutUrl(String type){
		//type=getVersionType(version,type);
		String searychUrl=URL+"/"+type+"/";
		return searychUrl;
	}
	
	/**
	 * 组装ES JSON地址
	 * @param type
	 * @return
	 */
	public static String getJsonUrl(String json){
		//type=getVersionType(version,type);
		String searychUrl=json_url+json;
		return searychUrl;
	}
}
