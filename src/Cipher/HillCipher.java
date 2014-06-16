/*
 * Created By - Mark Provenzano
 * Plain text is used for either encryption or decryption. 
 * 
 */


package Cipher;

import java.text.Normalizer;

/**
 * @author Mark Provenzano
 * Encrypt and decrypt using matrices
 *
 */
public class HillCipher {

	char[] Alphabet = new char[26];
	//. Key that was used for encryption 
	int[][] Key;
	//. Number of rows and columns
	int N;
	String PlainText;
	
	
	/**
	 * Gets the encryption key
	 * @return Key
	 */
	public int[][] getKey() {
		return Key;
	}

	/**
	 * Sets the encryption key
	 * @param key 
	 */
	public void setKey(int[][] key) {
		Key = key;
	}
	
	
	/**
	 * Returns the nxn number for the matrix
	 * @return N
	 */
	public int getN() {
		return N;
	}

	/**
	 * Sets the xnx number for the matrix
	 * @param n
	 */
	public void setN(int n) {
		N = n;
	}

	/**
	 * Returns the plain text value for encryption
	 * @return PlainText
	 */
	public String getPlainText() {
		return PlainText;
	}

	/**
	 * Sets the plain text value to be encrypted.
	 * And fills the empty spaces appropriately with x
	 * Normalizes the string and removes all special characters.
	 * @param plainText
	 */
	public void setPlainText(String plainText) {
		PlainText = Normalizer.normalize(plainText.toLowerCase(),
				Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");;
		FillString();
	}

	/**
	 * Constructor that sets up variables for the class.
	 * @param key the encryption key
	 * @param n nxn of the matrix.
	 * @param plainText all special characters will be removed
	 */
	public HillCipher(int[][] key, int n, String plainText) {
		Key = key;
		N = n;
		PlainText = Normalizer.normalize(plainText.toLowerCase(),
				Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");
		
		InitializeAlphabet();

		FillString();
	}

	/**
	 * Constructor that sets up variables for the class
	 * Plain text will not be initialized
	 * @param key
	 * @param n
	 */
	public HillCipher(int[][] key, int n) {
		Key = key;
		N = n;
		PlainText = "";
		InitializeAlphabet();
	}
	
	/**
	 * Initializes the English ASCII alphabet to an array.
	 */
	private void InitializeAlphabet() {
		for (int i = 0; i < 26; i++) {
			Alphabet[i] = (char) (97 + i);
		}
	}
	
	/**
	 * Fills the plain text string with x to match the matrix requirements
	 */
	private void FillString() {

		while (PlainText.length() % N != 0)
			PlainText += 'x';
	}

	//. ----------------BEGIN ENCRYPTION------------------------------.//
	
	/**
	 * Converts a number to a character with a modulo of 26. (a-z)
	 * @param y
	 * @return ASCII Character a-z
	 */
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

	/**
	 * Converts a character to a number (0-25)
	 * @param c
	 * @return A number 0-25
	 */
	public int CharToNumber(char c) {
		for (int i = 0; i < Alphabet.length; i++) {
			if (c == Alphabet[i]) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Encrypts the plain text based on the key passed in.
	 * pass in this.getKey()
	 * @param key
	 * @return A string from the encrypted plain text
	 */
	public String Encrypt(int[][] key) {
		String encryption = "";

		//. Grab blocks of text based off N
		for (int sIndex = 0; sIndex < PlainText.length(); sIndex += N) {
			String temp = PlainText.substring(sIndex, sIndex + N);

			//. For every row in the key matrix
			for (int r = 0; r < N; r++) {

				//. new encrypted block of size N
				int[] block = new int[N];

				//. For every column in the key matrix
				for (int c = 0; c < N; c++) {
					//. Do stuff to set the character values as numbers
					block[c] = key[r][c] * CharToNumber(temp.charAt(c));
				}
				
				//. Math for getting encrypted characters out of out block
				int sum = 0;
				for (int i : block) {
					sum += i;
				}
				
				//. add said character to the encryption string
				encryption += NumberToCharacter(sum);
			}

		}

		return encryption;
	}

	// . ------------------------------------BEGIN DECRYPTION-------------------------------------.//
	/**
	 * Gets the mod inverse value from a determinant
	 * @param a
	 * @param m
	 * @return Mod inverse value
	 */
	public int ModInverse(int a, int m) {
		a %= m;
		for (int x = 1; x < m; x++) {
			if ((a * x) % m == 1) {
				return x;
			}
		}

		return -1;
	}
	
	/**
	 * Determinant of the matrix
	 * @param matrix
	 * @return Determinant value of the matrix
	 */
	public int Determinant(int[][] matrix)  {
	    if (matrix[0].length == 1) {
	    	return matrix[0][0];
	    }
	    if (matrix[0].length ==2) {
	        return (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
	    }
	    
	    return -1;
	} 
	
	/**
	 * Gets the decryption key from the original encryption key
	 * @return a new key from the original encryption key
	 */
	public int[][] getDecryptionKey() {
		int[][] newKey = Key;
		if (N == 2) {
			int first = newKey[0][0];
			int last = newKey[1][1];
			newKey[0][0] = last;
			 newKey[1][1] = first;
			newKey[0][1] *= -1;
			newKey[1][0] *= -1;
			
		}
		
		int[][] decKey = new int[N][N];
		
		int a = ModInverse(Determinant(Key) , 26);
		
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				int temp = 0;
				int x = (temp = newKey[r][c] * a);
				if (x >= 0) {
					temp /= 26;
					temp *= 26;
					decKey[r][c] = x - temp;
				}
				else {
					int mod = 26;
					while (-1 * x > mod) {
						mod += 26;
					}
					
					decKey[r][c] = x + mod;
				}
			}
			
		}
		
		return decKey;
	}
	
	/**
	 * Decrypts the text provided prior to its encrypted state.
	 * Be sure when using this to pas in this.getDecryptionKey(); 
	 * @param key
	 * @param encryption
	 * @return Plain text from encrypted string
	 */
	public String Decrypt(int[][] key , String encryption) {
		String temp = PlainText;
		PlainText = encryption;
		String result = Encrypt(key);
		PlainText = temp;
		
		return result;
	}
}
