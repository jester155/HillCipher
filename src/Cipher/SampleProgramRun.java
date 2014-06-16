package Cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SampleProgramRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("Please enter the name of the file storing the key.");
		String keyFilePath = in.nextLine();
		
		System.out.println("Please enter the name of the file to encrypt.");
		String messageFilePath = in.nextLine();
		
		System.out.println("Please enter the name of the file to store the ciphertext.");
		String outFilePath = in.nextLine();
		
		File outFile = new File(outFilePath);
		
		//. Read the key file
		
		int[][] key = null;
		int n = 0;
		try {
			// buffered reader to read in key file
			BufferedReader br = new BufferedReader(new FileReader(keyFilePath));
			String line;
			
			int count = -1;
			while ((line = br.readLine()) != null) {
				if (count == -1) {
					n = Integer.parseInt(line);
					key = new int[n][n];
				}
				else {
					for (int i = 0; i < line.split(" ").length; i++) {
						key[count][i] = Integer.parseInt(line.split(" ")[i]);
					}
				}
				count++;
			}			
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//. Reads in the message file
		String plainText = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(messageFilePath));
			String line;
			
			while ((line = br.readLine()) != null) {
				plainText += line.replaceAll(" ", "");
			}	
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HillCipher cipher = new HillCipher(key, n, plainText);
		String enc = cipher.Encrypt(cipher.getKey());
		
		try {
			FileWriter fw = new FileWriter(outFile);
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			if (!outFile.exists()) {
				outFile.createNewFile();
			}
			
			bw.write(enc);
			bw.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		System.out.println("\nFile completed sucessfully!");
	}

}
