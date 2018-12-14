package com.ise.service;

import com.ise.pojo.User;

public interface userService {
	
	void makeUserRoot(String username);

	User existUser(String username, String password);
}
