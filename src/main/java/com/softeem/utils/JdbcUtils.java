package com.softeem.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC工具类
 * 	* 加载驱动
 *  * 获得连接
 *  * 释放资源
 *  	* 代码都重复
 * @author leesure
 *
 */
public class JdbcUtils {
  private static ComboPooledDataSource cpds=new ComboPooledDataSource();
  public static DataSource getDataSource(){
//	  try {
//		cpds.setDriverClass("com.mysql.jdbc.Driver");
//		cpds.setJdbcUrl("jdbc:mysql://192.168.1.18:3306/chatroom?serverTimezone=UTC&useSSL=false&characterEncoding=utf-8");
//		cpds.setUser("root");
//		cpds.setPassword("root");
//	} catch (PropertyVetoException e) {
//		e.printStackTrace();
//	}
	  return cpds;
  }
  public static Connection getConn(){
	  Connection connection = null;
	  try {
		 connection = getDataSource().getConnection();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	  return connection;
  }

}
