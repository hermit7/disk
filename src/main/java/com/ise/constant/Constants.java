package com.ise.constant;

public class Constants {
	public static final String NETPAN_ROOT = "/disk/"; // ���̸�Ŀ¼

	// ����Ψһrowkey�ı� ����û������
	public static final String TABLE_GID = "table_gid";
	public static final String ROWKEY_GID = "gid";
	public static final String FAMILY_GID = "gid";
	public static final String COLUMN_GID = "gid";

	// ��������ļ���Ϣ��
	public static final String SHARE_TABLE = "share";
	public static final String SHARE_FAMILY = "share_content";
	public static final String[] SHARE_COLUMEN = { "shareId", "shareType", "sharetime", "filePath", "fileName",
			"fileType", "fileSize", "receiverId", "receiverName", "providerId", "providerName"};

	// ���ѹ�ϵ��
	public static final String FRIEND_TABLE = "friend";
	public static final String FRIEND_FAMILY = "friend_content";
	public static final String[] FRIEND_COLUMN = { "userid", "username", "nickname" };
}
