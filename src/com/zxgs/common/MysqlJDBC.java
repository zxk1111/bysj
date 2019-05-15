package com.zxgs.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlJDBC {
	
	/**
	 * InstantiationException IllegalAccessException
	 * @param url	
	 * @param user
	 * @param password
	 */
	public static Connection getConnection(String ip,String port,String db,String username,String psd){
		Connection conn=null;
		
        	try {
				Class.forName("com.mysql.jdbc.Driver");
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url="jdbc:mysql://"+ip+":"+port+"/"+db+"?user="+username+"&password="+psd;
        	
				conn = DriverManager.getConnection(url);
			
				return conn;
        	}catch(InstantiationException | IllegalAccessException e){
        		e.printStackTrace();
        	} catch (ClassNotFoundException |SQLException e) {
        		e.printStackTrace();
        	}
		
        return conn;
	}
	
}
