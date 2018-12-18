package com.jdbc.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.druid.pool.DruidDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:applicationContext.xml" })
public class JdbcTest {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private DruidDataSource dataSource;

	@Test
	public void testI() {
		jdbcTemplate.execute("insert into `group`(`group_name`,`owner`) values('friends','lilei')");
	}

	@Test
	public void testQuery() {
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement pst = conn.prepareStatement("insert into `group`(`group_name`,`owner`) values(?,?)");
			pst.setString(1, "durid");
			pst.setString(2, "lilei");
			pst.executeUpdate();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from `group`");
			while (rs.next()) {
				System.out.println(
						rs.getString("group_id") + " " + rs.getString("group_name") + " " + rs.getString("owner"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
