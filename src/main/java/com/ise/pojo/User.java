package com.ise.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 用户
 * @author 6
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username; // 用户名 唯一约束 用来存放用户文件根目录
	private String userId; // 用户ID 方便外部引用  主键
	private String pasword;  // 密码
	private String space; // 用户空间
	private String type; // 用户类型 普通用户 、管理员
	
	private String nickname; //备注
	private List<Group> groups; // 该用户在的群 存群id
	private List<User> friends; // 该用户所拥有的好友 存用户id
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
