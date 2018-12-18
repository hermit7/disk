package com.ise.pojo;

import java.io.Serializable;

/**
 * 群对象
 * 
 * @author 6
 *
 */
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	private String groupName; // 群名称 
	
	private String groupNumber; // 群名称 

	private int groupId; // 群id 主键

	private String owner; // 该群的管理员

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

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
