package com.ise.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.dao.HdfsDao;
import com.ise.dao.UserDao;
import com.ise.pojo.User;
import com.ise.service.userService;
@Service("userService")
public class userServiceImpl implements userService{
	
	@Autowired
	private HdfsDao hdfsDao; 
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public void makeUserRoot(String username) {
		hdfsDao.makeDir(username);
	}

	@Override
	public User existUser(String username, String password) {
		//对密码进行MD5加密
		//password = MD5Util.md5(password);
		return userDao.existUser(username, password);
	}
	
}
