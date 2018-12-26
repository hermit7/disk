package com.ise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.dao.HdfsDao;
import com.ise.mapper.UserMapper;
import com.ise.pojo.User;
import com.ise.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private HdfsDao hdfsDao;

	@Autowired
	private UserMapper userMapper;

	@Override
	public void makeUserRoot(String username) {
		hdfsDao.makeDir(username);
	}

	@Override
	public User existUser(String username, String password) {
		// 对密码进行MD5加密
		// password = MD5Util.md5(password);
		return userMapper.login(username, password);
	}

	@Override
	public List<User> listAllUsers() {
		List<User> users = userMapper.findAllUsers();
		for (User user : users) {
			String type = user.getUserType();
			if("0".equals(type)) {
				type="管理员";
			}else if("1".equals(type)) {
				type="普通用户";
			}else if("2".equals(type)) {
				type="已禁用";
			}
			user.setUserType(type);
			user.setUsedSpace(user.getUsedSpace() * 1024);
		}
		return users;
	}

}
