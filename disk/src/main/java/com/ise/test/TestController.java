package com.ise.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.dao.HdfsDao;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private HdfsDao dao;
	
	@RequestMapping("/login")
	public void login() {
		System.out.println("success");
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object listFiles() {
		Map<String, String> map = dao.listFiles();
		return map;
	}
	
}
