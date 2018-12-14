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
			model.addAttribute("msg", "�˺Ż��������벻��ȷ");
			return "/login.html";
		}
		session.setAttribute("user", user);
		//�˲�Ӧ����ע��ʱʹ��
		userService.makeUserRoot(user.getUsername());
		return "redirect:/jsp/index.jsp";
	}

	@RequestMapping("/index")
	public String index() {
		return "/jsp/index.jsp";
	}

	@RequestMapping("/register")
	public String register(String email, String username, String password) {
		// �����ݿ�ע����û�
		// 1���ж������Ƿ�ע��
		// 2���ж��û����Ƿ��Ѿ����� �������ڣ�Ϊ���û����ɸ�Ŀ¼
		// 3�����������MD5����
		return "/login.jsp";
	}

}
