package com.ise.pojo;

import java.io.Serializable;

import com.ise.util.MyFileUtil;

/**
 * 用户
 * 
 * @author 6
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username; // 用户名 唯一约束 用来存放用户文件根目录
	private String userId; // 用户ID 方便外部引用 主键
	private String password; // 密码
	private long usedSpace; // 用户空间
	private String userType; // 用户类型 普通用户 、管理员

	private String nickname; // 备注

	@SuppressWarnings("unused")
	private String value;
	@SuppressWarnings("unused")
	private String text;

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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

	public long getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(long usedSpace) {
		this.usedSpace = usedSpace;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getValue() {
		return MyFileUtil.getPercentSpace(usedSpace);
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return MyFileUtil.getUsedSpace(usedSpace);
	}

	public void setText(String text) {
		this.text = text;
	}

}
