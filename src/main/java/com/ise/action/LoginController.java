package com.ise.action;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ise.pojo.User;
import com.ise.service.userService;

@Controller
public class LoginController {

	@Autowired
	private userService userService;

	@RequestMapping("/login")
	public String login(HttpSession session, String username, String password, Model model) {
		User user = userService.existUser(username, password);
		if (user == null) {
			model.addAttribute("msg", "账号或密码输入不正确");
			return "/login.html";
		}
		session.setAttribute("user", user);
		//此步应该在注册时使用
		userService.makeUserRoot(user.getUsername());
		return "redirect:/jsp/index.jsp";
	}

	@RequestMapping("/index")
	public String index() {
		return "/jsp/index.jsp";
	}

	@RequestMapping("/register")
	public String register(String email, String username, String password) {
		// 向数据库注册此用户
		// 1、判断邮箱是否注册
		// 2、判断用户名是否已经存在 若不存在，为该用户生成根目录
		// 3、对密码进行MD5加密
		return "/login.jsp";
	}

}
