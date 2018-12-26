package com.ise.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralUtil {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public static String formatDate(Date date) {
		String result = sdf.format(date);
		return result;
	}

	public static long unitConvert(String space) {
		return 1l;
	}
	
	public static void main(String[] args) {
		System.out.println(formatDate(new Date()));
	}
}
