package com.zxgs.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleJDBC {
	
	/**
	 * 
	 * @param url	
	 * @param user
	 * @param password
	 */
	public static Connection getConnection(String ip,String port,String sid,String username,String psd){
		 //设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
        String driverName="oracle.jdbc.driver.OracleDriver";  
//        String url="jdbc:oracle:thin:@192.168.110.228:1521:orcl";  //test为数据库名称，1521为连接数据库的默认端口  
        String url="jdbc:oracle:thin:@"+ip+":"+port+":"+sid;  //test为数据库名称，1521为连接数据库的默认端口  
        String user=username;   //为用户名  
        String password=psd;  //为密码  
      //数据库连接对象  
        Connection conn = null;  
        //反射Oracle数据库驱动程序类  
        try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);  
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
		}
        return conn;
	}
}
