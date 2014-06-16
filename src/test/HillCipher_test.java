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
		assertEquals("gmcv", Cipher.Encrypt(Cipher.getKey()));
		int[][] key = {{3,1},{6,5}};
		String plainText = "math";
		int n = 2;
		HillCipher c = new HillCipher(key, n, plainText);
		
		assertEquals("kumt", c.Encrypt(c.getKey()));
	}
	
	@Test
	public void testModInverse() {
		assertTrue(Cipher.ModInverse(11, 26) == 19);
	}
	
	@Test
	public void testGetInverseKey() {
		int[][] key = {{3,2},{5,7}};
		int[][] inverseKey = {{3,14},{9,5}};
		int n = 2;
		String plainText = "cat"; 
		HillCipher c = new HillCipher(key, n, plainText);
		assertArrayEquals(inverseKey, c.getDecryptionKey());
	}
	
	@Test
	public void testDeterminant() {
		int[][] key = {{3,2},{5,7}};
		int[][] inverseKey = {{3,14},{9,5}};
		int n = 2;
		String plainText = "cat"; 
		HillCipher c = new HillCipher(key, n, plainText);
		int x = c.Determinant(key);
		
		assertEquals(11, x);
	}
	
	@Test
	public void testDecrypt() {
		assertEquals("catx", Cipher.Decrypt(Cipher.getDecryptionKey() , "gmcv"));
	}
}