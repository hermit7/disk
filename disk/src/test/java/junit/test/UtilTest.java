package junit.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testString(){
		String s1 = "d";
		String s2 = "f";
		System.out.println(s1.charAt(0) - s2.charAt(0));
	}
	
	@Test
	public void testReg() {
		String str = "hdfs://192.168.137.30:9000/disk/admin/aaaaa";
		System.out.println(str.replaceAll("^.+://.+:[0-9]+/[a-z]+/[a-z]+", ""));
	}
	
	@Test
	public void testReg2() {
		String str = "/disk/admin/aaaaa";
		//String[] split = str.replaceAll("^/[a-z]+/[a-z]+/", "").split("/");
		Pattern pattern = Pattern.compile("^/[a-z]+/[a-z]+");
		Matcher ma = pattern.matcher(str);
		System.out.println(ma.group());
		//System.out.println(split.length);
	}
}
