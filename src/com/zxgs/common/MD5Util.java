package com.zxgs.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class MD5Util {
	/*** 
     * MD5加密 生成32位md5码
     * @param 待加密字符串
     * @return 返回32位md5码
     */
    public static String md5Encode(String inStr)  {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] byteArray = inStr.getBytes("UTF-8");
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        
    }
    
    public static String getMd5ByFile(File file) throws FileNotFoundException {  
        String value = null;  
        FileInputStream in = new FileInputStream(file);  
    try {  
        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(byteBuffer);  
        BigInteger bi = new BigInteger(1, md5.digest());  
        value = bi.toString(16);  
    } catch (Exception e) {  
        e.printStackTrace();  
    } finally {  
            if(null != in) {  
                try {  
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    return value;  
    }
    
    /**
     * 测试主函数
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
//        String str = new String("admin");
//        System.out.println("原始：" + str);
//        System.out.println("MD5后：" + md5Encode(str));
//        System.out.println("MD5(md5)后：" + md5Encode(md5Encode(str)));
        
        String path="D:\\app2.12.log";
        File file=new File(path);
        if(file.exists()){
        	String v = getMd5ByFile(file);  
        	System.out.println("MD5:"+v.toUpperCase());  
        	
        	FileInputStream fis= new FileInputStream(path);    
        	String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));    
        	IOUtils.closeQuietly(fis);    
        	System.out.println("MD5:"+md5);   
        }else{
        	System.out.println("MD5:"+"文件不存在");   
        }
    }
}
