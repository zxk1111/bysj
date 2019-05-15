package com.zxgs.service;

import com.google.gson.JsonObject;


public interface IMsShareExchangeService {
	
	JsonObject  queryByUuid(String uuid);
	
	/**
	 * 查询法人详情
	 * @param tyshyxdm
	 * @return
	 */
	JsonObject  queryFrInfo(String tyshyxdm,String name);
	
	/**
	 * 建筑物查询企业信息
	 * @param pageNo
	 * @param pageSize
	 * @param floornum
	 * @param geojson
	 * @return
	 */
	JsonObject queryJzwcxqyxx(int pageNo,int pageSize,Integer floornum,String geojson);
	
	/**
	 * 建筑物查询人口信息
	 * @param pageNo
	 * @param pageSize
	 * @param floornum
	 * @param geojson
	 * @return
	 */
	JsonObject queryJzwrkxx(int pageNo,int pageSize,String geojson);
}
