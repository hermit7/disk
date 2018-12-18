package com.ise.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.dao.UserDao;
import com.ise.pojo.User;

@Controller()
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/existUser")
	@ResponseBody
	public User existUser(String username) {
		return userDao.existUser(username);
	}
}
