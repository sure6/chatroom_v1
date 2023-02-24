package com.softeem.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.softeem.dto.User;
import com.softeem.utils.JdbcUtils;

public class UserDAO {
	public User login(User user) {
		QueryRunner qr = new QueryRunner();
		String sql = "select * from user where username=? and password=?";
		User query = null;
		try {
			query = qr.query(JdbcUtils.getConn(), sql, new BeanHandler<User>(
					User.class), user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return query;
	}

	
}
