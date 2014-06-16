package Cipher;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.*;
import javax.swing.plaf.FileChooserUI;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainForm {

	private JFrame frame;
	private JTextField tbFilePath;
	private JButton btnGetKeyFile;
	private JFileChooser fc;
	private JTextField tbPlainTextPath;
	private JButton btnPlainText;
	private JButton btnEncrypt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 226);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		tbFilePath = new JTextField();
		tbFilePath.setBounds(10, 12, 315, 20);
		frame.getContentPane().add(tbFilePath);
		tbFilePath.setColumns(10);

		btnGetKeyFile = new JButton("Key");
		btnGetKeyFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource() == btnGetKeyFile) {
					Component parent = null;
					int val = fc.showOpenDialog(parent);

					if (val == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						tbFilePath.setText(file.getPath());
					}
				}
			}
		});
		btnGetKeyFile.setBounds(335, 11, 89, 23);
		frame.getContentPane().add(btnGetKeyFile);

		tbPlainTextPath = new JTextField();
		tbPlainTextPath.setColumns(10);
		tbPlainTextPath.setBounds(10, 60, 315, 20);
		frame.getContentPane().add(tbPlainTextPath);

		btnPlainText = new JButton("Message");
		btnPlainText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (arg0.getSource() == btnPlainText) {
					Component parent = null;
					int val = fc.showOpenDialog(parent);

					if (val == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						tbPlainTextPath.setText(file.getPath());
					}
				}
			}
		});
		btnPlainText.setBounds(335, 59, 89, 23);
		frame.getContentPane().add(btnPlainText);

		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Component parent = null;
			int val = fc.showSaveDialog(parent);
				File outFile = null;
				
				int[][] key = null;
				int n = 0;

				if (val == JFileChooser.APPROVE_OPTION) {
					outFile = fc.getSelectedFile();
				}

				try {
					// buffered reader to read in key file
					BufferedReader br = new BufferedReader(new FileReader(tbFilePath.getText()));
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
					br = new BufferedReader(new FileReader(tbPlainTextPath.getText()));
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
				String enc = cipher.Encrypt();
				
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
				
				Component comp = null;
				JOptionPane.showMessageDialog(comp, "File has been saved");
				
			}
		});
		btnEncrypt.setBounds(10, 146, 414, 23);
		frame.getContentPane().add(btnEncrypt);

		fc = new JFileChooser();
	}
}
