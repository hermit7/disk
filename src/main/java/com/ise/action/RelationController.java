package com.ise.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ise.pojo.Group;
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
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("path", path);
		return "/jsp/share.jsp";
	}

	@RequestMapping(value = "/member")
	public String member(String groupName, String groupNumber, String groupOwner, Model model) {
		try {
			groupName = URLDecoder.decode(groupName, "UTF-8");
			groupOwner = URLDecoder.decode(groupOwner, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		model.addAttribute("groupName", groupName);
		model.addAttribute("groupNumber", groupNumber);
		model.addAttribute("groupOwner", groupOwner);
		return "/jsp/member.jsp";
	}

	@RequestMapping(value = "/addGroupMember")
	@ResponseBody
	public boolean addMember(String groupName, String groupNumber, String groupOwner, String userId, String username) {
		return relationService.addGroupMemeber(groupName, groupNumber, groupOwner, userId, username);
	}

	/**
	 * 展示该用户所关注的用户
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/showFollows")
	@ResponseBody
	public List<User> showFollows(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<User> follows = relationService.listFriends(user);
		return follows;
	}

	/**
	 * 展示该用户所关注的用户
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/showGroups")
	@ResponseBody
	public List<Group> showGroups(HttpSession session) {
		User user = (User) session.getAttribute("user");
		List<Group> follows = relationService.listGroups(user);
		return follows;
	}

	/**
	 * 查询我所在的群组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/group")
	public String groupList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Group> groupList = relationService.listGroups(user);
		model.addAttribute("groupList", groupList);
		model.addAttribute("user", user);
		return "/jsp/group.jsp";
	}

	/**
	 * 新建群组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/createGroup")
	@ResponseBody
	public boolean createGroup(HttpSession session, String groupName) {
		User user = (User) session.getAttribute("user");
		return relationService.createGroup(user, groupName);
	}

	/**
	 * 新建群组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/renameGroup")
	@ResponseBody
	public boolean renameGroup(HttpSession session, String groupNumber, String destName) {
		User user = (User) session.getAttribute("user");
		return relationService.renameGroup(user, groupNumber, destName);
	}

	/**
	 * 退出群组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/quitGroup")
	@ResponseBody
	public boolean quitGroup(HttpSession session, String groupNumber) {
		User user = (User) session.getAttribute("user");
		return relationService.quitGroup(user, groupNumber);
	}

	/**
	 * 解散群组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dismissGroup")
	@ResponseBody
	public boolean dismissGroup(HttpSession session, String groupNumber) {
		User user = (User) session.getAttribute("user");
		return relationService.dissmissGroup(user, groupNumber);
	}

	/**
	 * 组成员
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMember")
	public String listGroupMember(HttpSession session, String groupNumber, Model model) {
		User user = (User) session.getAttribute("user");
		List<User> member = relationService.listGroupMember(groupNumber);
		Map<String, String> groupInfo = relationService.getGroupInfo(groupNumber, user.getUserId());
		String owner = groupInfo.get("owner");
		String groupName = groupInfo.get("groupName");
		model.addAttribute("memberList", member);
		model.addAttribute("owner", owner);
		model.addAttribute("groupName", groupName + "(成员)");
		model.addAttribute("groupNumber", groupNumber);
		model.addAttribute("user", user);
		return "/jsp/groupmember.jsp";
	}

}
