package com.zxgs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zxgs.service.IMsShareExchangeService;
import com.zxgs.util.ESUtil;
import com.zxgs.util.HttpClientUtil;

@Service("IMsShareExchangeService")
public class MsShareExchangeServiceImpl implements IMsShareExchangeService{
	
	private static Logger LOG = Logger.getLogger(MsShareExchangeServiceImpl.class);
	
	@Override
	public JsonObject queryByUuid(String uuid) {
		//获取信用代码
		JsonObject jsonData=getTyshxydm(uuid);
		String shxydm=jsonData.get("tyshxydm").getAsString();
		String name=jsonData.get("zhsq_ysname").getAsString();
		//获取共享接口
		JsonObject shareConfInfo=getShareConfInfo();
		//查询共享交换信息
//		Map<String,Object> map=getShareInfo(shareConfInfo,shxydm);
		JsonObject map=getShareInfo(shareConfInfo,shxydm,name);
		return map;
	}
	
	/**
	 * 查询共享交换接口
	 * @param shareConfInfo
	 * @param shxydm
	 * @return
	 */
	private JsonObject getShareInfo(JsonObject shareConfInfo,String shxydm,String name){
		 ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
//		 Map<String,Object> res=new HashMap<String, Object>();
		 JsonObject res=new JsonObject();
		 Iterator<Entry<String, JsonElement>> it=shareConfInfo.entrySet().iterator();
		 while(it.hasNext()){
			 Entry<String, JsonElement> entry=it.next();
			 String key=entry.getKey();
			 fixedThreadPool.execute(
			 new Runnable(){
				@Override
				public void run() {
					long start=System.currentTimeMillis()/1000;
					String searchValue="";
					if(key.equals("qyzz") || key.equals("tzxmxxclmg")){
						searchValue=name;
					}else{
						searchValue=shxydm;
					}
					MsShareExchangeServiceImpl s =new MsShareExchangeServiceImpl();
					JsonObject value=s.queryShareExchange(searchValue,shareConfInfo.get(key).getAsJsonObject().get("url").getAsString(),
							shareConfInfo.get(key).getAsJsonObject().get("searchField").getAsString());
//					JsonObject jsonData=new JsonParser().parse(value).getAsJsonObject();
//					mapRes.put(key, value);
					res.add(key, value);
					long end=System.currentTimeMillis()/1000;
					LOG.info(key+":"+(end-start));
				}
				 
			 }
			 );
		 }
		 //停止线程池
		 fixedThreadPool.shutdown(); 
		 while(true){
			 if(fixedThreadPool.isTerminated()){
				 System.out.println("=======完成============");
				 System.out.println(res);
				 break;
			 }
			 try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		 }
		 return res;
	}
	
	
	/**
	 * 获取社会信用代码
	 * @return
	 */
	private JsonObject getTyshxydm(String uuid){
		try{
			String queryJson="{"+
					  "\"query\": {"+
				        "\"term\": {"+
				          "\"mapguid\": \""+uuid+"\""+
				          "}"+
				        "}"+
					"}";
			String result=ESUtil.queryEsData("zhsq_online",queryJson);
			System.out.println(queryJson);
			JsonObject jsonObject=new JsonParser().parse(result).getAsJsonObject();
			JsonArray jsonArray=jsonObject.get("hits")
					.getAsJsonObject().get("hits").getAsJsonArray();
			JsonObject jsonSource=null;
			if(jsonArray.size()>0){
				jsonSource=jsonArray.get(0)
						.getAsJsonObject();
				JsonObject jsonData=jsonSource.get("_source").getAsJsonObject();
//				String shxydm=jsonData.get("tyshxydm").getAsString();
				return jsonData;
			}
		}catch(Exception e){
			LOG.info("获取社会信用代码异常 uuid:"+uuid);
			e.printStackTrace();;
		}
		return null;
	}
	
	/**
	 * 获取共享交换接口信息
	 * @return
	 */
	private JsonObject getShareConfInfo(){
		JsonObject json=new JsonObject();
		//主要人员信息
		JsonObject zyryxxObj= new JsonObject();
		zyryxxObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLQCG/zyryxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		zyryxxObj.addProperty("searchField", "pripid.eq");
		json.add("zyryxx", zyryxxObj);
		//企业历程
		JsonObject qylcObj= new JsonObject();
		qylcObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLFMK/qylc/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		qylcObj.addProperty("searchField", "pripid.eq");
		json.add("qylc", qylcObj);
		//企业变更信息
		JsonObject qybgxxObj= new JsonObject();
		qybgxxObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLPUF/qybgxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		qybgxxObj.addProperty("searchField", "pripid.eq");
		json.add("qybgxx", qybgxxObj);
		//企业基本信息
		JsonObject qyjbxxObj= new JsonObject();
		qyjbxxObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLQT/qyjbxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		qyjbxxObj.addProperty("searchField", "pripid.eq");
		json.add("qyjbxx", qyjbxxObj);
		//企业证照
		JsonObject qyzzxoogObj= new JsonObject();
		qyzzxoogObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLCNJ/qyzzxoog/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		qyzzxoogObj.addProperty("searchField", "pripid.eq");
		json.add("qyzzxoog", qyzzxoogObj);
		//企业资质
		JsonObject qyzzObj= new JsonObject();
		qyzzObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLEFN/qyzz/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		qyzzObj.addProperty("searchField", "qymc.eq");
		json.add("qyzz", qyzzObj);
		//投资人员信息
		JsonObject tzryxxObj= new JsonObject();
		tzryxxObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLSG/tzryxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		tzryxxObj.addProperty("searchField", "pripid.eq");
		json.add("tzryxx", tzryxxObj);
		//投资项目信息
		JsonObject tzxmxxclmgObj= new JsonObject();
		tzxmxxclmgObj.addProperty("url", "http://www.cqzhsq.cn:8070/services/RES_ZHMLWG/tzxmxxclmg/TY3IAC4fBsU6PLM90rCZhw/getDataJson");
		tzxmxxclmgObj.addProperty("searchField", "dwmc.eq");
		json.add("tzxmxxclmg", tzxmxxclmgObj);
		return json;
	}
	
	/**
	 * 共享交换 查询数据
	 * @param shxydm
	 * @return
	 * @throws Exception
	 */
	public JsonObject queryShareExchange(String searchValue,String url,String searchField){
		if(StringUtils.isBlank(searchValue) || StringUtils.isBlank(url) || StringUtils.isBlank(searchField)){
			return null;
		}
		Map<String, Object> params1=new HashMap<String, Object>();
		params1.put("pageNo", 1);
		params1.put("pageSize", 100);
		params1.put("search", "[{\""+searchField+"\":\""+searchValue+"\"}]");
		try {
			String resultShareEg = HttpClientUtil.httpGetRequestUtf8(url, params1);
			
//			System.out.println(resultShareEg);
			JsonObject jsonObject=new JsonParser().parse(resultShareEg).getAsJsonObject();
			String data=jsonObject.get("getDataJsonResponse").getAsJsonObject().get("return").getAsString();
			JsonObject jsonData=new JsonParser().parse(data).getAsJsonObject();
			if(jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray().size()<=0){
				LOG.info("暂无数据,url:"+url+" 查询条件:"+searchValue+" 查询字段:"+searchField);
			}
//			Map<String, Object> result=new HashMap<String, Object>();
//			result.put("result", jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray());
//			result.put("title", jsonData.get("data").getAsJsonObject().get("title").getAsJsonArray());
			return jsonData.get("data").getAsJsonObject();
		} catch (Exception e) {
			LOG.info("共享交换数据查询异常,url:"+url+" 查询值:"+searchValue);
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JsonObject queryJzwcxqyxx(int pageNo,int pageSize,Integer floornum,String geojson) {
		String url="http://www.cqzhsq.cn:8070/services/RES_ZHMLOV/jzwcxqyxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson";
		JsonObject geoJsonObject=new JsonParser().parse(geojson).getAsJsonObject();
		JsonArray arr=geoJsonObject.get("coordinates").getAsJsonArray();
		JsonArray arr1=arr.get(0).getAsJsonArray().get(0).getAsJsonArray();
		Map<String,Object> params=new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		String search="[{\"geoextents.eq\":\"[";
		for(int i=0;i<arr1.size();i++){
			search+="{\"lat\":"+arr1.get(i).getAsJsonArray().get(1)+",";
			search+="\"lng\":"+arr1.get(i).getAsJsonArray().get(0)+"}";
			Map<String,Object> mapa=new HashMap<String, Object>();
			mapa.put("lat", arr1.get(i).getAsJsonArray().get(1));
			mapa.put("lng", arr1.get(i).getAsJsonArray().get(0));
			list.add(mapa);
			if(i != (arr1.size()-1)){
				search+=",";
			}
		}
		search+="]\"}";
		if(floornum!=null){
			search+=",{\"floornum.eq\":"+floornum+"}";
		}
		search+="]";
//		List<Object> paramEq=new ArrayList<Object>();
//		Map<String,Object> map=new HashMap<String, Object>();
//		Map<String,Object> map1=new HashMap<String, Object>();
//		map.put("geoextents.eq", list);
//		map.put("geoextents.eq", "[{ \"lat\": 29.99817, \"lng\": 105.95764 }, { \"lat\": 29.35547, \"lng\": 105.82031 }, { \"lat\": 28.96545, \"lng\": 106.65527 }, { \"lat\": 29.10828, \"lng\": 107.07275 }, { \"lat\": 29.76196, \"lng\": 107.1936 }, { \"lat\": 30.09705, \"lng\": 106.47949 }]");
//		map1.put("floornum.eq", 1);
//		paramEq.add(map);
//		paramEq.add(map1);
//		params.put("search", "[{\"geoextents.eq\":\"[{ \"lat\": 29.99817, \"lng\": 105.95764 }, { \"lat\": 29.35547, \"lng\": 105.82031 }, { \"lat\": 28.96545, \"lng\": 106.65527 }, { \"lat\": 29.10828, \"lng\": 107.07275 }, { \"lat\": 29.76196, \"lng\": 107.1936 }, { \"lat\": 30.09705, \"lng\": 106.47949 }]\"}]\"}]");
		params.put("search", search);
		params.put("pageSize", pageSize);
		params.put("pageNo", pageNo);
		
		try {
			System.out.println(params);
			String resultShareEg=HttpClientUtil.httpPostRequest(url, params);
			JsonObject jsonObject=new JsonParser().parse(resultShareEg).getAsJsonObject();
			String data=jsonObject.get("getDataJsonResponse").getAsJsonObject().get("return").getAsString();
			JsonObject jsonData=new JsonParser().parse(data).getAsJsonObject();
			if(jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray().size()<=0){
				LOG.info("暂无数据,url:"+url+" 查询条件:"+search+" 查询字段:"+"geoextents.eq");
				LOG.info(" 查询条件:"+1+" 查询字段:"+"floornum.eq");
			}
//			Map<String, Object> result=new HashMap<String, Object>();
//			result.put("result", jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray());
//			result.put("title", jsonData.get("data").getAsJsonObject().get("title").getAsJsonArray());
			System.out.println(jsonData.get("data").getAsJsonObject());
			return jsonData.get("data").getAsJsonObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JsonObject queryJzwrkxx(int pageNo, int pageSize, String geojson) {
		String url="http://www.cqzhsq.cn:8070/services/RES_ZHMLXY/jzwcxrkxx/TY3IAC4fBsU6PLM90rCZhw/getDataJson";
		JsonObject geoJsonObject=new JsonParser().parse(geojson).getAsJsonObject();
		JsonArray arr=geoJsonObject.get("coordinates").getAsJsonArray();
		JsonArray arr1=arr.get(0).getAsJsonArray().get(0).getAsJsonArray();
		Map<String,Object> params=new HashMap<String, Object>();
		List<Object> list=new ArrayList<Object>();
		String search="[{\"geoextents.eq\":\"[";
		for(int i=0;i<arr1.size();i++){
			search+="{\"lat\":"+arr1.get(i).getAsJsonArray().get(1)+",";
			search+="\"lng\":"+arr1.get(i).getAsJsonArray().get(0)+"}";
			Map<String,Object> mapa=new HashMap<String, Object>();
			mapa.put("lat", arr1.get(i).getAsJsonArray().get(1));
			mapa.put("lng", arr1.get(i).getAsJsonArray().get(0));
			list.add(mapa);
			if(i != (arr1.size()-1)){
				search+=",";
			}
		}
		search+="]\"}";
		search+="]";
//		List<Object> paramEq=new ArrayList<Object>();
//		Map<String,Object> map=new HashMap<String, Object>();
//		Map<String,Object> map1=new HashMap<String, Object>();
//		map.put("geoextents.eq", list);
//		map.put("geoextents.eq", "[{ \"lat\": 29.99817, \"lng\": 105.95764 }, { \"lat\": 29.35547, \"lng\": 105.82031 }, { \"lat\": 28.96545, \"lng\": 106.65527 }, { \"lat\": 29.10828, \"lng\": 107.07275 }, { \"lat\": 29.76196, \"lng\": 107.1936 }, { \"lat\": 30.09705, \"lng\": 106.47949 }]");
//		map1.put("floornum.eq", 1);
//		paramEq.add(map);
//		paramEq.add(map1);
//		params.put("search", "[{\"geoextents.eq\":\"[{ \"lat\": 29.99817, \"lng\": 105.95764 }, { \"lat\": 29.35547, \"lng\": 105.82031 }, { \"lat\": 28.96545, \"lng\": 106.65527 }, { \"lat\": 29.10828, \"lng\": 107.07275 }, { \"lat\": 29.76196, \"lng\": 107.1936 }, { \"lat\": 30.09705, \"lng\": 106.47949 }]\"}]\"}]");
		params.put("search", search);
		params.put("pageSize", pageSize);
		params.put("pageNo", pageNo);
		
		try {
			System.out.println(params);
			String resultShareEg=HttpClientUtil.httpPostRequest(url, params);
			JsonObject jsonObject=new JsonParser().parse(resultShareEg).getAsJsonObject();
			String data=jsonObject.get("getDataJsonResponse").getAsJsonObject().get("return").getAsString();
			JsonObject jsonData=new JsonParser().parse(data).getAsJsonObject();
			if(jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray().size()<=0){
				LOG.info("暂无数据,url:"+url+" 查询条件:"+search+" 查询字段:"+"geoextents.eq");
			}
//			Map<String, Object> result=new HashMap<String, Object>();
//			result.put("result", jsonData.get("data").getAsJsonObject().get("result").getAsJsonArray());
//			result.put("title", jsonData.get("data").getAsJsonObject().get("title").getAsJsonArray());
			System.out.println(jsonData.get("data").getAsJsonObject());
			return jsonData.get("data").getAsJsonObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception{
//		long start=System.currentTimeMillis()/1000;
//		MsShareExchangeServiceImpl se=new MsShareExchangeServiceImpl();
//		System.out.println(se.queryByUuid("1b463158-f8ed-4744-8456-566cf697a189"));
//		long end=System.currentTimeMillis()/1000;
//		System.out.println(end-start);
		MsShareExchangeServiceImpl se1=new MsShareExchangeServiceImpl();
		JsonObject jsonData=se1.getTyshxydm("5f77303f-2e1e-4172-b6e8-9d153d8cc409");
		String url="http://zhsq.digitalcq.com/zhsq_cms/open/share/queryJzwrkxx.do";
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("geojson", jsonData.get("geojson").getAsJsonObject().toString());
		System.out.println(jsonData.get("geojson").getAsJsonObject().toString());
		params.put("pageNo", 2);
		params.put("pageSize", 10);
		params.put("floornum", "");
		System.out.println(params);
		String resultShareEg=HttpClientUtil.httpPostRequest(url, params);
		System.out.println(resultShareEg);
//		se1.queryJzwrkxx(1,10,jsonData.get("geojson").getAsJsonObject().toString());
//		se1.queryJzwcxqyxx(1,10,null,jsonData.get("geojson").getAsJsonObject().toString());
//		MsShareExchangeServiceImpl s =new MsShareExchangeServiceImpl();
//		JsonObject value=s.queryShareExchange("垫江县明贵宏食品加工厂","http://www.cqzhsq.cn:8070/services/RES_ZHMLWG/tzxmxxclmg/TY3IAC4fBsU6PLM90rCZhw/getDataJson",
//				"dwmc.eq");
//		System.out.println(value);
	}

	@Override
	public JsonObject queryFrInfo(String tyshyxdm,String name) {
		//获取共享接口
		JsonObject shareConfInfo=getShareConfInfo();
		//查询共享交换信息
//		Map<String,Object> map=getShareInfo(shareConfInfo,shxydm);
		JsonObject map=getShareInfo(shareConfInfo,tyshyxdm,name);
		return map;
	}
}
