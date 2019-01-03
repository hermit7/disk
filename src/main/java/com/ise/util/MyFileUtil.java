package com.ise.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ise.constant.Constants;

public class MyFileUtil {
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	static NumberFormat numberFormat = NumberFormat.getInstance();

	static Map<String, String> map = new HashMap<>();
	static {
		numberFormat.setMaximumFractionDigits(2);
		// 图片 picture
		map.put("jpg", "p");
		map.put("png", "p");
		map.put("jpeg", "p");
		map.put("bmp", "p");
		map.put("gif", "p");
		// 视频 video
		map.put("avi", "v");
		map.put("mtv", "v");
		map.put("mp4", "v");
		map.put("flv", "v");
		map.put("rmvb", "v");

		// 压缩文件 compress
		map.put("zip", "z");
		map.put("gz", "z");
		map.put("rar", "z");
		map.put("7z", "z");

		// 文档 document
		map.put("txt", "t");
		map.put("pdf", "t");
		map.put("ppt", "t");
		map.put("doc", "t");
		map.put("docx", "t");
		map.put("chm", "t");
		// 音乐 music
		map.put("mp3", "m");
		map.put("wma", "m");
		map.put("wav", "m");
		// 其他 other
	}

	public static String fileSizeFormat(long size) {
		if (size == 0) {
			return "-";
		}
		// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
		if (size < 1024) {
			return String.valueOf(size) + "B";
		} else {
			size = size / 1024;
		}
		// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
		// 因为还没有到达要使用另一个单位的时候
		// 接下去以此类推
		if (size < 1024) {
			return String.valueOf(size) + "KB";
		} else {
			size = size / 1024;
		}
		if (size < 1024) {
			// 因为如果以MB为单位的话，要保留最后1位小数，
			// 因此，把此数乘以100之后再取余
			size = size * 100;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
		} else {
			// 否则如果要以GB为单位的，先除于1024再作同样的处理
			size = size * 100 / 1024;
			return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
		}
	}

	// 格式化文件修改时间
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

	public static String getPercentSpace(String usedSpace) {
		long used = Long.parseLong(usedSpace);
		String percent = numberFormat.format(used / (1024 * 1024) * 1.0);
		return percent;
	}

	public static String getUsedSpace(String usedSpace, int times) {
		long used = Long.parseLong(usedSpace);
		String result = fileSizeFormat(used * 1024l);
		return result + "/" + Constants.DEFAULT_MAX_SPACE * times + "G";
	}

	public static void main(String[] args) {
		System.out.println(getPercentSpace("104786670"));
		System.out.println(getUsedSpace("104786670", 2));

	}
}
