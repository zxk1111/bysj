package com.zxgs.test;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  

public class TestOracle {
	public void SelectUser(){  
		  
        //设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
        String driverName="oracle.jdbc.driver.OracleDriver";  
        String url="jdbc:oracle:thin:@192.168.110.228:1521:orcl";  //test为数据库名称，1521为连接数据库的默认端口  
        String user="zhsq";   //aa为用户名  
        String password="zhsq";  //123为密码  
          
        PreparedStatement pstmt = null;  
        ResultSet rs = null;  
          
        //数据库连接对象  
        Connection conn = null;  
          
        try {  
            //反射Oracle数据库驱动程序类  
            Class.forName(driverName);  
              
            //获取数据库连接  
            conn = DriverManager.getConnection(url, user, password);  
              
            //输出数据库连接  
            System.out.println(conn);  
              
            //定制sql命令  
            String sql = "select * from jjsh_rjgdp  where QX = ?";  
              
            //创建该连接下的PreparedStatement对象  
            pstmt = conn.prepareStatement(sql);  
              
            //传递第一个参数值 root，代替第一个问号  
            pstmt.setString(1, "万州区");  
              
            //执行查询语句，将数据保存到ResultSet对象中  
            rs = pstmt.executeQuery();  
              
            //将指针移到下一行，判断rs中是否有数据  
            if(rs.next()){  
                //输出查询结果  
                System.out.println("查询到名为【" + rs.getString("objectid") + "】的信息，其密码为：" + rs.getString("QX"));  
            }else{  
                //输出查询结果  
                System.out.println("未查询到用户名为【" + rs.getString("user_id") + "】的信息");  
            }  
            conn.close();
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }finally{  
            try{  
                if(rs != null){  
                    rs.close();  
                }  
                if(pstmt != null){  
                    pstmt.close();  
                }  
                if(conn != null){  
                    conn.close();  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }     
        }  
      
    }  
	
	public void getFileds(){  
		
		//设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
		String driverName="oracle.jdbc.driver.OracleDriver";  
		String url="jdbc:oracle:thin:@192.168.110.228:1521:orcl";  //test为数据库名称，1521为连接数据库的默认端口  
		String user="zhsq";   //aa为用户名  
		String password="zhsq";  //123为密码  
		
		PreparedStatement pstmt = null;  
		ResultSet rs = null;  
		
		//数据库连接对象  
		Connection conn = null;  
		
		try {  
			//反射Oracle数据库驱动程序类  
			Class.forName(driverName);  
			
			//获取数据库连接  
			conn = DriverManager.getConnection(url, user, password);  
			
			//输出数据库连接  
			System.out.println(conn);  
			
			String sql="select COLUMN_NAME,DATA_TYPE,DATA_LENGTH  from "
					+ " dba_tab_columns where  table_name =upper(?) "
					+ " order by COLUMN_NAME";
			
			//创建该连接下的PreparedStatement对象  
			pstmt = conn.prepareStatement(sql);  
			
			//传递第一个参数值 root，代替第一个问号  
			pstmt.setString(1, "zhsq_qxzd");  
			
			//执行查询语句，将数据保存到ResultSet对象中  
			rs = pstmt.executeQuery();  
			
			//将指针移到下一行，判断rs中是否有数据  
			if(rs.next()){  
				System.out.println("success");
				//输出查询结果  
//				System.out.println("查询到名为【" + rs.getString("objectid") + "】的信息，其密码为：" + rs.getString("QX"));  
			}else{  
				//输出查询结果  
//				System.out.println("未查询到用户名为【" + rs.getString("user_id") + "】的信息");  
			}  
			conn.close();
		} catch (ClassNotFoundException e) {  
			e.printStackTrace();  
		} catch (SQLException e) {  
			e.printStackTrace();  
		}finally{  
			try{  
				if(rs != null){  
					rs.close();  
				}  
				if(pstmt != null){  
					pstmt.close();  
				}  
				if(conn != null){  
					conn.close();  
				}  
			} catch (SQLException e) {  
				e.printStackTrace();  
			}     
		}  
		
	}  
	
	public void testTableName(){  
			
			//设定数据库驱动，数据库连接地址、端口、名称，用户名，密码  
			String driverName="oracle.jdbc.driver.OracleDriver";  
			String url="jdbc:oracle:thin:@192.168.110.228:1521:orcl";  //test为数据库名称，1521为连接数据库的默认端口  
			String user="zhsq";   //aa为用户名  
			String password="zhsq";  //123为密码  
			
			PreparedStatement pstmt = null;  
			ResultSet rs = null;  
			
			//数据库连接对象  
			Connection conn = null;  
			
			try {  
				//反射Oracle数据库驱动程序类  
				Class.forName(driverName);  
				
				//获取数据库连接  
				conn = DriverManager.getConnection(url, user, password);  
				
				//输出数据库连接  
				System.out.println(conn);  
				
				String sql="select count(*) from zhsq_qxzd";
				
				//创建该连接下的PreparedStatement对象  
				pstmt = conn.prepareStatement(sql);  
				
				//传递第一个参数值 root，代替第一个问号  
//				pstmt.setString(1, "zhsq_qxzd");  
//				pstmt.set
				
				//执行查询语句，将数据保存到ResultSet对象中  
				rs = pstmt.executeQuery();  
				
				//将指针移到下一行，判断rs中是否有数据  
				if(rs.next()){  
					System.out.println("success");
					//输出查询结果  
	//				System.out.println("查询到名为【" + rs.getString("objectid") + "】的信息，其密码为：" + rs.getString("QX"));  
				}else{  
					//输出查询结果  
	//				System.out.println("未查询到用户名为【" + rs.getString("user_id") + "】的信息");  
				}  
				conn.close();
			} catch (ClassNotFoundException e) {  
				e.printStackTrace();  
			} catch (SQLException e) {  
				e.printStackTrace();  
			}finally{  
				try{  
					if(rs != null){  
						rs.close();  
					}  
					if(pstmt != null){  
						pstmt.close();  
					}  
					if(conn != null){  
						conn.close();  
					}  
				} catch (SQLException e) {  
					e.printStackTrace();  
				}     
			}  
			
		}  
      
    public static void main(String[] args){  
//        new TestOracle().SelectUser();  
//        new TestOracle().getFileds();  
        new TestOracle().testTableName();  
    }  
}
