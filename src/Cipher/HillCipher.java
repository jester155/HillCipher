package Cipher;

import java.text.Normalizer;

public class HillCipher {

	char[] Alphabet = new char[26];
	int[][] Key;
	int N;
	String PlainText;

	public int[][] getKey() {
		return Key;
	}

	public void setKey(int[][] key) {
		Key = key;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public String getPlainText() {
		return PlainText;
	}

	public void setPlainText(String plainText) {
		PlainText = plainText;
	}

	public HillCipher(int[][] key, int n, String plainText) {
		Key = key;
		N = n;
		PlainText = Normalizer.normalize(plainText.toLowerCase(), Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");;

		for (int i = 0; i < 26; i++) {
			Alphabet[i] = (char) (97 + i);
		}

		FillString();
	}

	public void FillString() {

		while (PlainText.length() % N != 0)
			PlainText += 'x';
	}

	public char NumberToCharacter(int y) {
		int x = y;
		int t = 0;

		if (y > 25) {
			x /= 26;
			x *= 26;
			t = y - x;
		} else
			t = y;

		return Alphabet[t];
	}

	public int CharToNumber(char c) {
		for (int i = 0; i < Alphabet.length; i++) {
			if (c == Alphabet[i]) {
				return i;
			}
		}

		return -1;
	}

	public String Encrypt() {
		String encryption = "";

		for (int sIndex = 0; sIndex < PlainText.length(); sIndex += N) {
	;		String temp = PlainText.substring(sIndex, sIndex + N);
			
			for (int c = 0; c < N; c++) {
				
				int[] block = new int[N];

				for (int i = 0; i < N; i++) {
					block[i] = Key[c][i] * CharToNumber(temp.charAt(i));
				}

				int sum = 0;
				for (int i : block) {
					sum += i;
				}
				encryption += NumberToCharacter(sum);
			}

		}

		return encryption;
	}
}
