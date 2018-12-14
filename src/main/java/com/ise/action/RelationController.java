package com.ise.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.pojo.User;
import com.ise.service.RelationService;

@Controller
@RequestMapping("/relation")
public class RelationController {

	@Autowired
	private RelationService relationService;

	@RequestMapping(value = "/friend")
	public String friendList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<User> friends = relationService.listFriends(user);
		model.addAttribute("friendList", friends);
		return "/jsp/friend.jsp";
	}

	@RequestMapping(value = "/follow")
	@ResponseBody
	public boolean follow(HttpSession session, String friendId, String friendName) {
		User user = (User) session.getAttribute("user");
		return relationService.follow(user.getUserId(), friendId, friendName);
	}

	@RequestMapping(value = "/remark")
	@ResponseBody
	public boolean remarkFriend(HttpSession session, String friendId, String remark) {
		User user = (User) session.getAttribute("user");
		return relationService.remarkFriend(user.getUserId(), friendId, remark);
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public boolean deleteFriend(HttpSession session, String friendId) {
		User user = (User) session.getAttribute("user");
		return relationService.deleteFriend(user.getUserId(), friendId);
	}

	@RequestMapping(value = "/shareUI")
	public String relation(String path, Model model) {
		model.addAttribute("path", path);
		return "/jsp/share.jsp";
	}

	/**
	 * 展示该用户所关注的用户
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/showFollows")
	@ResponseBody
	public List<Map<String, String>> showFollows(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Map<String, String>> follows = relationService.showFollows(user);
		return follows;
	}


	@RequestMapping(value = "/group")
	public String groupList() {
		return "/jsp/group.jsp";
	}

}
