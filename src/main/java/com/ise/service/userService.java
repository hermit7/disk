package com.ise.service;

import java.util.List;

import com.ise.pojo.User;

public interface UserService {
	
	void makeUserRoot(String username);

	User existUser(String username, String password);

	List<User> listAllUsers();

	boolean banUser(String userId);

	boolean permitUser(String userId);

	boolean dilatation(String userId);

	boolean reduction(String userId);
}
