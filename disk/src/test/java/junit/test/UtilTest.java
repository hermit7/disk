package junit.test;

import org.junit.Test;

public class UtilTest {

	@Test
	public void testString(){
		String s1 = "d";
		String s2 = "f";
		System.out.println(s1.charAt(0) - s2.charAt(0));
	}
}
