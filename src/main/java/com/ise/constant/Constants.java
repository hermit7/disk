package com.ise.constant;

public class Constants {
	public static final String NETPAN_ROOT = "/disk/"; // 网盘根目录

	// 生成唯一rowkey的表 基本没其他用
	public static final String TABLE_GID = "table_gid";
	public static final String ROWKEY_GID = "gid";
	public static final String FAMILY_GID = "gid";
	public static final String COLUMN_GID = "gid";

	public static final long DEFAULT_MAX_SPACE = 100; // 单位为G
	public static final long DEFAULT_MAX_SPACE_KBIT = 100 * 1024 * 1024; // 单位为G

	// 保存分享文件信息表
	public static final String SHARE_TABLE = "share";
	public static final String SHARE_FAMILY = "share_content";
	public static final String[] SHARE_COLUMN = { "shareId", "shareType", "sharetime", "filePath", "fileName",
			"fileType", "fileSize", "receiverId", "receiverName", "providerId", "providerName" };

	// 好友关系表
	public static final String FRIEND_TABLE = "friend";
	public static final String FRIEND_FAMILY = "friend_content";
	public static final String[] FRIEND_COLUMN = { "userid", "username", "nickname" };

	// 群组信息表
	public static final String GROUP_MEMBER_TABLE = "group_member";
	public static final String GROUP_MEMBER_FAMILY = "group_member_content";
	public static final String[] GROUP_MEMBER_COLUMN = { "groupName", "groupNumber", "memberId", "member",
			"ownerName" };

	// 群组文件信息表
	public static final String GROUP_SHARE_TABLE = "group_share";
	public static final String GROUP_SHARE_FAMILY = "group_share_content";
	public static final String[] GROUP_SHARE_COLUMN = { "shareId", "groupName", "groupNumber", "sharetime", "filePath",
			"fileName", "fileType", "fileSize", "providerId", "providerName" };
}
