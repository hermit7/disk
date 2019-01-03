package com.ise.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.dao.HdfsDao;
import com.ise.mapper.UserMapper;
import com.ise.pojo.User;
import com.ise.service.UserService;
import com.ise.util.MyFileUtil;

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
			user.setUsedSpace(MyFileUtil.fileSizeFormat(Long.parseLong(user.getUsedSpace()) * 1024));
		}
		return users;
	}

	@Override
	public boolean banUser(String userId) {
		return userMapper.banUser(userId);
	}
	@Override
	public boolean permitUser(String userId) {
		return userMapper.permitUser(userId);
	}

	@Override
	public boolean dilatation(String userId) {
		return userMapper.dilatation(userId);
	}
	@Override
	public boolean reduction(String userId) {
		return userMapper.reduction(userId);
	}

}
