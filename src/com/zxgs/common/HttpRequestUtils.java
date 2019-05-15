package com.zxgs.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


public class HttpRequestUtils {
	/***
	 * httppost方式发送
	 * @param url 连接地址
	 * @param params 参数
	 * @return
	 */
	public static String callRestfulPost(String url, Map<String, Object> params) 
	{
		String temp;
		String ret="";
		try{
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			StringBuffer param = new StringBuffer();
			for (String key: params.keySet())
			{
				param.append(key).append("=").append(URLEncoder.encode(params.get(key).toString(), "GBK")).append("&");
				
			}
			conn.getOutputStream().write(param.toString().getBytes("GBK"));
			//System.out.println(param);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			InputStreamReader isr = new InputStreamReader(conn.getInputStream());  
            BufferedReader br = new BufferedReader(isr);  
            while((temp = br.readLine()) != null){  
                ret += temp;  
            }     
            br.close();  
            isr.close();
            conn.disconnect();
            //System.out.println(ret);
            
		} catch(java.net.SocketTimeoutException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

}
