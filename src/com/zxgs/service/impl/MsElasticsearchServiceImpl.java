package com.zxgs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zxgs.service.IMsElasticsearchService;
import com.zxgs.util.ESUtil;

@Service("IMsElasticsearchService")
public class MsElasticsearchServiceImpl implements IMsElasticsearchService{
	
	@Override
	public String queryOnlineByUuid(String uuid) {
		String queryJson="{"+
				  "\"query\": {"+
			        "\"term\": {"+
			          "\"mapguid\": \""+uuid+"\""+
			          "}"+
			        "}"+
				"}";
		String result=ESUtil.queryEsData("zhsq_online",queryJson);
//				String result=ESUtil.queryEsData("zhsq_xzqh",queryJson);
		JsonObject jsonObject=new JsonParser().parse(result).getAsJsonObject();
		JsonArray jsonArray=jsonObject.get("hits")
				.getAsJsonObject().get("hits").getAsJsonArray();
		JsonObject jsonSource=null;
		if(jsonArray.size()>0){
			jsonSource=jsonArray.get(0)
					.getAsJsonObject();
			JsonObject jsonData=jsonSource.get("_source").getAsJsonObject();
			return jsonData.toString();
		}
		return "";
	}
}
