package com.ise.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.pojo.User;
import com.ise.service.UserService;

@Controller
@RequestMapping("/system")
public class SystemAction {

	@Autowired
	private UserService userService;

	@RequestMapping("/userManage")
	public String userManage(Model model) {
		List<User> list = userService.listAllUsers();
		model.addAttribute("userList", list);
		return "/jsp/usermanager.jsp";
	}

	@RequestMapping("/banUser")
	@ResponseBody
	public boolean banUser(String userId) {
		return userService.banUser(userId);
	}

	@RequestMapping("/permitUser")
	@ResponseBody
	public boolean permitUser(String userId) {
		return userService.permitUser(userId);
	}

	@RequestMapping("/dilatation")
	@ResponseBody
	public boolean dilatation(String userId) {
		return userService.dilatation(userId);
	}

	@RequestMapping("/reduction")
	@ResponseBody
	public boolean reduction(String userId) {
		return userService.reduction(userId);
	}

}
