package com.ise.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 群对象
 * 
 * @author 6
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupName; // 群名称 

	private int groupId; // 群id 主键

	private User admin; // 该群的管理员

	private List<User> members; // 群成员

	private List<HFile> files; // 群里的文件信息 主要是文件名，文件大小

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<HFile> getFiles() {
		return files;
	}

	public void setFiles(List<HFile> files) {
		this.files = files;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}
}
