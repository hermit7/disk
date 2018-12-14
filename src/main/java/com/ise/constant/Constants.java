package com.ise.constant;

public class Constants {
	public static final String NETPAN_ROOT = "/disk/"; // 网盘根目录

	// 生成唯一rowkey的表 基本没其他用
	public static final String TABLE_GID = "table_gid";
	public static final String ROWKEY_GID = "gid";
	public static final String FAMILY_GID = "gid";
	public static final String COLUMN_GID = "gid";

	// 保存分享文件信息表
	public static final String SHARE_TABLE = "share";
	public static final String SHARE_FAMILY = "share_content";
	public static final String[] SHARE_COLUMEN = { "shareId", "shareType", "sharetime", "filePath", "fileName",
			"fileType", "fileSize", "receiverId", "receiverName", "providerId", "providerName"};

	// 好友关系表
	public static final String FRIEND_TABLE = "friend";
	public static final String FRIEND_FAMILY = "friend_content";
	public static final String[] FRIEND_COLUMN = { "userid", "username", "nickname" };
}
