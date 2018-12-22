package com.ise.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.mapper.UserMapper;
import com.ise.pojo.User;

@Controller()
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserMapper userDao;
	
	@RequestMapping(value = "/existUser")
	@ResponseBody
	public User findUser(String username) {
		return userDao.findUser(username);
	}
}
