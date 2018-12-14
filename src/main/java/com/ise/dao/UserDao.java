package com.ise.dao;

import com.ise.pojo.User;

public interface UserDao {
	
	User existUser(String username,String password);

	User existUser(String username);
	
	
}
