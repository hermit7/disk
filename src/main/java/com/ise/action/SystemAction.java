package com.ise.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ise.pojo.User;
import com.ise.service.UserService;

@Controller
@RequestMapping("/system")
public class SystemAction {

	@Autowired 
	private UserService userService;
	
	@RequestMapping("/userManage")
	public String userManage(Model model) {
		List<User> list = userService.listUsers();
		model.addAttribute("userList", list);
		return "/jsp/usermanager.jsp";
	}
	
}
