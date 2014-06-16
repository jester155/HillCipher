package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Cipher.HillCipher;

public class HillCipher_test {

	int[][] key = {{3,1},{6,5}};
	String plainText = "!@#$%^&*(*(ca:t";
	int n = 2;
	HillCipher Cipher = new HillCipher(key, n, plainText);	
	
	@Test
	public void testFillString() {
		assertEquals("catx", Cipher.getPlainText());
	}

	@Test
	public void testNumberToCharacter() {
		char c = 'c';
		assertEquals(c, Cipher.NumberToCharacter(2));
		
		char a = 'a';
		assertEquals(a, Cipher.NumberToCharacter(26));
		char m = 'm';
		assertEquals(m, Cipher.NumberToCharacter(38));
		
		char v = 'v';
		assertEquals(v, Cipher.NumberToCharacter(229));
	}

	@Test
	public void testCharToNumber() {
		assertEquals(12, Cipher.CharToNumber('m'));
	}

	@Test
	public void testEncrypt() {
		assertEquals("gmcv", Cipher.Encrypt());
		int[][] key = {{3,1},{6,5}};
		String plainText = "math";
		int n = 2;
		HillCipher c = new HillCipher(key, n, plainText);
		
		assertEquals("kumt", c.Encrypt());
	}

}
