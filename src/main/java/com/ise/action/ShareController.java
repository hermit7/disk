package com.ise.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.pojo.Share;
import com.ise.pojo.User;
import com.ise.service.ShareService;

@Controller
@RequestMapping("/share")
public class ShareController {

	@Autowired
	private ShareService shareService;

	/**
	 * 
	 * @param session
	 * @param path        分享的文件路径
	 * @param receiveId   接收者id
	 * @param receiveName 接收者name
	 * @return
	 */
	@RequestMapping(value = "/addShare")
	@ResponseBody
	public boolean addShare(HttpSession session, String path, String receiverId, String receiverName) {
		User user = (User) session.getAttribute("user");
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return shareService.addShare(user, path, receiverId, receiverName);
	}

	@RequestMapping(value = "/receive")
	public String receivedShare(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Share> shareList = shareService.listReceivedShare(user);
		model.addAttribute("shareList", shareList);
		return "/jsp/receive.jsp";
	}

	@RequestMapping(value = "/provide")
	public String providedShare(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Share> shareList = shareService.listProvidedShare(user);
		model.addAttribute("shareList", shareList);
		return "/jsp/provide.jsp";
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public boolean deleteShare(HttpSession session, @RequestParam(defaultValue = "") String userId, String shareId) {
		if ("".equals(userId)) {
			User user = (User) session.getAttribute("user");
			userId = user.getUserId();
		}
		return shareService.deleteShare(userId, shareId);
	}

	@RequestMapping(value = "/dirTree")
	public String showDirTreeUI(Model model, String path) {
		try {
			path = URLDecoder.decode(path, "UTF-8");
			model.addAttribute("path", path);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "/jsp/saveShare.jsp";
	}

	@RequestMapping(value = "/saveShare")
	@ResponseBody
	public boolean saveShare(String path, String dst) {
		boolean flag = false;
		try {
			path = URLDecoder.decode(path, "UTF-8");
			flag = shareService.saveShare(path, dst);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
