/**
 * 
 */
package com.zxgs.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.zxgs.dao.UserInfoMapper;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Desc //TODO 添加描述
 * @author zxk
 *
 * @Date 2019年3月26日 上午10:46:45
 */
public class ToolsImpl implements ITools{

	@Autowired
	private UserInfoMapper userInfoMapper ;
	
	/**
	 * @auther zxk
	 * @ 2019年3月26日 上午10:46:45
	 */
	public ToolsImpl() {
		// TODO Auto-generated constructor stub
	}

	/* @auther zxk
	 * @2019年3月26日 上午10:47:16
	 * @return 返回一个String类型的随机数
	 */
	@Override
	public String getRandomNumber() {
		// TODO Auto-generated method stub
		 Random random = new Random();
		 int randomInt = random.nextInt(99999);//[0,99999)
		 randomInt += 10000;//[10000,109999)
		 return String.valueOf(randomInt);
	}
	
	
	/* @auther zxk
	 * @2019年3月26日 上午10:47:16
	 * @return 返回一个String类型的随机数
	 */
	@Override
	public String getOneRandomNumber() {
		// TODO Auto-generated method stub
		 Random random = new Random();
		 int randomInt = random.nextInt(6);//[0,99999)
		 return String.valueOf(randomInt);
	}
	
	/**
	 * 
	 * //TODO 生成8位随机字符串
	 * @Desc
	 * @auther zxk
	 * @Date 2019年3月27日 上午10:20:42
	 * @return
	 */
	public String getRandomStr() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        int randomNum;
        char randomChar;
        Random random = new Random();
        // StringBuffer类型的可以append增加字符
        StringBuffer str = new StringBuffer();
        //生成8位随机字符串
        for (int i = 0; i < 8; i++) {
            // 可生成[0,n)之间的整数，获得随机位置
            randomNum = random.nextInt(base.length());
            // 获得随机位置对应的字符
            randomChar = base.charAt(randomNum);
            // 组成一个随机字符串
            str.append(randomChar);
        }
        return str.toString();
    }
	
	
	/**
	 * 
	 * @Desc
	 * 加密
	 * @auther zxk
	 * @Date 2019年3月25日 下午8:04:49
	 * 采用md5加密
	 * @param str
	 * @return 返回加密字符串
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//确定计算方法,获取固定长度的随机数字节数组
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //将字节数组转换成字符串（由于字符数组转换字符串可能会有乱码  所以使用这个类来防止）
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
//        Md5(Md5("123456")+salt).eq(passwd)
        return newstr;
        }
	
	/**
	 * 解密
	 * @Desc
	 * @auther zxk
	 * @Date 2019年3月26日 上午10:12:09
	 * @param str 加密密码
	 * @return newStr 解密密码
	 * @throws IOException
	 */
	@Override
	public String decodeByMd5(String str) throws IOException{
		String newStr = new String(new BASE64Decoder().decodeBuffer(str));
		return newStr;
	}

	/* @auther zxk
	 * @2019年3月27日 上午8:54:24
	 * @param 
	 */
	@Override
	public Date getCurrentTime() throws ParseException {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        Date currentTime = df.parse(df.format(new Date()));
		return currentTime;
	}
	
	 /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
	@Override
    public boolean delele(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }
	
	
	  /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
    
    
    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag =new ToolsImpl().deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = new ToolsImpl().deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    
    public void createFile(String url,String fileName) throws IOException{
		String path= url;//所创建文件的路径
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();//创建目录
		}
		File file = new File(path, fileName);
		if(!file.exists()){
			file.createNewFile();		
		}
	}

    /*
     * 
     * @auther zxk
     * @2019年5月14日 下午12:04:42
     * @param commandStr
     * @return
     */
    public static Integer exeCmd(String commandStr) {  
        BufferedReader br = null; 
        int exitVal = -1000 ;//退出值，编译成功为0，失败为1
    
        try {  
        	String line = null;  
            Runtime rt = Runtime.getRuntime();  
            Process proc = rt.exec(commandStr); 
            
           
            br = new BufferedReader(new InputStreamReader(proc.getInputStream()));//正确信息流
            System.out.println("<outPut>");  
            while ((line = br.readLine()) != null)  
                System.out.println(line); 
            System.out.println("<outPut>");  
            
            InputStream stderr = proc.getErrorStream();//错误信息流
            InputStreamReader isr = new InputStreamReader(stderr);
            br = new BufferedReader(isr);  
            System.out.println("<ERROR>");  
            while ((line = br.readLine()) != null){
//            	test.writeToTxt("d:\\output.txt",line);
                System.out.println(line);  
            }
            System.out.println("</ERROR>");  
            
            exitVal = proc.waitFor();//将该进程挂起，等待外部程序结束
            System.out.println("Process exitValue: " + exitVal); 
           
        } catch (Throwable t) {  
            t.printStackTrace();  
        }  
        return exitVal;
    }
    
	/*
	 * 
	 * @auther zxk
	 * @2019年5月14日 下午12:05:00
	 * @param code
	 * @return
	 */
    @Override
	public Boolean compiler(String path, String url) {
		// TODO Auto-generated method stub
    	String cmdStr="cmd /c set CLASSPATH="+url+"&& javac "+path+
    			"<"+url+"\\output.txt 2>"+url+"\\err.txt";//编译
    	if(ToolsImpl.exeCmd(cmdStr) == 0){//编译通过
    		return true;
    	}else{
    		return false;
    	}
	}
    
    
    @Override
    public void writeToFile(String path, String conent) {     
        BufferedWriter out = null;   
        File f=new File(path);
        Boolean flag=false;
        try {     
      	  if(!f.exists()){//文件是否已经存在
                f.createNewFile();
      	  }
             out = new BufferedWriter(new OutputStreamWriter(     
                    new FileOutputStream(path, flag)));            
             out.write(conent+"\r\n");   
             
         } catch (Exception e) {     
             e.printStackTrace();     
         } finally {     
            try {     
                 out.close();     
             } catch (IOException e) {     
                 e.printStackTrace();     
             }     
         }     
     }
	
	public String buildCode(String path,String code) {
		String cCode="";
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        try (FileReader reader = new FileReader(path);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
            	cCode += line;
                System.out.println(cCode);
            }
            cCode = cCode.split("\\*\\*\\*\\*\\*")[0]+code+cCode.split("\\*\\*\\*\\*\\*")[1];
            System.out.println("编译的代码："+cCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cCode;
    }

	
	public String readFile(String path) {
		String re = "";
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        try (FileReader reader = new FileReader(path);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据            	
            	re += line;
                System.out.println(re);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return re;
    }

	
	/**
	 * //TODO
	 * @Desc
	 * @auther zxk
	 * @Date 2019年5月14日 下午5:15:44
	 * @return
	 */
	public Boolean run(String path,String className) {
		// TODO Auto-generated method stub
		String cmdSentence = "cmd /c set CLASSPATH="+path+"&& java "+className+
				"<"+path+"\\input.txt 1>"+path+"\\output.txt 2>"+path+"\\err.txt";
		if(ToolsImpl.exeCmd(cmdSentence) == 0){
			return true;
		}else{
			return false;
		}
	}
		
	 public String getFile(String path) {
	        String re = "";
	        // 获得指定文件对象  
	        File file = new File(path);
	        // 获得该文件夹内的所有文件   
	        File[] array = file.listFiles();
	        for (int i = 0; i < array.length; i++) {
	        	if (array[i].isFile()){//文件
	                re = array[i].getName().split("\\.")[0].trim();
	            }
//		            else if(array[i].isDirectory())//如果是文件夹
//		            {  
//		                    for (int j = 0; j < deep; j++)//输出前置空格
//		                    System.out.print(" ");
	//
//		                    System.out.println( array[i].getName());
//		                    //System.out.println(array[i].getPath());
//		                    //文件夹需要调用递归 ，深度+1
//		                getFile(array[i].getPath(),deep+1);  
//		            }   
	        }
	        return re;
	    }
	
//		 public static void main(String[] args) {int arr[] = new int[5];Scanner iScanner = new Scanner(System.in);
//	        ToolsImpl.getFile("D:\\bysj\\小红老师\\冒泡排序", 1);
//	    }


	

}
