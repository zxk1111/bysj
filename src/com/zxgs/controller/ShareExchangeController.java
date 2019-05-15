package com.zxgs.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.zxgs.common.SysCodeMsg;
import com.zxgs.service.IMsElasticsearchService;
import com.zxgs.service.IMsShareExchangeService;


/**
 * 
 */
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/open/share")
public class ShareExchangeController extends WebBaseController{
//	
	@Autowired
	private IMsShareExchangeService iMsShareExchangeService;
	
	@Autowired
	private IMsElasticsearchService IiMsElasticsearchService;

//	@RequestMapping("/getUser")
//	public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		PlClientBaseModel result=new PlClientBaseModel();
//		List<SysUser> list=iSysUserService.getUser();
//		result.setData(list);
//		writeJSON(response, result);
//	}
	
	@RequestMapping("/getByUuid")
	@ResponseBody
	public void getByUuid(HttpServletRequest request, HttpServletResponse response,@RequestParam String mapguid) throws IOException {
		JsonObject result=new JsonObject();
		JsonObject json=iMsShareExchangeService.queryByUuid(mapguid);
		result.addProperty("code", SysCodeMsg.SUCCESS.getCode());
		result.addProperty("message", SysCodeMsg.SUCCESS.getDescribe());
		result.add("data", json);
		writeJSON(response, result.toString());
	}
	
	/**
	 * 查询法人信息
	 * @param request
	 * @param response
	 * @param wyid 唯一ID 统一社会信用代码
	 * test http://zhsq.digitalcq.com/zhsq_cms/static/fr/index.html?wyid=500231010100002578&name=%E5%9E%AB%E6%B1%9F%E5%8E%BF%E6%98%8E%E8%B4%B5%E5%AE%8F%E9%A3%9F%E5%93%81%E5%8A%A0%E5%B7%A5%E5%8E%82
	 * @throws IOException
	 */
	@RequestMapping("/queryFrInfo")
	@ResponseBody
	public void queryFrInfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String wyid,@RequestParam String name) throws IOException {
		JsonObject result=new JsonObject();
		JsonObject json=iMsShareExchangeService.queryFrInfo(wyid,name);
		result.addProperty("code", SysCodeMsg.SUCCESS.getCode());
		result.addProperty("message", SysCodeMsg.SUCCESS.getDescribe());
		result.add("data", json);
		writeJSON(response, result.toString());
	}
	
	/**
	 * 建筑物查询企业信息
	 * @param request
	 * @param response
	 * @param geojson 面
	 * @param pageNo 页码
	 * @param pageSize	数量
	 * @param floornum 楼层
	 * @throws IOException
	 */
	@RequestMapping("/queryJzwcxqyxx")
	@ResponseBody
	public void queryJzwcxqyxx(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String geojson,@RequestParam String pageNo,@RequestParam String pageSize
			,@RequestParam String floornum) throws IOException {
		int no=1;
		int size=10;
		Integer floor=null;
		JsonObject result=new JsonObject();
		//设置 分页默认值
		if(StringUtils.isNotBlank(pageNo)){
			no=Integer.parseInt(pageNo);
		}
		if(StringUtils.isNotBlank(pageSize)){
			size=Integer.parseInt(pageSize);
		}
		//楼层参数
		if(StringUtils.isNotBlank(floornum)){
			floor=Integer.parseInt(floornum);
		}
		JsonObject json=iMsShareExchangeService.queryJzwcxqyxx(no, size, floor, geojson);
		result.addProperty("code", SysCodeMsg.SUCCESS.getCode());
		result.addProperty("message", SysCodeMsg.SUCCESS.getDescribe());
		result.add("data", json);
		writeJSON(response, result.toString());
	}
	/**
	 * 建筑物查询人口信息
	 * @param request
	 * @param response
	 * @param geojson 面
	 * @param pageNo 页码
	 * @param pageSize	数量
	 * @throws IOException
	 */
	@RequestMapping("/queryJzwrkxx")
	@ResponseBody
	public void queryJzwrkxx(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String geojson,@RequestParam String pageNo,@RequestParam String pageSize) throws IOException {
		int no=1;
		int size=10;
		JsonObject result=new JsonObject();
		//设置 分页默认值
		if(StringUtils.isNotBlank(pageNo)){
			no=Integer.parseInt(pageNo);
		}
		if(StringUtils.isNotBlank(pageSize)){
			size=Integer.parseInt(pageSize);
		}
		JsonObject json=iMsShareExchangeService.queryJzwrkxx(no, size, geojson);
		result.addProperty("code", SysCodeMsg.SUCCESS.getCode());
		result.addProperty("message", SysCodeMsg.SUCCESS.getDescribe());
		result.add("data", json);
		writeJSON(response, result.toString());
	}

}
