package com.ise.test;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/test")
public class TestController {

	/*@Autowired
	private HdfsDao dao;*/

	@RequestMapping("/login")
	public void login() {
		System.out.println("success");
	}

	@RequestMapping("/index")
	public String index() {
		System.out.println("index success");
		return "/index.jsp";
	}

}
