package com.zxgs.common;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zxgs.util.PlClientBaseModel;


public class Util {
	/**
	 * 推荐码生成基础数值
	 */
	public static final int baseValue = 3000;
	/***
	 * 默认36进制转换
	 */
	public static final int systemNum = 36;
	/***
	 * 推荐码最小长度
	 */
	public static final int minLenth = 4;
	final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
	
	/***
	 * 根据用户id返回用户推荐码
	 * @param userid
	 * @return
	 */
	public static String getRecomcode(int userid){
		String recomcode=toSystemString(baseValue+userid,36,4);
		return recomcode;
	}
	
	/***
	 * 将十进制数转成任意(num)进制数
	 * @param i 十进制数字
	 * @param num 需要转到num进制数,最大支持36进制
	 * @param minLength 返回的最小字符长度,不够的补0
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String toSystemString(int i,int num,int minLength){
		if(num>36){//最大支持36进制
			num=36;
		}
		StringBuffer sb = new StringBuffer();
		for (int j = 0; i >= num; j++) {
			int a = i % num;
			i /= num;
			sb.append(digits[a]);
		}
		sb.append(digits[i]);
		String str=sb.reverse().toString();
		//如果返回字符窜不够最小位数,则前面用0补位
		int strlength=str.length();
		if(strlength<minLength){
			for(int j=strlength;j<minLength;j++){
				str="0"+str;
			}
		}
		return str;
	}
	

	/**
	 * @param type
	 *            0 获取原始的uuid,1 获取不带中间-的32位的纯字符的uuid
	 * @return
	 */
	public static String getUuid(String type) {
		String uuid = UUID.randomUUID().toString();
		if ("1".equals(type)) {
			uuid = uuid.replace("-", "");
		}
		return uuid;
	}

	

	/**
	 * 字符串是否为空串，包括本身字符串为null以及""
	 * 
	 * @param target
	 *            目标串
	 * @return 目标串是否为空串
	 * @author huangzhen
	 */
	public static boolean isNullString(String target) {
		if (target == null || "".equals(target.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Date转换为字符串
	 * @param date
	 * @return
	 * @author 
	 */
	public static String dateFormat(Date date,String pattern) {
		if (date == null||isNullString(pattern)) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 转换指定日期格式
	 * @param str 日期字符窜
	 * @param pattern 日期格式
	 * @return
	 */
	public static Date dateFormat(String str,String pattern) {
		if (isNullString(str)||isNullString(pattern)) {
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date=null;
		try {
			date=format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	 /** 
     * 日期格式字符串转换成时间戳 
     * @param date 字符串日期 
     * @param format 如：yyyy-MM-dd HH:mm:ss 
     * @return 
     */  
    public static String date2TimeStamp(String date_str,String format){  
        try {  
            SimpleDateFormat sdf = new SimpleDateFormat(format);  
            return String.valueOf(sdf.parse(date_str).getTime()/1000);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return "";  
    }  
	
	/**
	 * 获取两个日期之间的日期差
	 * @param str 日期字符窜
	 * @param pattern 日期格式
	 * @return
	 */
	public static Integer betweenDates(Date start,Date end) {
		long between=(end.getTime()-start.getTime())/1000;//除以1000是为了转换成秒

		long day1=between/(24*3600);
		long hour1=between%(24*3600)/3600;
		long minute1=between%3600/60;
		long second1=between%60/60;
		System.out.println(""+day1+"天"+hour1+"小时"+minute1+"分"+second1+"秒");
		return 0;
	}
	
	/***
	 * java生成随机数
	 * @param number
	 * @return
	 */
	public static String randomNumber(int number) {
		String strNumber="";
		if(number<0){//默认最小为1
			number=1;
		}
		if(number>100){//最大为100
			number=100;
		}
		for(int i=0;i<number;i++){
			strNumber+=""+(int)(Math.random()*10);
		}
		return strNumber;
	}
	
	public static void main(String[] args) {
		System.out.println(randomNumber(6));
	}
	
	/**   
     *  复制单个文件   
     *  @param  oldPath  String  原文件路径  如：c:/fqf.txt   
     *  @param  newPath  String  复制后路径  如：f:/fqf.txt   
     *  @return  boolean   
     */    
   @SuppressWarnings("resource")
public  void  copyFile(String  oldPath,  String  newPath)  {    
       try  {    
//           int  bytesum  =  0;    
           int  byteread  =  0;    
           File  oldfile  =  new  File(oldPath);    
           if  (oldfile.exists())  {  //文件存在时    
               InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件   
               FileOutputStream  fs  =  new  FileOutputStream(newPath);    
               byte[]  buffer  =  new  byte[1444];    
//               int  length;    
               while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {    
//                   bytesum  +=  byteread;  //字节数  文件大小    
//                   System.out.println(bytesum);    
                   fs.write(buffer,  0,  byteread);    
               }    
               inStream.close();    
           }    
       }    
       catch  (Exception  e)  {    
           System.out.println("复制单个文件操作出错");    
           e.printStackTrace();    
   
       }    
   }    
   
   /**   
    *  删除文件   
    *  @param  filePathAndName  String  文件路径及名称  如c:/fqf.txt   
    *  @param  fileContent  String   
    *  @return  boolean   
    */    
  public  void  delFile(String  filePathAndName)  {    
      try  {    
          String  filePath  =  filePathAndName;    
          filePath  =  filePath.toString();    
          java.io.File  myDelFile  =  new  java.io.File(filePath);    
          myDelFile.delete();    
  
      }    
      catch  (Exception  e)  {    
          System.out.println("删除文件操作出错");    
          e.printStackTrace();    
  
      }    
  }    
  
  /**   
   *  移动文件到指定目录   
   *  @param  oldPath  String  如：c:/fqf.txt   
   *  @param  newPath  String  如：d:/fqf.txt   
   */    
 public void  moveFile(String  oldPath,  String  newPath)  {    
     copyFile(oldPath,  newPath);    
     delFile(oldPath);    
 }    
 
 /**
  * @param curpage	第几页
  * @param pagecount	每页条数
  * @param allCount	共有多少条
  * @return
  */
 public static Map<String,Object> getPageInfo(int curpage,int pagecount,int allCount){
	 Map<String,Object> map=new HashMap<String, Object>();
	 	map.put("pagesize", Math.ceil(allCount % pagecount));
	 	map.put("allCount", allCount);
	 	map.put("curpage", curpage);
	 	map.put("pagecount", pagecount);
	 	map.put("pageStart", (curpage-1)*pagecount);
	 	map.put("pageEnd", pagecount);
	 return map;
 }
 
 public static String logFormat(String message){
	 Date data=new Date();
	 String str="\r\n"+data.toString()+" INFO[]:"+message;
	 return str;
 }
 
//判断是否是数字
	public static boolean isNumeric(String str){
		  for (int i = 0; i < str.length(); i++){
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
	}
 

	/**
	 * 将区域编码解析成范围
	 * @param areacode
	 * @param type type表示周边，type=2表示下一级
	 * @return
	 */
	public static String areacodeToCondition(long areacode,int type){
		String result="";
		String code="";
		if(type==1){
			if(areacode==500000){
				result="";
			}else if((areacode+"").length()==6){
				code=(areacode+"").substring(0,3);
			}else if((areacode+"").length()==9){
				code=(areacode+"").substring(0,6);
			}else if((areacode+"").length()==12){
				code=(areacode+"").substring(0,9);
			}
			result="["+code+"001"+" TO " +code+"999"+"]";
		}else if(type==2){
			if(areacode==500000){
				code=(areacode+"").substring(0,3);
				result="["+code+"001"+" TO " +code+"999"+"]";
			}else if((areacode+"").length()==6){
				result="["+areacode+"001"+" TO " +areacode+"999"+"]";
			}else if((areacode+"").length()==9){
				result="["+areacode+"001"+" TO " +areacode+"999"+"]";
			}
		}
		return result;
	}
	
	/** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param formatStr 
     * @return 
     */  
    public static String timeStamp2Date(String seconds,String format) {  
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";  
        SimpleDateFormat sdf = new SimpleDateFormat(format);  
        return sdf.format(new Date(Long.valueOf(seconds+"000")));  
    }  
    
    /**
     * 实体转换为Map
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
    }
    
    /**
     * 获取模板Id
     * 1，无行政区划编码为通用模板7
     * 2，六位编码 500000为全市，
     * 3，500001 501000，502000，503000，504000，505000为五大功能区
     * 4，500002为主城层级
     * 6，其余六位为区县层级
     * 7，九位编码 第七位为0街道，2乡，其余镇
     * 8，12位编码 第十位为2，3是村，0和其他未社区
     * @param areaCode
     * @return
     */
//    public static String getTemplateId(String areaCode){
//		String templateId="";
//		
//		//根据区域编码获取模板 ,6.0.4版本修改统一使用通用模板
//		if(Util.isNullString(areaCode) || !Util.isNullString(areaCode)){
//			//通用模板
//			templateId="7";	
//			return templateId;
//		}else if(areaCode.length()==6){
//				if("500000".equals(areaCode)){
//					//全市
//					templateId="1";
//				}else if("500001".equals(areaCode)){
//					//五大功能区父级
//					templateId="2";
//				}else if("500002".equals(areaCode)){
//					//主城区层级
//					templateId="3";
//				}else{
//					//五大功能区编码
//					String[] fiveFun=new String[]{"501000","502000","503000","504000","505000"};
//					List<String> list=Arrays.asList(fiveFun);
//					if(list.contains(areaCode)){
//						templateId="2";
//					}else{
//						//区县层级
//						templateId="4";
//					}
//				}
//		}else if(areaCode.length()==9){
//			String flag=areaCode.substring(6,7);
//			//第七位为0的是街道
//			if("0".equals(flag)){
//				templateId="6";
//			}else if("2".equals(flag)){
//				//第七位为2的是乡
//				templateId="8";
//			}else{
//				//其余的为镇
//				templateId="5";
//			}
//		}else if(areaCode.length()==12){
//			String flag=areaCode.substring(9,10);
//			//第十位为2或3的是村
//			if("2".equals(flag) || "3".equals(flag)){
//				templateId="10";
//			}else{
//				//第十位为0或其他的是社区
//				templateId="9";
//			}
//		}
//		return templateId;
//}
    
    /** 
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0 
     * @param version1 
     * @param version2 
     * @return 
     */  
    public static int compareVersion(String version1, String version2) throws Exception {  
        if (version1 == null || version2 == null) {  
            throw new Exception("compareVersion error:illegal params.");  
        }  
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；  
        String[] versionArray2 = version2.split("\\.");  
        int idx = 0;  
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值  
        int diff = 0;  
        while (idx < minLength  
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度  
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符  
            ++idx;  
        }  
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；  
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;  
        return diff;  
    } 
    
    public static  PlClientBaseModel requestHttpUrl(String urlStr,Map<String,String> param){
		BufferedReader in = null;
		PlClientBaseModel rnData=new PlClientBaseModel();
//		String content="";
		try{
					//先建立连接
					HttpURLConnection conn=null;
//					System.setProperty("http.agent", "");
					PrintWriter printWriter=null;
						URL url=new URL(urlStr);
						conn = (HttpURLConnection) url.openConnection();  
//						conn.setRequestProperty("ContentType","text/xml;charset=utf-8");
						conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
						conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
						conn.setRequestProperty("Accept-Language","zh-cn,zh;q=0.5");
						conn.setRequestProperty("Accept-Encoding","gzip, deflate");
						conn.setRequestProperty("Connection","keep-alive");
						conn.setRequestProperty("Upgrade-Insecure-Requests","1");
						conn.setReadTimeout(10000);
						conn.setRequestMethod("GET");  
						conn.setDoOutput(true);  
						conn.setDoInput(true);  
						printWriter = new PrintWriter(conn.getOutputStream());  
						//拼接参数
//						Set<String> set=param.keySet();
//						for(String str:set){
//							if(!Util.isNullString(content)){
//								content+="&"+str+"="+URLEncoder.encode(param.get(str), "utf-8");
//							}else{
//								content+=str+"="+URLEncoder.encode(param.get(str), "utf-8");
//							}
//						}
//						 printWriter.write(content);  
						   printWriter.flush();          
						   StringBuilder sb = new StringBuilder();   
						   try{     
							   System.out.println(conn.getInputStream());
						       in = new BufferedReader( new InputStreamReader(conn.getInputStream(),"UTF-8") );   
						   String str = null;    
						   while((str = in.readLine()) != null) {    
						       sb.append( str );     
						   }    
						  rnData.setData(sb);
				}catch (Exception ex) {  
					ex.printStackTrace();
				} finally{    
				 try{   
				     conn.disconnect();  
				     if(in!=null){  
				         in.close();  
				     }  
				     if(printWriter!=null){  
				         printWriter.close();  
				     }  
			 }catch(IOException ex) {     
			     throw ex;   
			 }
		}
		} catch(Exception ex){
			ex.printStackTrace();
			rnData.setCode("-1");
			rnData.setMessage("请求异常");
			return rnData;
		} 
		return rnData;
	}
    
    /**
     * 外部POI数据字段对应关系转换 dockPoiCore
     * @param map
     * @return
     */
    public Map<String,String> getDockPoiFiled(Map<String,String> map){
    	Map<String,String> result=new HashMap<String, String>();
    	//封装solr字段信息
		Set<String> set=map.keySet();
//		Set<String> fields=new HashSet<String>();
		for(String field:set){
			if("id".equals(field)){
				result.put("id", map.get(field));
			}else if("名称".equals(field)){
				result.put("DOCK_NAME", map.get(field));
			}else if("经度".equals(field)){
				result.put("DOCK_X", map.get(field));
			}else if("纬度".equals(field)){
				result.put("DOCK_Y", map.get(field));
			}else{
				result.put(field, map.get(field)+"_s");
			}
		}
    	return result;
    }
    
    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toUpperCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toUpperCase());
            }
        }
        return result.toString();
    }
    
	public static String getRandomString(int i) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < i; j++) {
			sb.append(String.valueOf(RandomUtils.nextInt(10)));
		}
		return sb.toString();
	}

	public static String stringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 获取6个随机字符串
	 * @return
	 */
	public static String getStringRandom(){
		 String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		 String result="";
	        char[] rands = new char[6]; 
	        for (int i = 0; i < rands.length; i++) 
	        { 
	            int rand = (int) (Math.random() * a.length()); 
	            rands[i] = a.charAt(rand); 
	        } 
	        for(int i=0;i<rands.length;i++){
	        	result+=rands[i];
	        }
	        return result;
	}
	
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	  public static void sendGet(String url) throws ParseException {  
	        CloseableHttpClient httpclient = HttpClients.createDefault();  
	        try {  
	            // 创建httpget.    
	            HttpGet httpget = new HttpGet(url);
	            System.out.println("executing request " + httpget.getURI());  
	            // 执行get请求.    
	            CloseableHttpResponse response = httpclient.execute(httpget);  
	            try {  
	                // 获取响应实体    
	                HttpEntity entity = response.getEntity();  
	                System.out.println("--------------------------------------");  
	                // 打印响应状态    
	                System.out.println(response.getStatusLine());  
	                if (entity != null) {  
	                    // 打印响应内容长度    
	                    System.out.println("Response content length: " + entity.getContentLength());  
	                    // 打印响应内容    
	                    System.out.println("Response content: " + EntityUtils.toString(entity));  
	                }  
	                System.out.println("------------------------------------");  
	            } finally {  
	                response.close();  
	            }  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            // 关闭连接,释放资源    
	            try {  
	                httpclient.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	  
	  public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    }    
	  
	  /**
	   * 获取所有文件绝对地址
	   * @param path
	   * @param fileName
	   */
	  public static void getAllFileName(String path,List<String> fileName)
	    {
		  	String sp=File.separator;
	        File file = new File(path);
	        File [] files = file.listFiles();
	        String [] names = file.list();
	        if(names != null){
	        	//拼接为绝对地址
	        	for(String n:names){
	        		fileName.add(path+sp+n);
	        	}
//	        	fileName.addAll(Arrays.asList(names));
	        }
	        for(File a:files)
	        {
	            if(a.isDirectory())
	            {
	                getAllFileName(a.getAbsolutePath(),fileName);
	            }
	        }
	    }
	  
	  /**
	   * 处理EUditor content有效信息
	   * @param str
	   * @return
	   */
	 public static String preseEUditorHtml(String str){
		 String head="</head>";
		 if(StringUtils.isEmpty(str)){
			 return "";
		 }
		 str=str.replace("<html>", "").replace("</html>", "");
		 if(str.indexOf(head)>=0){
			 str=str.substring(str.indexOf(head)+7);
		 }
		 str=str.replace("<body>", "").replace("</body>", "").replace("<body >", "");
		 return str;
	 }
}