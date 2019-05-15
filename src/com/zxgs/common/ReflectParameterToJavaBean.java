package com.zxgs.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Map;
/***
 * 封装参数到Javabean
 * @author maleibo
 *
 */
public class ReflectParameterToJavaBean {
	
	/***
	 * 封装参数到Javabean
	 * @param c 要封装的类
	 * @param map 参数的map集合
	 * @return
	 * @throws Exception
	 */
    public  static Object reflectParameter(Class<?> c, Map<String, Object> map) throws Exception{
    	return reflectParameter(c,map,null);
    }
    
	/***
	 * 封装参数到Javabean
	 * @param c 要封装的类
	 * @param map 参数的map集合
	 * @param dateFormat 日期的格式化
	 * @return
	 * @throws Exception
	 */
    @SuppressWarnings("unused")
	public  static Object reflectParameter(Class<?> c, Map<String, Object> map,String dateFormat) throws Exception { 
    	Object o = (Object) c.newInstance();
    	if(dateFormat==null||dateFormat.equals("")){//默认日期格式化
    		dateFormat="yyyy-MM-dd";
    	}
    	SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
    	Field[] fieldlist = c.getDeclaredFields();
    	if(fieldlist!=null&&fieldlist.length>0){
    		for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                String name=fld.getName();
                Class<?> clazz=fld.getType();
                Object value = map.get(name);
                if(value!=null){//获取的value值是个字符串数组
                	value=((String[])value)[0];
                }
                Method get_Method = c.getMethod("get" + name);  //获取getMethod方法  
                Method set_Method = c.getMethod("set" + name, clazz);//获得属性get方法
                String type = clazz.getName(); //获取返回值名称  
                if (type.equals("long"))
                    set_Method.invoke(o, Long.valueOf(value.toString()));  //对于类型 long  
                else if (type.equals("int") || type.equals("java.lang.Integer"))//对于int 类型  
                    set_Method.invoke(o, Integer.valueOf(value.toString()));  
                else if ("java.lang.String".equals(type))  
                    set_Method.invoke(o,value);
                else if ("java.util.Date".equals(type)){
                	if(value!=null&&!value.toString().trim().equals("")){
                		set_Method.invoke(o,sdf.parse(value.toString()));
                	}
                }
                else set_Method.invoke(o, c.cast(value));//其他类型调用class.cast方法  
    		}
    	}
        return o;  
    }
  
    // 把一个字符串的第一个字母大写、效率是最高的、  
    @SuppressWarnings("unused")
	private static String getMethodName(String fildeName) {  
        byte[] items = fildeName.getBytes();  
        items[0] = (byte) ((char) items[0] - 'a' + 'A');  
        return new String(items);  
    }
}
