package com.ise.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Ⱥ����
 * 
 * @author 6
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupName; // Ⱥ���� 

	private int groupId; // Ⱥid ����

	private User admin; // ��Ⱥ�Ĺ���Ա

	private List<User> members; // Ⱥ��Ա

	private List<HFile> files; // Ⱥ����ļ���Ϣ ��Ҫ���ļ������ļ���С

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
