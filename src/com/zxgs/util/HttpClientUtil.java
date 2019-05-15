package com.zxgs.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/***
 * http请求工具类
 * @author maleibo
 *
 */
public class HttpClientUtil {
	private static PoolingHttpClientConnectionManager cm;
//	private static final int MAX_CLIENT_CONNECTION = 500;
	private static final int MAX_CLIENT_CONNECTION = 1000;
	private static String EMPTY_STR = "";
	private static int ERROR_STATUS = 500;
	private static String UTF_8 = "UTF-8";
	/**请求超时时间**/
	private static final int CONNECT_TIME_OUT=10*1000;
	/**获取链接超时时间**/
	private static final int CONNECT_REQUEST_TIME_OUT=10*1000;
	/**传输数据超时时间**/
	private static final int SOCKET_TIME_OUT=3000*1000;

	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(MAX_CLIENT_CONNECTION);// 整个连接池最大连接数
			//每路由最大连接数，默认值是2.根据连接到的主机对MaxTotal的一个细分,比如：
			//MaxtTotal=400 DefaultMaxPerRoute=200
			//而我只连接到http://sishuok.com时，到这个主机的并发最多只有200；而不是400；
			//而我连接到http://sishuok.com 和 http://qq.com时，到每个主机的并发最多只有200；即加起来是400(但不能超过400);
			//所以起作用的设置是DefaultMaxPerRoute。
			cm.setDefaultMaxPerRoute(MAX_CLIENT_CONNECTION);
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		CloseableHttpClient httpClient=HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;
	}
	
	private static RequestConfig getRequestConfig(){
		RequestConfig requestConfig = RequestConfig.custom()  
		        .setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)  
		        .setSocketTimeout(SOCKET_TIME_OUT).build();
		return requestConfig;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGetRequest(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResult(httpGet);
	}
	
	public static String httpGetRequestUtf8(String url) {
		HttpGet httpGet = new HttpGet(url);
		return getResultUtf8(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}
	
	public static String httpGetRequestUtf8(String url, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);
		
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);
		
		HttpGet httpGet = new HttpGet(ub.build());
		return getResultUtf8(httpGet);
	}
	
	public static int httpGetRequestStatus(String url, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);
		
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);
		
		HttpGet httpGet = new HttpGet(ub.build());
		return getResultStatus(httpGet);
	}

	public static String httpGetRequest(String url,
			Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}
		return getResult(httpGet);
	}

	public static String httpPostRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url,
			Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);

		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), String.valueOf(param.getValue()));
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

		return getResult(httpPost);
	}

	/***
	 * http post发送json数据请求
	 * @param url
	 * @param json json字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String httpPostRequestForJsonData(String url, String json){
		HttpPost httpPost = new HttpPost(url);
		// 设置http头
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
		return getResult(httpPost);
	}
	
	/**
	 * 删除操作
	 * @param url
	 */
	public static void httpDelete(String url) {
		HttpDelete hd = new HttpDelete(url);
		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(hd);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/****
	 * 组装http请求参数
	 * @param params
	 * @return
	 */
	private static ArrayList<NameValuePair> covertParams2NVPS(
			Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), String
					.valueOf(param.getValue())));
		}

		return pairs;
	}

	/**
	 * 处理Http结果请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) {
//		CloseableHttpClient httpClient = getHttpClient();
		CloseableHttpClient httpClient=HttpClients.createDefault();
		request.setConfig(getRequestConfig());
		CloseableHttpResponse response=null;
		try {
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			request.releaseConnection();
			try {
				httpClient.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(response != null) { 
			    try {
			    	response.close();
					EntityUtils.consume(response.getEntity());//释放连接
				} catch (IOException e) {
					e.printStackTrace();
				} 
			  }
		}

		return EMPTY_STR;
	}
	
	private static String getResultUtf8(HttpRequestBase request) {
		CloseableHttpClient httpClient = getHttpClient();
		request.setConfig(getRequestConfig());
		CloseableHttpResponse response=null;
		try {
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity,UTF_8);
				response.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null) { 
				try {
					EntityUtils.consume(response.getEntity());//释放连接
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return EMPTY_STR;
	}
	
	private static int getResultStatus(HttpRequestBase request) {
		CloseableHttpClient httpClient = getHttpClient();
		request.setConfig(getRequestConfig());
		CloseableHttpResponse response=null;
		try {
			response = httpClient.execute(request);
			return response.getStatusLine().getStatusCode();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(response != null) { 
				try {
					EntityUtils.consume(response.getEntity());//释放连接
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
		}
		
		return ERROR_STATUS;
	}
	
	/***
	 * http put发送json数据请求
	 * @param url
	 * @param json json字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String httpPutRequestForJsonData(String url, String json){
		HttpPut httpPost = new HttpPut(url);
		// 设置http头
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
		
		return getResult(httpPost);
	}
	
	/***
	 * http put发送json数据请求
	 * @param url
	 * @param json json字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String httpPutRequestForJsonDataStatus(String url, String json){
		HttpPut httpPost = new HttpPut(url);
		// 设置http头
		httpPost.addHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Accept", "application/json");
		httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
		
		return getResultStatus(httpPost)+"";
	}
	
	public static void main(String []args){
		String url="http://106.14.65.58:8100/gyjzw/_search";
		String json="{\"query\":{\"match_all\":{}}}";
		System.out.println(httpPostRequestForJsonData(url,json));
		
	}
}
