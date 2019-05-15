package com.zxgs.test;

import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zxgs.service.impl.MsShareExchangeServiceImpl;
import com.zxgs.util.HttpClientUtil;

public class TestCase {
//	private String conf = "applicationContext.xml";
	
	public static void main(String[] args) throws Exception{
		String url="http://localhost:8090/zhsq_business/app/apiv3.do?areacode=500000&start=0&rows=10&method=getContentInfo&point=106.91867,29.33192&areaname=重庆市&e=5e68f7c9a2ace48b3705f52e12486485&name=彩票&type=2&code=9BEF0CAB5C344A69AF0783439B1CD7D8&os=android_pad&wyid=5001091090050000044&dataId=1";
		String result=HttpClientUtil.httpGetRequestUtf8(url);
		JsonObject jsonObject=new JsonParser().parse(result).getAsJsonObject();
		System.out.println(jsonObject.get("data").getAsJsonObject().get("path").getAsString());

	}
	
	public static void getUserNamePwd(){
//		String param="huangguohong";
		String[] arr=new String[]{"72","85","65","78","71","79"};
		int[] arr1=new int[]{0,1,2,3,4,4,1,5,0,5,3,4};
 		String param="";
		System.out.print("用户名:");
		for(int i:arr1){
			String tmp=(Integer.parseInt(arr[i])+32)+"";
			param+=String.valueOf((char)Integer.parseInt(tmp));
		}
		System.out.println(param);
		System.out.print("密码:");
		for(int i=49;i<=54;i++){
			System.out.print((char)Integer.parseInt(i+""));
		}
		
	}

//	@Test
//	public void testFindByPage() {
//		ApplicationContext ctx = 
//			new ClassPathXmlApplicationContext(conf);
//		System.out.println(ctx);
////		System.out.println(dao.findCount());
//	}
//	@Test
//	public void testFindEmpList() {
//		ApplicationContext ctx = 
//			new ClassPathXmlApplicationContext(conf);
////		serviceDao dao = ctx.getBean(serviceDao.class);
////		List<Emp> list=dao.findEmpList();
////		for(Emp emp:list){
////			System.out.println(emp.getEmp_id()+","+emp.getEmp_name()+","+emp.getEmp_job()+","+emp.getEmp_sal());
////		}
//	}
}
