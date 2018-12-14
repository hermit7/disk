package com.ise.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ise.dao.UserDao;
import com.ise.pojo.User;

/**
 * 更新mysql用此类
 * 
 * @author 6
 *
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	/*
	 * @Autowired private JdbcTemplate jdbc;
	 */

	@Autowired
	private DataSource dataSource;

	@Override
	public User existUser(String username, String password) {
		Connection conn = null;
		User user = null;
		String sql = "select `user_id`,`user_name`,`password`,`remain_space`,`user_type` "
				+ "from user where `user_name`= ? and `password` = ?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUsername(username);
				user.setSpace(rs.getString("remain_space"));
				user.setType(rs.getString("user_type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	@Override
	public User existUser(String username) {
		Connection conn = null;
		User user = null;
		String sql = "select `user_id`,`user_name` from user where `user_name`=?";
		try {
			conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setUsername(username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return user;
	}

}
