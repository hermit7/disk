package com.ise.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * �û�
 * @author 6
 *
 */
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username; // �û��� ΨһԼ�� ��������û��ļ���Ŀ¼
	private String userId; // �û�ID �����ⲿ����  ����
	private String pasword;  // ����
	private String space; // �û��ռ�
	private String type; // �û����� ��ͨ�û� ������Ա
	
	private String nickname; //��ע
	private List<Group> groups; // ���û��ڵ�Ⱥ ��Ⱥid
	private List<User> friends; // ���û���ӵ�еĺ��� ���û�id
	
	
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
