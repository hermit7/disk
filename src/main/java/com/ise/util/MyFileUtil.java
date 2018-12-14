package com.ise.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyFileUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	static Map<String, String> map = new HashMap<>();
	static {
		// ͼƬ picture
		map.put("jpg", "p");
		map.put("png", "p");
		map.put("jpeg", "p");
		map.put("bmp", "p");
		map.put("gif", "p");
		// ��Ƶ video
		map.put("avi", "v");
		map.put("mtv", "v");
		map.put("mp4", "v");
		map.put("flv", "v");
		map.put("rmvb", "v");

		// ѹ���ļ� compress
		map.put("zip", "z");
		map.put("gz", "z");
		map.put("rar", "z");
		map.put("7z", "z");

		// �ĵ� document
		map.put("txt", "t");
		map.put("pdf", "t");
		map.put("ppt", "t");
		map.put("doc", "t");
		map.put("docx", "t");
		map.put("chm", "t");
		// ���� music
		map.put("mp3", "m");
		map.put("wma", "m");
		map.put("wav", "m");
		// ���� other
	}

	public static String fileSizeFormat(long size) {
		if (size == 0) {
			return "-";
		}
		// ����ֽ�������1024����ֱ����BΪ��λ�������ȳ���1024����3λ��̫��������
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// ���ԭ�ֽ�������1024֮������1024�������ֱ����KB��Ϊ��λ
		// ��Ϊ��û�е���Ҫʹ����һ����λ��ʱ��
		// ����ȥ�Դ�����
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// ��Ϊ�����MBΪ��λ�Ļ���Ҫ�������1λС����
			// ��ˣ��Ѵ�������100֮����ȡ��
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// �������Ҫ��GBΪ��λ�ģ��ȳ���1024����ͬ���Ĵ���
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}

	// ��ʽ���ļ��޸�ʱ��
	public static String fileTimeFormat(long time) {
		return sdf.format(new Date(time));
	}

	public static String getFileType(String name) {
		String suffix = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
		String type = map.get(suffix);
		if (null == type) {
			type = "o";
		}
		return type;
	}

	public static void main(String[] args) {
		System.out.println(getFileType("dhwjadadjk.gz"));
	}
}
